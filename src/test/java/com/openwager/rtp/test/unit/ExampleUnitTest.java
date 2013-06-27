package com.openwager.rtp.test.unit;

import com.openwager.rtp.EventBusVerticle;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ExampleUnitTest {

  @Test
  public void testVerticle() {
    EventBusVerticle vert = new EventBusVerticle();

    // Interrogate your classes directly....

    assertNotNull(vert);
  }
}
