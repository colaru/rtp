load('vertx.js')

var eb = vertx.eventBus;

var address = 'example.address'
var creditsAddress = 'example.credits'

var batchSize = 10000;

var handler = function() {
  credits += batchSize;
  sendMessage();
}

eb.registerHandler(creditsAddress, handler);

var credits = batchSize;
var count = 0

sendMessage();

function sendMessage() {
  console.log("sendMessages()");
  for (var i = 0; i < batchSize / 2; i++) {
    if (credits > 0) {
      credits--;
      eb.send(address, "some-message");
      count++;
    }
    else {
      return;
    }
  }

  console.log("before runLoop, messages so far " + count);
  vertx.runOnLoop(sendMessage);
}

function vertxStop() {
  eb.unregisterHandler(creditsAddress, handler);
}

console.log("Started");
