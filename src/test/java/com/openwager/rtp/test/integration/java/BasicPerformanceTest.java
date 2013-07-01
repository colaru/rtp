package com.openwager.rtp.test.integration.java;

import com.openwager.rtp.eventbus.performance.Handler;
import com.openwager.rtp.eventbus.performance.Sender;
import com.openwager.rtp.ws.performance.PerfClient;
import com.openwager.rtp.ws.performance.PerfServer;
import com.openwager.rtp.ws.performance.RateCounter;
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

        JsonObject config = container.config();
        container.logger().info("Config is " + config);

        container.deployVerticle(RateCounter.class.getName());
        container.deployVerticle(PerfServer.class.getName());
        container.deployVerticle(PerfClient.class.getName());

//        testComplete(); // uncomment for long time running the test
    }

    @Test
    public void testEvenBusPerformance() {

        JsonObject config = container.config();
        container.logger().info("Config is " + config);

        container.deployVerticle(Handler.class.getName());
        container.deployVerticle(Sender.class.getName());

        testComplete(); // uncomment for long time running the test
    }
}
