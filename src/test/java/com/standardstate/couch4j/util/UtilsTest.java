package com.standardstate.couch4j.util;

import com.standardstate.couch4j.mock.MockObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class UtilsTest {
    
    @Test
    public void removeRevTest() {
        
        final MockObject obj = new MockObject();
        obj.set_id("1029384756");
        obj.set_rev("1-1029384756");
        obj.setIntValue(12);
        obj.setName("MockObjectName");
        
        final String jsonUser = Utils.objectToJSON(obj);
        
        assertEquals("", "", Utils.removeRev(jsonUser));
        
    }
    
}
