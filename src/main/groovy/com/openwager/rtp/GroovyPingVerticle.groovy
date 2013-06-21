package com.openwager.rtp

import org.vertx.groovy.platform.Verticle

/*
 * This is a simple compiled Groovy verticle which receives `ping` messages on the event bus and sends back `pong`
 * replies
 */
class GroovyPingVerticle extends Verticle {

  def start() {

    vertx.eventBus.registerHandler("ping-address") { message ->
      message.reply("pong!")
      container.logger.info("Sent back pong groovy!")
    }
  }
}
