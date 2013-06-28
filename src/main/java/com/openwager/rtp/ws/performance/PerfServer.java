package com.openwager.rtp.ws.performance;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.core.streams.Pump;
import org.vertx.java.platform.Verticle;

public class PerfServer extends Verticle {

  private static final int BUFF_SIZE = 32 * 1024;
  int count = 0;

  public void start() {

    System.out.println("Starting perf server");

    vertx.createHttpServer().setReceiveBufferSize(BUFF_SIZE).setSendBufferSize(BUFF_SIZE).setAcceptBacklog(32000).
        websocketHandler(new Handler<ServerWebSocket>() {

      public void handle(ServerWebSocket ws) {
        System.out.println("connected " +  ++count);
        Pump.createPump(ws, ws, BUFF_SIZE).start();
      }
    }).listen(8080, "localhost");
  }
}
