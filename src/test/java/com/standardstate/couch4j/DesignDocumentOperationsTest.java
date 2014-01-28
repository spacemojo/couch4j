package com.standardstate.couch4j;

import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.design.MapView;
import com.standardstate.couch4j.response.OperationResponse;
import org.junit.Test;

public class DesignDocumentOperationsTest extends BaseCouch4JTest {

    @Test
    public void createDesignDocumentTest() {
        
        DatabaseOperations.createDatabase(session, TEST_DATABASE_NAME);
        session.setDatabase(TEST_DATABASE_NAME);
        
        final DesignDocument designDocument = new DesignDocument();
        designDocument.set_id("users");
        
        final MapView view = new MapView();
        view.setName("_all");
        view.setMap("function(){emit(doc, null);}");
        designDocument.getViews().add(view);
        
        final OperationResponse createDesignDocument = DesignDocumentOperations.createDesignDocument(session, designDocument);
        System.out.println(createDesignDocument);
        
        //DatabaseOperations.deleteDatabase(session, TEST_DATABASE_NAME);
        
    }
    
}
