package com.standardstate.couch4j;

import static com.standardstate.couch4j.Constants.UUIDS;
import com.standardstate.couch4j.response.UUIDS;
import com.standardstate.couch4j.response.Welcome;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class StatusOperationsTest extends BaseTest {

  @Test
  public void consructorTest() {
    assertNotNull("constructorTest()", new StatusOperations());
  }

  @Test
  public void getWelcome() {

    final Welcome serverWelcome = new StatusOperations(session).getWelcome();
    assertEquals("getWelcome(1)", "Welcome", serverWelcome.getCouchdb());
    assertFalse("getWelcome(2)", serverWelcome.getVersion().isEmpty());
  }

  @Test
  public void getUUIDS() {

    final int count = 3;
    final UUIDS uuids = new StatusOperations(session).getUUIDS(count);
    assertEquals("getUUIDS()", count, uuids.getUuids().length);
  }

  @Test
  public void getUUID() {

    final String uuid = new StatusOperations(session).getUUID();
    assertNotNull("getUUID()", uuid);
  }

}
