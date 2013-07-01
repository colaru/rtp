package com.openwager.rtp.eventbus.performance;

import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

import java.util.Date;

public class Handler extends Verticle {

    String address = "example.address";
    String creditsAddress = "example.credits";

    int batchSize = 10000;
    int received = 0;
    int count = 0;
    Date start = null;

    public void start() {

        container.logger().info("Handler started");

        vertx.eventBus().registerHandler(address, new org.vertx.java.core.Handler<Message<String>>() {
            @Override
            public void handle(Message<String> message) {


                received++;
                if (received == batchSize)
                {
                    vertx.eventBus().send(creditsAddress, (String) null);
//                    container.logger().info("received " + received);
                    received = 0;
                }

                count++;
                if (count % batchSize == 0)
                {
//                    container.logger().info("count " + count);
                    if (start == null) {
                        start = new Date();
                    } else {
                        Date now = new Date();
                        long elapsed = now.getTime() - start.getTime();
                        double rate = 1000 * (count + 0.0) / (elapsed);
                        container.logger().info("rate: " + (long)rate + " msgs/sec");
                    }
                }
            }
        });
    }
}

