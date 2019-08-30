package com.standardstate.couch4j.util;

import com.standardstate.couch4j.Couch4JException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class ConstrainedMapTest {

  @Test
  public void constructorTest() {
    assertNotNull("constructorTest()", new ConstrainedMap());
  }

  @Test(expected = Couch4JException.class)
  public void putFailure() {
    final ConstrainedMap map = new ConstrainedMap();
    map.put("notValidKey", "aValue");
  }

  @Test
  public void putAll() {

    final Map<String, String> toPut = new HashMap<>();
    toPut.put("map", "this is the value for map");
    toPut.put("reduce", "this is the value for reduce");

    final ConstrainedMap map = new ConstrainedMap();
    assertEquals("constrained map size", 0, map.size());

    map.putAll(toPut);
    assertEquals("constrained map size", 2, map.size());

  }

}
