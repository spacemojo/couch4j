package com.standardstate.couch4j;

import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.test.TestDocument;
import com.standardstate.couch4j.util.ConstrainedMap;
import java.util.HashMap;
import org.joda.time.DateTime;
import static org.junit.Assert.assertTrue;

public class BaseTest {

    public static final String TEST_DATABASE_NAME = "c4jtest";

    public static Session newTestSession() {

        final Session session = new Session();
        session.setDatabase(TEST_DATABASE_NAME);
        session.setHost("http://127.0.0.1");
        session.setPassword("nimda");
        session.setPort(Constants.DEFAULT_PORT);
        session.setUsername("admin");
        return session;

    }
    
    public static void bulkInsert() {
        final DocumentOperations docOps = new DocumentOperations(BaseTest.newTestSession());
        for (int i = 0 ; i < 10 ; i++) {
            final TestDocument doc = new TestDocument();
            doc.setActive((i % 2 ==0));
            doc.setDate(DateTime.now());
            doc.setIntValue(i);
            doc.setName("This is document #" + i);
            
            final OperationResponse response = docOps.createDocument(doc);
            assertTrue("BaseTest.bulkInsert", response.isOk());
            
            final OperationResponse responseWithId = docOps.createDocumentWithId(doc, "doc-" + i);
            assertTrue("BaseTest.bulkInsert", responseWithId.isOk());
            
        }
    }

    public static void createDesignDocument() {
        
        final DesignDocument designDoc = new DesignDocument();
        designDoc.set_id("_design/testdocs");
        designDoc.setLanguage("javascript");
        designDoc.setViews(new HashMap<>());
        
        final ConstrainedMap byActive = new ConstrainedMap();
        byActive.put("map", "function (doc) {\\n  emit(doc.active, doc);\\n}");
        
        designDoc.getViews().put("by_active", byActive);
        
        final DesignDocumentOperations desOps = new DesignDocumentOperations(BaseTest.newTestSession());
        desOps.createDesignDocument(designDoc);
        
    }
    
    public static void beforeTest() {
        final DatabaseOperations ops = new DatabaseOperations(BaseTest.newTestSession());
        final OperationResponse response = ops.createDatabase(TEST_DATABASE_NAME);
        assertTrue("DatabaseOperationsTest.beforeTest", response.isOk());
    }

    public static void afterTest() {
        final DatabaseOperations ops = new DatabaseOperations(BaseTest.newTestSession());
        final OperationResponse response = ops.deleteDatabase(TEST_DATABASE_NAME);
        assertTrue("DatabaseOperationsTest.afterTest", response.isOk());
    }
    
}
