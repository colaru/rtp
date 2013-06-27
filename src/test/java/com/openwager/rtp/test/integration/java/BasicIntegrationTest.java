package com.openwager.rtp.test.integration.java;

import com.openwager.rtp.EventBusVerticle;
import org.junit.Test;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.*;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.TestVerticle;

import static org.vertx.testtools.VertxAssert.*;
import static org.vertx.testtools.VertxAssert.assertEquals;

/**
 * Simple integration test which shows tests deploying other verticles, using the Vert.x API etc
 */
public class BasicIntegrationTest extends TestVerticle {

    @Test
    public void testEventBusPointToPoint() {

        container.deployVerticle(EventBusVerticle.class.getName());

        container.logger().info("In Event Bus test() PointToPoint");
        vertx.eventBus().send("default.address", createEventMessage(), new Handler<Message<String>>() {
            @Override
            public void handle(Message<String> reply) {
                assertEquals("Test", reply.body());

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

    private JsonObject createEventMessage() {
        JsonObject message = new JsonObject().putString("_t", "msg");
        JsonObject header = new JsonObject().putString("_t", "hdr").putString("cid", "m4");
        JsonArray numbers = new JsonArray().addNumber(1).addNumber(2).addNumber(12312).addNumber(12);
        JsonObject body = new JsonObject().putString("_t", "sum").putArray("numbers", numbers);
        message.putObject("header", header);
        message.putObject("body", body);
        return message;
    }
}
