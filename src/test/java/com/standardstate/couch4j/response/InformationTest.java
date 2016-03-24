package com.standardstate.couch4j.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class InformationTest {

  @Test
  public void constructorTest() {
    assertNotNull("constructorTest()", new Information());
  }

  @Test
  public void getterSetterTests() {

    final Information information = new Information();

    information.setCommitted_update_seq(12);
    assertEquals("committed_update_seq", new Integer(12), information.getCommitted_update_seq());

    information.setDoc_count(20);
    assertEquals("doc_count", new Integer(20), information.getDoc_count());

    information.setDoc_del_count(24);
    assertEquals("doc_del_count", new Integer(24), information.getDoc_del_count());

    information.setUpdate_seq(36);
    assertEquals("update_seq", new Integer(36), information.getUpdate_seq());

  }

}
