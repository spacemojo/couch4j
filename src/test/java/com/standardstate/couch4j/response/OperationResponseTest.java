package com.standardstate.couch4j.response;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class OperationResponseTest {

  @Test
  public void operationResponseTest() {

    final OperationResponse response = new OperationResponse();
    response.setId("1029384756");
    response.setOk(Boolean.TRUE);
    response.setRev("1-1029384756");
    assertEquals("operationResponseTest()", "{\"ok\":true,\"id\":\"1029384756\",\"rev\":\"1-1029384756\"}", response.toString());

  }

}
