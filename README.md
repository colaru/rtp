# Vert.x Tests Sandbox Module

+ Scalability, performance, integration, unit tests for Vert.x 2.x: http://vert-x.github.io
+ Based on Vert.x Gradle template: https://github.com/vert-x/vertx-gradle-template
+ Using container testing support for Vert.x (Java, Groovy, JavaScript):  https://github.com/vert-x/testtools
+ Build with Gradle: http://www.gradle.org

Verticles:

* BootstrapVerticle - a verticle that starts all other module verticles and is configurable via json files.Is the main verticle for this module: "main":"com.openwager.rtp.BootstrapVerticle". Configuration file sample (conf.json):
{
    "webSocketServer": {
        "port": 8081,
        "instances": 1
    },
    "eventBusVerticle": {
        "instances": 1
    }
}

* WebSocketServer - a web socket server that take the message, put it on event bus and send it back to the caller (echo)
* EventBusVerticle - a event bus handler that log the message received

Tests:
1. testEventBusPointToPoint (BasicIntegrationTest) - test event bus point to point

2. testEventBusPublishSubscribe (BasicIntegrationTest) - test event bus pub/subscribe

3. testWebSocketServer (ModuleIntegrationTest) - test for send/receive for WebSocket server

4. testWebSocketServerAndEventBus (ModuleIntegrationTest) - test WebSocket and Event Bus working together

5. testWebsocketPerformance (BasicPerformanceTest) - performance test for WebSocket

6. testEvenBusPerformance (BasicPerformanceTest) - performance test for EventBus

7. testMongoPersistor (ThirdPartyModulesTest) - test for Mongodb persistor


