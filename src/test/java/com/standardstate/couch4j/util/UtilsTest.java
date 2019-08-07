package com.standardstate.couch4j.util;

import com.standardstate.couch4j.Constants;
import com.standardstate.couch4j.Couch4JException;
import com.standardstate.couch4j.Session;
import com.standardstate.couch4j.design.DesignDocument;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UtilsTest {

    private Session newSession() {
        final Session session = new Session();
        session.setDatabase("unittests");
        session.setHost("http://127.0.0.1");
        session.setPassword("p@$$W0rd");
        session.setPort(Constants.DEFAULT_PORT);
        session.setUsername("nimda");
        return session;
    }

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

        final Session session = newSession();
        final String url = Utils.createDatabaseURL(session);
        assertEquals("createDatabaseURLTest", "http://127.0.0.1:5984/", url);

    }

    @Test
    public void createDocumentURLTest() {

        final Session session = newSession();
        final String url = Utils.createDocumentURL(session);
        assertEquals("createDOcumentURLTest", "http://127.0.0.1:5984/unittests", url);

    }

    @Test
    public void createDesignDocumentURLTest() {

        final Session session = newSession();
        final String url = Utils.createDesignDocumentURL(session, "_design/users");
        assertEquals("createDesignDocumentURLTest", "http://127.0.0.1:5984/unittests/_design/users", url);

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

    @Test(expected = Couch4JException.class)
    public void objectToJSONFailure() {

        final TestBean bine = mock(TestBean.class);
        when(bine.getName()).thenThrow(new RuntimeException());

        final String json = Utils.objectToJSON(bine);
        fail("Test should have failed : " + json);

    }

}
