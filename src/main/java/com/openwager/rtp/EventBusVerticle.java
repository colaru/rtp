package com.openwager.rtp;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

public class EventBusVerticle extends Verticle {

  public void start() {

    vertx.eventBus().registerHandler("default.address", new Handler<Message<String>>() {
      @Override
      public void handle(Message<String> message) {
        message.reply("Test");
        container.logger().info("Received: " + message.toString());
      }
    });

    container.logger().info("Event Bus Verticle started");

  }
}
