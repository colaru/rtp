package com.openwager.rtp;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

public class BootstrapVerticle extends Verticle {

    public void start() {

        JsonObject appConfig = container.config();

        container.logger().info("Config is " + appConfig);
        container.logger().info("Number of cores is " + Runtime.getRuntime().availableProcessors());

        if(!appConfig.toString().contains("webSocketServer")) {
            String conf = "{\"webSocketServer\": {\"port\": 8081,\"instances\": 1},\"eventBusVerticle\": {\"instances\": 1}}";
            appConfig = new JsonObject(conf);
        }

        JsonObject webSocketServerConfig = appConfig.getObject("webSocketServer");

        container.deployVerticle(WebSocketServer.class.getName(),
                webSocketServerConfig,
                webSocketServerConfig.getInteger("instances"),
                new AsyncResultHandler<String>() {
                @Override
                public void handle(AsyncResult<String> asyncResult) {
                    if (asyncResult.failed()) {
                        container.logger().error(asyncResult.cause());
                    }
                    container.logger().info(asyncResult.succeeded());
                }});

        container.deployVerticle(EventBusVerticle.class.getName(),
                webSocketServerConfig.getInteger("instances"),
                new AsyncResultHandler<String>() {
                @Override
                public void handle(AsyncResult<String> asyncResult) {
                    if (asyncResult.failed()) {
                        container.logger().error(asyncResult.cause());
                    }
                    container.logger().info(asyncResult.succeeded());
                }});
    }
}
