package com.openwager.rtp.test.integration.java;

import org.junit.Test;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.*;
import org.vertx.testtools.TestVerticle;

import static org.vertx.testtools.VertxAssert.*;

/**
 * Simple integration test which shows tests deploying other verticles, using the Vert.x API etc
 */
public class BasicIntegrationTest extends TestVerticle {

    private static final int CONNS = 100;
    int connectCount = 0;

    @Test
  /*
  This demonstrates using the Vert.x API from within a test.
   */
    public void testHTTP() {
        // Create an HTTP server which just sends back OK response immediately
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response().end();
            }
        }).listen(8181, new AsyncResultHandler<HttpServer>() {
            @Override
            public void handle(AsyncResult<HttpServer> asyncResult) {
                assertTrue(asyncResult.succeeded());
                // The server is listening so send an HTTP request
                vertx.createHttpClient().setPort(8181).getNow("/", new Handler<HttpClientResponse>() {
                    @Override
                    public void handle(HttpClientResponse resp) {
                        assertEquals(200, resp.statusCode());
            /*
            If we get here, the test is complete
            You must always call `testComplete()` at the end. Remember that testing is *asynchronous* so
            we cannot assume the test is complete by the time the test method has finished executing like
            in standard synchronous tests
            */
                        testComplete();
                    }
                });
            }
        });
    }

    @Test
  /*
  This test deploys some arbitrary verticle - note that the call to testComplete() is inside the Verticle `SomeVerticle`
   */
    public void testDeployArbitraryVerticle() {
        assertEquals("bar", "bar");
        container.deployVerticle(SomeVerticle.class.getName());
    }

    @Test
    public void testDeployWebSoketServerVerticle() {
        assertEquals("bar", "bar");
        container.deployVerticle(WebSoketServer.class.getName(), 2);

        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();
        System.out.println("Starting perf client");
        HttpClient client = vertx.createHttpClient().setPort(8080).setHost("localhost").setMaxPoolSize(CONNS);
        for (int i = 0; i < CONNS; i++) {
            System.out.println("connecting ws: " + i);
            client.connectWebsocket("/someuri", new Handler<WebSocket>() {
                public void handle(WebSocket ws) {
                    ws.write(new Buffer("request-string: " + ++connectCount));

//                    System.out.println("ws connected: " + ++connectCount);

                    ws.dataHandler(new Handler<Buffer>() {
                        @Override
                        public void handle(Buffer buff) {
                            System.out.println("response:  " + buff.toString());
                        }
                    });

//                    testComplete();
                }
            });
        }
        endTime = System.currentTimeMillis();
        System.out.println("Ending perf client in: " + (endTime - startTime) + " milliseconds");

    }

    @Test
    public void testCompleteOnTimer() {
        vertx.setTimer(1000, new Handler<Long>() {
            @Override
            public void handle(Long timerID) {
                assertNotNull(timerID);

                // This demonstrates how tests are asynchronous - the timer does not fire until 1 second later -
                // which is almost certainly after the test method has completed.
                testComplete();
            }
        });
    }


}
