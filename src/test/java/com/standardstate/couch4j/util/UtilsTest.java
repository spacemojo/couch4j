package com.standardstate.couch4j.util;

import com.standardstate.couch4j.mock.MockObject;
import java.util.Date;
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
        obj.setDate(new Date());
        obj.setType("mock");
    }
    
    @Test
    public void objectToJSONSuccess() {
        
        final String jsonObj = Utils.objectToJSON(obj);
        assertEquals("removeRevTest", "{\"_id\":\"1029384756\",\"_rev\":\"1-1029384756\",\"type\":\"mock\",\"name\":\"MockObjectName\",\"intValue\":12,\"date\":" + obj.getDate().getTime() + ",\"active\":false}", jsonObj);
        
    }
    
    @Test
    public void removeRevTest() {
        
        final String jsonObj = Utils.objectToJSON(obj);
        
        assertEquals("removeRevTest", "{\"_id\":\"1029384756\",\"_rev\":\"1-1029384756\",\"type\":\"mock\",\"name\":\"MockObjectName\",\"intValue\":12,\"date\":" + obj.getDate().getTime() + ",\"active\":false}", jsonObj);
        assertEquals("removeRevTest", "{\"_id\":\"1029384756\",\"type\":\"mock\",\"name\":\"MockObjectName\",\"intValue\":12,\"date\":" + obj.getDate().getTime() + ",\"active\":false}", Utils.removeRev(jsonObj));
        
    }
    
    @Test
    public void removeIdTest() {
        
        final String jsonObj = Utils.objectToJSON(obj);
        
        assertEquals("removeIdTest", "{\"_id\":\"1029384756\",\"_rev\":\"1-1029384756\",\"type\":\"mock\",\"name\":\"MockObjectName\",\"intValue\":12,\"date\":" + obj.getDate().getTime() + ",\"active\":false}", jsonObj);
        assertEquals("removeIdTest", "{\"_rev\":\"1-1029384756\",\"type\":\"mock\",\"name\":\"MockObjectName\",\"intValue\":12,\"date\":" + obj.getDate().getTime() + ",\"active\":false}", Utils.removeId(jsonObj));
        
    }
    
}
