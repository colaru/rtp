import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

/*
 * This is a simple Java *source* verticle which receives `ping` messages on the event bus and sends back `pong`
 * replies.
 *
 * Note that we don't precompile this - Vert.x can do this on the fly when it's run
 *
 */
public class JavaSourcePingVerticle extends Verticle {

  public void start() {


    vertx.eventBus().registerHandler("ping-address", new Handler<Message<String>>() {
      @Override
      public void handle(Message<String> message) {
        message.reply("pong!");
        container.logger().info("Sent back pong");
      }
    });

    container.logger().info("PingVerticle started");

  }
}
