package com.openwager.rtp.test.integration.java;

import org.junit.Test;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.TestVerticle;

import static org.vertx.testtools.VertxAssert.*;

/**
 * Simple integration test which shows tests deploying other verticles, using the Vert.x API etc
 */
public class ThirdPartyModulesTest extends TestVerticle {

    @Test
    public void testMongoPersistor() {

        container.deployModule("io.vertx~mod-mongo-persistor~2.0.0-beta2", new AsyncResultHandler<String>() {
            @Override
            public void handle(AsyncResult<String> asyncResult) {

                if (asyncResult.failed()) {
                    container.logger().error("Deployment error: ", asyncResult.cause());
                } else {
                    container.logger().info("Deployment done: io.vertx~mod-mongo-persistor~2.0.0-CR2");

                    // a test for delete all and fiend by criteria can be added here

                    String jsonMessage = "{\"action\": \"save\", \"collection\": \"test\", \"document\": {\"firstname\": \"Tim\", \"lastname\": \"Fox\", \"username\": \"tim\", \"password\": \"password\"}}";

                    vertx.eventBus().send("vertx.mongopersistor", new JsonObject(jsonMessage), new Handler<Message<JsonObject>>() {
                        public void handle(Message<JsonObject> message) {
                            container.logger().info("I received a reply " + message.body().toString());
                            assertEquals(message.body().getString("status"), "ok");
                            testComplete();
                        }
                    });

                    String saveAction = "{\"action\": \"save\", \"collection\": \"docs\"}}";
                    JsonObject saveObject = new JsonObject(saveAction);
                    saveObject.putObject("document", Util.createEventMessage());

                    vertx.eventBus().send("vertx.mongopersistor", saveObject, new Handler<Message<JsonObject>>() {
                        public void handle(Message<JsonObject> message) {
                            container.logger().info("I received a reply " + message.body().toString());
                            assertEquals(message.body().getString("status"), "ok");
                            testComplete();
                        }
                    });

                }
                assertTrue(asyncResult.succeeded());
                assertNotNull("deploymentID should not be null", asyncResult.result());
                // If deployed correctly then start the tests!
            }});
    }
}
