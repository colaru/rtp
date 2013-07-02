package com.openwager.rtp.test.integration.java;

import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

public class Util {

    public static JsonObject createEventMessage() {
        JsonObject message = new JsonObject().putString("_t", "msg");
        JsonObject header = new JsonObject().putString("_t", "hdr").putString("cid", "m4");
        JsonArray numbers = new JsonArray().addNumber(1).addNumber(2).addNumber(12312).addNumber(12);
        JsonObject body = new JsonObject().putString("_t", "sum").putArray("numbers", numbers);
        message.putObject("header", header);
        message.putObject("body", body);
        return message;
    }
}
