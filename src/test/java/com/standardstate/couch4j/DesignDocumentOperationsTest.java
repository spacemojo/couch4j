package com.standardstate.couch4j;

import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.design.MapView;
import com.standardstate.couch4j.response.OperationResponse;
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
        designDocument.set_id("users");
        
        final MapView view = new MapView();
        view.setName("_all");
        view.setMap("function(){emit(doc, null);}");
        designDocument.getViews().add(view);
        
        final OperationResponse createDesignDocument = DesignDocumentOperations.createDesignDocument(session, designDocument);
        assertTrue("createDesignDocumentTest(isOk)", createDesignDocument.isOk());
        assertEquals("createDesignDocumentTest(getId)", "_design/users", createDesignDocument.getId());
        
        final DesignDocument createdDesignDocument = DesignDocumentOperations.getDesignDocument(session, "users");
    
    }
    
}
