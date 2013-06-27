package com.openwager.rtp;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

/*
 * This is a simple Java verticle which receives `ping` messages on the event bus and sends back `pong` replies
 *
 */
public class EventBusVerticle extends Verticle {

  public void start() {

    vertx.eventBus().registerHandler("default.address", new Handler<Message<String>>() {
      @Override
      public void handle(Message<String> message) {
        message.reply("Test");
        container.logger().info("Sent back: " + message.toString());
      }
    });

    container.logger().info("EventBusVerticle started");

  }
}
