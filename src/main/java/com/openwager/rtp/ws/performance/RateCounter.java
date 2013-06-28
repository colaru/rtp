package com.openwager.rtp.ws.performance;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

public class RateCounter extends Verticle implements Handler<Message<Integer>> {

  private long last;

  private long count;

  private long start;

  private long totCount;

  public void handle(Message<Integer> msg) {
    if (last == 0) {
      last = start = System.currentTimeMillis();
    }
    count += msg.body();
    totCount += msg.body();
  }

  public void start() {
    System.out.println("Starting rate counter");

    vertx.eventBus().registerHandler("rate-counter", this);
    vertx.setPeriodic(30, new Handler<Long>() {
      public void handle(Long id) {
        if (last != 0) {
          System.out.println(" Count: " + count);
          long now = System.currentTimeMillis();
          long rate = 1000 * (long)count / (now - last);
          long avRate = 1000 * (long)totCount / (now - start);
          count = 0;
          System.out.println((now - start) + " Rate: count/sec: " + rate + " Average rate: " + avRate);
          last = now;
        }
      }
    });
  }
}
