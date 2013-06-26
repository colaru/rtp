package com.openwager.rtp.ws;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.WebSocket;
import org.vertx.java.platform.Verticle;

public class ConnectClient extends Verticle {

  private HttpClient client;

  // Number of connections to create
  private static final int CONNS = 1000;


  public ConnectClient() {
  }

  int connectCount;

  public void start() {
    System.out.println("Starting perf client");
    client = vertx.createHttpClient().setPort(8080).setHost("localhost").setMaxPoolSize(CONNS);
    for (int i = 0; i < CONNS; i++) {
      System.out.println("connecting ws");
      client.connectWebsocket("/someuri", new Handler<WebSocket>() {
        public void handle(WebSocket ws) {
          System.out.println("ws connected: " + ++connectCount);
        }
      });
    }
  }

}
