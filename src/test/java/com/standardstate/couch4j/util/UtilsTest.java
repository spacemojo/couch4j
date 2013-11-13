package com.standardstate.couch4j.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

public class UtilsTest {
    
    @Test
    public void removeRevTest() {
        
        final String test01 = "{\"_id\":\"1234567890\", \"_rev\":\"123\", \"name\":\"something\"}";
        
        final Pattern pattern = Pattern.compile("^.?");
        final Matcher matcher = pattern.matcher(test01);
        System.out.println(matcher.find());
        
    }
    
}
