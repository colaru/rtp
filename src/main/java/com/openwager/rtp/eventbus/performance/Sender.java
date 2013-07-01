package com.openwager.rtp.eventbus.performance;

import org.vertx.java.core.Handler;
import org.vertx.java.core.VoidHandler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

public class Sender extends Verticle {

    String address = "example.address";
    String creditsAddress = "example.credits";

    int batchSize = 10000;
    int credits = batchSize;
    int count = 0;

    public void start() {
        container.logger().info("Sender started");

        vertx.eventBus().registerHandler(creditsAddress, new Handler<Message<String>>() {
            @Override
            public void handle(Message<String> message) {
                credits += batchSize;
                sendMessage(vertx.eventBus());
            }
        });

        sendMessage(vertx.eventBus());
    }

    void sendMessage(final EventBus eb) {
//        container.logger().info("sendMessages()");
        for (int i = 0; i < batchSize / 2; i++) {
            if (credits > 0) {
                credits--;
                eb.send(address, "some-message");
                count++;
            } else {
                return;
            }
        }

//        container.logger().info("before runLoop, messages so far " + count);
        vertx.runOnContext(new VoidHandler() {
            public void handle() {
                sendMessage(eb);
            }
        });
    }
}


