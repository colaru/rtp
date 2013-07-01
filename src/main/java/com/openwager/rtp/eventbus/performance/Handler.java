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

//                calculateFibonacciSeries(200);

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

    private String calculateFibonacciSeries(int limit) {
        String response = "";
        long[] series = new long[limit];

        //create first 2 series elements
        series[0] = 0;
        series[1] = 1;

        //create the Fibonacci series and store it in an array
        for(int i=2; i < limit; i++){
            series[i] = series[i-1] + series[i-2];
        }

        //print the Fibonacci series numbers

        response += "Fibonacci Series upto " + limit;
        for(int i=0; i< limit; i++){
            response += series[i] + " ";
        }

        return response;
    }
}

