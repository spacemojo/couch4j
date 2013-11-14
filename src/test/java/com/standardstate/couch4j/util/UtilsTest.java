package com.standardstate.couch4j.util;

import com.standardstate.couch4j.User;
import org.junit.Test;

public class UtilsTest {
    
    @Test
    public void removeRevTest() {
        
        final User user = new User();
        user.set_id("1234567890");
        user.setUsername("viande");
                
        final String jsonUser = Utils.objectToJSON(user);
        
        System.out.println(jsonUser + " -> " + Utils.removeRev(jsonUser));
        System.out.println("{\"_id\":\"1234567890\",\"_rev\":\"null\",\"username\":\"viande\"} -> " + Utils.removeRev("{\"_id\":\"1234567890\",\"_rev\":\"null\",\"username\":\"viande\"}"));
        
    }
    
}
