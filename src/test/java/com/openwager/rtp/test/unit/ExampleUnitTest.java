package com.openwager.rtp.test.unit;

import com.openwager.rtp.PingVerticle;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ExampleUnitTest {

  @Test
  public void testVerticle() {
    PingVerticle vert = new PingVerticle();

    // Interrogate your classes directly....

    assertNotNull(vert);
  }
}
