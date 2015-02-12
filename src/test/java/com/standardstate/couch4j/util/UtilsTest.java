package com.standardstate.couch4j.util;

import com.standardstate.couch4j.mock.MockObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
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
    public void parseFileTest() throws IOException {
        
        final File file = new File("src/test/resources/com/standardstate/couch4j/dog.jpg");
        final byte[] fileBytes = Utils.parseFile(file);
        assertEquals("parseFileTest()", 128642, fileBytes.length);
        
        final String contentType = Files.probeContentType(file.toPath());
        assertEquals("Content-Type", "image/jpeg", contentType);
        
    }
    
    @Test(expected = RuntimeException.class)
    public void parseFileTestFailure() throws IOException {
        
        final File file = new File("src/test/resources/com/standardstate/couch4j/nothere.jpg");
        final byte[] fileBytes = Utils.parseFile(file);
        fail("This test should have failed " + fileBytes);
        
    }
    
    @Test
    public void objectToJSONSuccess() {
        
        final String jsonObj = Utils.objectToJSON(obj);
        assertEquals("removeRevTest", "{\"_id\":\"1029384756\",\"_rev\":\"1-1029384756\",\"type\":\"mock\",\"name\":\"MockObjectName\",\"intValue\":12,\"date\":\"" + obj.getDate().toString() + "\",\"active\":false}", jsonObj);
        
    }
    
    @Test
    public void constructorTest() {
        assertNotNull("constructorTest()", new Utils());
    }
    
    @Test(expected = RuntimeException.class)
    public void createURLFailure() {
        Utils.createURL("htp:/thisisamalformedurl");
    }

    @Test
    public void listToString() {
        
        final List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        
        assertEquals("listToString()", "1234", Utils.listToString(list));
        
    }
    
}
