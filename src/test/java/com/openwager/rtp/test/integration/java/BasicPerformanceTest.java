package com.openwager.rtp.test.integration.java;

import com.openwager.rtp.WebSocketServer;
import com.openwager.rtp.eventbus.performance.Handler;
import com.openwager.rtp.eventbus.performance.Sender;
import com.openwager.rtp.ws.performance.PerfClient;
import com.openwager.rtp.ws.performance.PerfServer;
import com.openwager.rtp.ws.performance.RateCounter;
import org.junit.Ignore;
import org.junit.Test;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.TestVerticle;

import static org.vertx.testtools.VertxAssert.testComplete;

/**
 * Simple integration test which shows tests deploying other verticles, using the Vert.x API etc
 */
public class BasicPerformanceTest extends TestVerticle {

    @Test
    public void testWebsocketPerformance() {

//        JsonObject config = container.config();
//        container.logger().info("Config is " + config);
//
//        container.deployVerticle(RateCounter.class.getName());
//
//        container.deployVerticle(PerfServer.class.getName());
////        String conf = "{\"webSocketServer\": {\"port\": 8080,\"instances\": 1},\"eventBusVerticle\": {\"instances\": 2}}";
////        JsonObject modConfig = new JsonObject(conf);
////        container.deployModule(System.getProperty("vertx.modulename"), modConfig);
//
//        container.deployVerticle(PerfClient.class.getName());

        testComplete(); // comment this line for long time running the test
    }

    @Test
    public void testEvenBusPerformance() {

//        JsonObject config = container.config();
//        container.logger().info("Config is " + config);
//
//        container.deployVerticle(Handler.class.getName());
//
//        container.deployVerticle(Sender.class.getName());

        testComplete(); // comment this line for long time running the test
    }
}
