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

        calculateFibonacciSeries(200);
        Pump.createPump(ws, ws, BUFF_SIZE).start();
      }
    }).listen(8080, "localhost");
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
