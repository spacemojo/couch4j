package com.standardstate.couch4j.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FieldParser {

    public final static String COUCHDB_ID = "{couchdbId}";
    public final static String COUCHDB_REV = "{couchdbRev}";

    private final static Pattern couchdbIdPattern = Pattern.compile("^[0-9a-f]{32}$");
    private final static Pattern couchdbRevPattern = Pattern.compile("^[0-9]{1,}-[0-9a-f]{32}$");

    private final static Map<String, String> fieldDefinitions = new HashMap<>();

    static {
        fieldDefinitions.put("truefalse", COUCHDB_ID);
        fieldDefinitions.put("falsetrue", COUCHDB_REV);
    }

    public static String parse(final String value) {
        
        final String key = 
                String.valueOf(couchdbIdPattern.matcher(value).matches())
              + String.valueOf(couchdbRevPattern.matcher(value).matches());
        return (fieldDefinitions.containsKey(key) ? fieldDefinitions.get(key) : value);

    }

}
