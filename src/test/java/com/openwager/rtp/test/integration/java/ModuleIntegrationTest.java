package com.openwager.rtp.test.integration.java;

import org.junit.Test;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.WebSocket;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.TestVerticle;

import static org.vertx.testtools.VertxAssert.*;

/**
 * Example Java integration test that deploys the module that this project builds.
 *
 * Quite often in integration tests you want to deploy the same module for all tests and you don't want tests
 * to start before the module has been deployed.
 *
 * This test demonstrates how to do that.
 */
public class ModuleIntegrationTest extends TestVerticle {

    private static final int CONNS = 1;
    int connectCount = 0;

//      @Test
//      public void testSomethingElse() {
//        // Whatever
//        testComplete();
//      }

    @Test
    public void testWebSocketServer() {

        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();
        container.logger().info("Starting web socket test");
        HttpClient client = vertx.createHttpClient().setPort(8081).setHost("localhost").setMaxPoolSize(CONNS);
        for (int i = 0; i < CONNS; i++) {
            container.logger().info("Connecting ws: " + (i+1));
            client.connectWebsocket("/rtp", new Handler<WebSocket>() {
                public void handle(WebSocket ws) {

                    ws.write(new Buffer(Util.createEventMessage().toString()));
                    vertx.eventBus().publish("default.address", Util.createEventMessage().toString());

                    ws.dataHandler(new Handler<Buffer>() {
                        @Override
                        public void handle(Buffer buff) {
                            assertEquals(buff.toString(), Util.createEventMessage().toString());
                            container.logger().info("Response:  " + buff.toString());

                            testComplete();
                        }
                    });
                }
            });
        }
        endTime = System.currentTimeMillis();
        container.logger().info("Ending web socket test (asynchronous call) client in: " + (endTime - startTime) + " milliseconds");
    }

    @Test
    public void testWebSocketServerAndEventBus() {
        container.logger().info("Starting web socket and service bus test");

        vertx.eventBus().registerHandler("default.address", new Handler<Message>() {
            @Override
            public void handle(Message reply) {
                assertEquals(Util.createEventMessage().toString(), reply.body().toString());
                container.logger().info("Response on Event Bus:  " + reply.body().toString());
                testComplete();
            }
        });

//        vertx.eventBus().publish("default.address", createEventMessage());

        HttpClient client = vertx.createHttpClient().setPort(8081).setHost("localhost").setMaxPoolSize(CONNS);
        container.logger().info("Connecting ws ");

        client.connectWebsocket("/rtp", new Handler<WebSocket>() {
            public void handle(WebSocket ws) {

                ws.write(new Buffer(Util.createEventMessage().toString()));

                ws.dataHandler(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buff) {
                        assertEquals(buff.toString(), Util.createEventMessage().toString());
                        container.logger().info("Response on Web Socket:  " + buff.toString());

                        testComplete();
                    }
                });
            }
        });
    }

    @Override
  public void start() {
    // Make sure we call initialize() - this sets up the assert stuff so assert functionality works correctly
    initialize();
    // Deploy the module - the System property `vertx.modulename` will contain the name of the module so you
    // don't have to hardecode it in your tests

    String conf = "{\"webSocketServer\": {\"port\": 8081,\"instances\": 1},\"eventBusVerticle\": {\"instances\": 1}}";
    JsonObject config = new JsonObject(conf);

//    JsonObject webSocketServer = new JsonObject().putNumber("port", 8081).putNumber("instances", 1);
//    JsonObject eventBusVerticle = new JsonObject().putNumber("instances", 1);
//    config.putObject("webSocketServer", webSocketServer);
//    config.putObject("eventBusVerticle", eventBusVerticle);

    container.deployModule(System.getProperty("vertx.modulename"), config, new AsyncResultHandler<String>() {
      @Override
      public void handle(AsyncResult<String> asyncResult) {
      // Deployment is asynchronous and this this handler will be called when it's complete (or failed)
      if (asyncResult.failed()) {
        container.logger().error(asyncResult.cause());
      }
      assertTrue(asyncResult.succeeded());
      assertNotNull("deploymentID should not be null", asyncResult.result());
      // If deployed correctly then start the tests!
      startTests();
      }
    });
  }

}
