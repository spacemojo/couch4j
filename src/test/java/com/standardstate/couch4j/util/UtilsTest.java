package com.standardstate.couch4j.util;

import com.standardstate.couch4j.mock.MockObject;
import org.joda.time.DateTime;
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
        obj.setDate(new DateTime());
        obj.setType("mock");
    }
    
    @Test
    public void objectToJSONSuccess() {
        
        final String jsonObj = Utils.objectToJSON(obj);
        assertEquals("removeRevTest", "{\"_id\":\"1029384756\",\"_rev\":\"1-1029384756\",\"type\":\"mock\",\"name\":\"MockObjectName\",\"intValue\":12,\"date\":\"" + obj.getDate().toString() + "\",\"active\":false}", jsonObj);
        
    }
    
    @Test
    public void removeRevTest() {
        
        final String jsonObj = Utils.objectToJSON(obj);
        
        String expected = "{\"_id\":\"1029384756\",\"_rev\":\"1-1029384756\",\"type\":\"mock\",\"name\":\"MockObjectName\",\"intValue\":12,\"date\":\"" + obj.getDate().toString() + "\",\"active\":false}";
        System.out.println(expected);
        System.out.println(jsonObj);
        assertEquals("removeRevTest", expected, jsonObj);
        
        expected = "{\"_id\":\"1029384756\",\"type\":\"mock\",\"name\":\"MockObjectName\",\"intValue\":12,\"date\":\"" + obj.getDate().toString() + "\",\"active\":false}";
        System.out.println(expected);
        System.out.println(Utils.removeRev(jsonObj));
        assertEquals("removeRevTest", expected, Utils.removeRev(jsonObj));
        
    }
    
    @Test
    public void removeIdTest() {
        
        final String jsonObj = Utils.objectToJSON(obj);
        
        String expected = "{\"_id\":\"1029384756\",\"_rev\":\"1-1029384756\",\"type\":\"mock\",\"name\":\"MockObjectName\",\"intValue\":12,\"date\":\"" + obj.getDate().toString() + "\",\"active\":false}";
        System.out.println(expected);
        System.out.println(jsonObj);
        assertEquals("removeIdTest", expected, jsonObj);
        
        expected = "{\"_rev\":\"1-1029384756\",\"type\":\"mock\",\"name\":\"MockObjectName\",\"intValue\":12,\"date\":\"" + obj.getDate().toString() + "\",\"active\":false}";
        System.out.println(expected);
        System.out.println(Utils.removeId(jsonObj));
        assertEquals("removeIdTest", expected, Utils.removeId(jsonObj));
        
    }
    
    @Test
    public void constructorTest() {
        assertNotNull("constructorTest()", new Utils());
    }
    
    @Test(expected = RuntimeException.class)
    public void createURLFailure() {
        Utils.createURL("htp:/thisisamalformedurl");
    }
    
}
