package com.standardstate.couch4j.util;

import org.junit.Test;

public class UtilsTest {
    
    final String[] testJSONs = {"{\"_id\":\"1234567890\", \"_rev\": \"123\", \"name\":\"something\"}", 
                                "{\"_rev\" :\"123\", \"name\":\"something\"}",
                                "{\"_id\":\"1234567890\",\"_rev\":\"value\", \"name\":\"something\"}",
                                "{\"_id\":\"1234567890\", \"_rev\" : \"123\", \"name\":\"something\"}",
                                "{\"_id\":\"1234567890\", \"_rev\":\"\", \"name\":\"something\"}",
                                "{\"_id\":\"1234567890\", \"_rev\":\"\", \"name\":\"something\", \"_rev\":\"last ... \"}",
                                "{\"_id\":\"1234567890\", \"name\":\"something\", \"_rev\":\"last ... \"} "};
    
    @Test
    public void removeRevTest() {
                
        for(String test : testJSONs) {
            System.out.println(test + " -> " + Utils.removeRev(test));
        }
        
    }
    
}
