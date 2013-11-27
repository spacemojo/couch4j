package com.standardstate.couch4j.util;

import com.standardstate.couch4j.mock.MockObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class UtilsTest {
    
    private final MockObject obj = new MockObject();
    
    @Before
    public void setMockObject() {
        obj.set_id("1029384756");
        obj.set_rev("1-1029384756");
        obj.setIntValue(12);
        obj.setName("MockObjectName");
    }
    
    @Test
    public void objectToJSONSuccess() {
        
        final String jsonObj = Utils.objectToJSON(obj);
        assertEquals("removeRevTest", "{\"_id\":\"1029384756\",\"_rev\":\"1-1029384756\",\"name\":\"MockObjectName\",\"intValue\":12}", jsonObj);
        
    }
    
    @Test
    public void removeRevTest() {
        
        
        
        
        // assertEquals("removeRevTest", "{\"_id\":\"1029384756\",\"name\":\"MockObjectName\",\"intValue\":12}", Utils.removeRev(jsonObj));
        
    }
    
    @Test
    public void removeIdTest() {
        
        final MockObject obj = new MockObject();
        obj.set_id("1029384756");
        obj.set_rev("1-1029384756");
        obj.setIntValue(12);
        obj.setName("MockObjectName");
        
        final String jsonObj = Utils.objectToJSON(obj);
        
        assertEquals("removeRevTest", "{\"_id\":\"1029384756\",\"_rev\":\"1-1029384756\",\"name\":\"MockObjectName\",\"intValue\":12}", jsonObj);
        assertEquals("removeRevTest", "{\"_rev\":\"1-1029384756\",\"name\":\"MockObjectName\",\"intValue\":12}", Utils.removeId(jsonObj));
        
    }
    
}
