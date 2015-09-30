package com.standardstate.couch4j;

import static com.standardstate.couch4j.BaseCouch4JTest.TEST_DATABASE_NAME;
import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.design.ValidationDocument;
import com.standardstate.couch4j.mock.MockObject;
import com.standardstate.couch4j.options.Options;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.io.IOException;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class DesignDocumentOperationsTest extends BaseCouch4JTest {

    @BeforeClass
    @Ignore
    public static void createTestDatabase() {
        final OperationResponse createResponse = DatabaseOperations.createDatabase(TEST_DATABASE_NAME);
        assertEquals("createTestDatabase", true, createResponse.isOk());
    }

    @AfterClass
    public static void deleteTestDatabase() {
        final OperationResponse deleteResponse = DatabaseOperations.deleteDatabase(TEST_DATABASE_NAME);
        assertEquals("deleteTestDatabase", true, deleteResponse.isOk());
    }

    @Test
    @Ignore
    public void constructorTest() {
        assertNotNull("constructorTest()", new DesignDocumentOperations());
    }

    public void createDocuments() throws IOException {

        for (int i = 0; i < 10; i++) {
            final MockObject mock = new MockObject();
            mock.setActive(Boolean.TRUE);
            mock.setDate(new DateTime());
            mock.setIntValue(i);
            mock.setName("Mock document " + i);
            DocumentOperations.createDocument(mock);
        }

    }

    @Test
    @Ignore
    public void createDesignDocumentTest() throws IOException {

        final DesignDocument designDocument = new DesignDocument();
        designDocument.set_id("_design/users");

        Utils.appendMapFunctionToView(designDocument, "all", "function(doc){emit(doc._id, doc);}");
        Utils.appendMapFunctionToView(designDocument, "bydate", "function(doc){emit(doc.date, doc);}");
        Utils.appendMapFunctionToView(designDocument, "byname", "function(doc){emit(doc.name, doc);}");

        final OperationResponse operationResponse = DesignDocumentOperations.createDesignDocument(designDocument);

        createDocuments();
        
        assertTrue("createDesignDocumentTest(isOk)", operationResponse.isOk());
        assertEquals("createDesignDocumentTest(getId)", "_design/users", operationResponse.getId());

        final DesignDocument createdDesignDocument = DesignDocumentOperations.getDesignDocument("users");
        assertEquals("createDesignDocumentTest(getId from fetched)", "_design/users", createdDesignDocument.get_id());

        // call a view ...
        final List<MockObject> mockObjects = DesignDocumentOperations.callView("_design/users", "byname", MockObject.class);
        System.out.println("MockObjects : " + mockObjects.size());

        final Options options = new Options();
        options.setKey("Mock document 3");
        final List<MockObject> calledWithParameters = DesignDocumentOperations.callView("_design/users", "byname", MockObject.class, options);
        assertEquals("calledWithParameters", "Mock document 3", calledWithParameters.get(0).getName());
        
        final List<DesignDocument> allDesignDocuments = DesignDocumentOperations.getAllDesignDocuments();
        assertEquals("allDesignDocuments", 2, allDesignDocuments.size());
        
    }

    @Test
    @Ignore
    public void createValidationDocumentTest() {

        final ValidationDocument validation = new ValidationDocument();
        validation.set_id("_design/uservalidation");
        validation.setValidate_doc_update("function(newDoc, oldDoc, usrCtx){if(!newDoc.type){throw { \"forbidden\":\"Documents need a type\" };}}");

        final OperationResponse createResponse = DesignDocumentOperations.createValidationDocument(validation);
        assertTrue("createValidationDocumentTest()", createResponse.isOk());

    }

}
