package com.openwager.rtp;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.streams.Pump;
import org.vertx.java.platform.Verticle;

public class WebSocketServer extends Verticle {
    private static final int BUFF_SIZE = 32 * 1024;

    int count = 0;

    public void start() {

        JsonObject config = container.config();
        container.logger().info("Config is " + config);

        int port = config.getInteger("port");

        vertx.createHttpServer().setReceiveBufferSize(BUFF_SIZE).
                setAcceptBacklog(10000).
                setSendBufferSize(BUFF_SIZE).
                setReceiveBufferSize(BUFF_SIZE).
                websocketHandler(new Handler<ServerWebSocket>() {
                    public void handle(final ServerWebSocket ws) {
//                        ws.write(new Buffer("response-string:   " + ++count));
                        container.logger().info("Connected: " + ++count);

                        ws.dataHandler(new Handler<Buffer>() {
                            public void handle(Buffer data) {
                                container.logger().info("Put an event on Event Bus: " + data.toString());
                                vertx.eventBus().publish("default.address", data.toString());
//                                ws.writeTextFrame(data.toString()); // Echo it back
                            }
                        });

                        container.logger().info("Topic name: " + ws.path());

                        vertx.eventBus().registerHandler(ws.path().replace("/", ""), new Handler<Message<String>>() {
                            @Override
                            public void handle(Message<String> message) {
                                message.reply("Test");
                                ws.writeTextFrame(message.body().toString()); // Echo it back twice
                                container.logger().info("Received: " + message.body().toString());
                            }
                        });

//                        Pump.createPump(ws, ws).start();
                    }
                }).listen(port, "localhost");
    }
}
