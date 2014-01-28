package com.standardstate.couch4j;

import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.mock.MockObject;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.util.Date;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DesignDocumentOperationsTest extends BaseCouch4JTest {
 
    @BeforeClass
    public static void createDatabase() {
        DatabaseOperations.createDatabase(session, TEST_DATABASE_NAME);
        session.setDatabase(TEST_DATABASE_NAME);
    }
    
    @AfterClass
    public static void afterClass(){
        DatabaseOperations.deleteDatabase(session, TEST_DATABASE_NAME);
    }
    
    @Test
    public void createDesignDocumentTest() {
        
        final DesignDocument designDocument = new DesignDocument();
        designDocument.set_id("_design/users");
        
        Utils.appendMapFunctionToView(designDocument, "all", "function(doc){emit(doc._id, doc);}");
        Utils.appendMapFunctionToView(designDocument, "bydate", "function(doc){emit(doc.date, doc);}");
        Utils.appendMapFunctionToView(designDocument, "byname", "function(doc){emit(doc.name, doc);}");
        
        final OperationResponse operationResponse = DesignDocumentOperations.createDesignDocument(session, designDocument);
        assertTrue("createDesignDocumentTest(isOk)", operationResponse.isOk());
        assertEquals("createDesignDocumentTest(getId)", "_design/users", operationResponse.getId());
        
        final DesignDocument createdDesignDocument = DesignDocumentOperations.getDesignDocument(session, "users");
        assertEquals("createDesignDocumentTest(getId from fetched)", "_design/users", createdDesignDocument.get_id());
        
        // add a bunch of documents to fetch with the view
        for(int i = 0 ; i < 10 ; i++) {
            final MockObject mock = new MockObject();
            mock.setActive(Boolean.TRUE);
            mock.setDate(new Date( (new Date().getTime() - (10000 * i)) ));
            mock.setIntValue(i);
            mock.setName("Mock document " + i);
            DocumentOperations.createDocument(session, mock);
        }
        // call a view ...
        final List<MockObject> mockObjects = DesignDocumentOperations.callView(session, "_design/users", "all", MockObject.class);
        System.out.println("MockObjects : " + mockObjects.size());
        
    }
    
}
