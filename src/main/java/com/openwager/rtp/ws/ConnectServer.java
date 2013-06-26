package com.openwager.rtp.ws;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.platform.Verticle;

public class ConnectServer extends Verticle {

  private static final int BUFF_SIZE = 32 * 1024;

  int count = 0;

  public void start() {
    vertx.createHttpServer().setReceiveBufferSize(BUFF_SIZE).setSendBufferSize(BUFF_SIZE).
        websocketHandler(new Handler<ServerWebSocket>() {
      public void handle(ServerWebSocket ws) {
        System.out.println("connected " + ++count);
      }
    }).listen(8080, "localhost");
  }
}