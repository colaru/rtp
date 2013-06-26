package com.openwager.rtp;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.streams.Pump;
import org.vertx.java.platform.Verticle;

public class WebSoketServer extends Verticle {
    private static final int BUFF_SIZE = 32 * 1024;

    int count = 0;

    public void start() {
        vertx.createHttpServer().setReceiveBufferSize(BUFF_SIZE).setSendBufferSize(BUFF_SIZE).
                websocketHandler(new Handler<ServerWebSocket>() {
                    public void handle(ServerWebSocket ws) {
//                        ws.write(new Buffer("response-string:   " + ++count));
                        container.logger().info("Connected: " + ++count);
                        Pump.createPump(ws, ws).start();
                    }
                }).listen(8080, "localhost");
    }
}
