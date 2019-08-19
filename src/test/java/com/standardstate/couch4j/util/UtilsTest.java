package com.standardstate.couch4j.util;

import com.standardstate.couch4j.BaseTest;
import com.standardstate.couch4j.Couch4JException;
import com.standardstate.couch4j.Session;
import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.options.Options;
import java.net.URI;
import static org.junit.Assert.*;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void constructorTest() {
        assertNotNull("constructorTest", new Utils());
    }

    @Test 
    public void objectToJSONWithoutRevTest() {
        
        final DesignDocument doc = new DesignDocument();
        doc.set_rev("1029384756");
        
        final String json = Utils.objectToJSONWithoutRev(doc);
        assertFalse("objectToJSONWithoutRevTest(design doc)", json.contains("_rev"));
        
        final TestBean bine = new TestBean();
        bine.set_rev("1029384756");
        final String otherJson = Utils.objectToJSONWithoutRev(bine);
        assertFalse("objectToJSONWithoutRevTest(abstract doc)", otherJson.contains("_rev"));
        
    }
    
    @Test 
    public void objectToJSONWithoutIdTest() {
        
        final TestBean document = new TestBean();
        document.set_id("1029384756");
        document.set_rev("1029384756-hash");
        document.setId(12);
        document.setName("This is a name");
        final String json = Utils.objectToJSONWithoutId(document);
        assertFalse("objectToJSONWithoutIdTest", json.contains("_id"));
        
    }
    
    @Test
    public void createDatabaseURLTest() {

        final Session session = BaseTest.newTestSession();
        final String url = Utils.createDatabaseURL(session);
        assertEquals("createDatabaseURLTest", "http://127.0.0.1:5984/", url);

    }

    @Test
    public void createDocumentURLTest() {

        final Session session = BaseTest.newTestSession();
        final String url = Utils.createDocumentURL(session);
        assertEquals("createDOcumentURLTest", "http://127.0.0.1:5984/" + BaseTest.TEST_DATABASE_NAME, url);

    }

    @Test
    public void createDesignDocumentURLTest() {

        final Session session = BaseTest.newTestSession();
        final String url = Utils.createDesignDocumentURL(session, "_design/users");
        assertEquals("createDesignDocumentURLTest", "http://127.0.0.1:5984/" + BaseTest.TEST_DATABASE_NAME + "/_design/users", url);

    }

    @Test
    public void readStringTest() {

        final String jsonString = "{\"id\":12, \"name\":\"TestBine\"}";
        final TestBean bine = Utils.readString(jsonString, TestBean.class);
        assertEquals("readStringTest", 12, bine.getId());
        assertEquals("readStringTest", "TestBine", bine.getName());

    }

    @Test(expected = Couch4JException.class)
    public void readStringFailureTest() {

        final String jsonString = "{\"id\":12, \"name\":\"TestBine}";
        final TestBean bine = Utils.readString(jsonString, TestBean.class);
        fail("Test should have failed " + bine);

    }
    
    @Test
    public void buildURITest() {
        
        final Options options = new Options();
        options.setDescending(Boolean.TRUE);
        options.setEndKey("10");
        options.setIncludeDocs(Boolean.FALSE);
        options.setStartKey("1");
        options.setLimit(10);
        
        final String expectedQuery = "descending=true&limit=10&include_docs=false&startkey=\"1\"&endkey=\"10\"";
        final URI uri = Utils.buildURI("http://127.0.0.1:5984/" + BaseTest.TEST_DATABASE_NAME, options);
        assertEquals("buildURITest", expectedQuery, uri.getRawQuery().replace("%22", "\""));
        
    }
    
    @Test
    public void buildURIKeyOnlyTest() {
        
        final Options options = new Options();
        options.setKey("1029384756");
        
        final String expectedQuery = "key=\"1029384756\"";
        final URI uri = Utils.buildURI("http://127.0.0.1:5984/unittests", options);
        assertEquals("buildURIKeyOnlyTest", expectedQuery, uri.getRawQuery().replace("%22", "\""));
        
    }
    
    @Test(expected = Couch4JException.class)
    public void buildURIFailureTest() {
        
        final Options options = new Options();
        options.setKey("1029384756");
        
        final URI uri = Utils.buildURI("://127.0.0.1:5984/unittests", options);
        fail("buildURIFailureTest should have failed :" + uri);
        
    }
    
    @Test
    public void appendMapFunctionToViewTest() {
        
        final DesignDocument doc = new DesignDocument();
        Utils.appendMapFunctionToView(doc, "_design/users", "this is the function code");
        assertTrue("appendMapFunctionToViewTest", doc.getViews().size() == 1);
        Utils.appendMapFunctionToView(doc, "_design/contacts", "this is some other function code");
        assertTrue("appendMapFunctionToViewTest", doc.getViews().size() == 2);
        
    }

}
