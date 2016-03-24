package com.standardstate.couch4j.util;

import com.standardstate.couch4j.Couch4JException;
import java.util.HashMap;
import java.util.Map;

public class ConstrainedMap extends HashMap<String, String> {

  @Override
  public String put(final String key, final String value) {
    if ("map".equals(key) || "reduce".equals(key)) {
      return super.put(key, value);
    } else {
      throw new Couch4JException("Key \"" + key + "\" is not accepted, only \"map\" and \"reduce\" are acceptable values.");
    }
  }

  @Override
  public void putAll(Map<? extends String, ? extends String> map) {
    clear();
    for (String key : map.keySet()) {
      put(key, map.get(key));
    }
  }

}
