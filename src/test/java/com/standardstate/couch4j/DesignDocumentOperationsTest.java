package com.standardstate.couch4j;

import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.design.ValidationDocument;
import com.standardstate.couch4j.mock.MockObject;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DesignDocumentOperationsTest extends BaseCouch4JTest {

    @BeforeClass
    public static void createDatabase() {
        DatabaseOperations.createDatabase(TEST_DATABASE_NAME);
    }

    @AfterClass
    public static void afterClass() {
        DatabaseOperations.deleteDatabase(TEST_DATABASE_NAME);
    }

    @Test
    public void constructorTest() {
        assertNotNull("constructorTest()", new DesignDocumentOperations());
    }

    @Test
    public void createDesignDocumentTest() {

        final DesignDocument designDocument = new DesignDocument();
        designDocument.set_id("_design/users");

        Utils.appendMapFunctionToView(designDocument, "all", "function(doc){emit(doc._id, doc);}");
        Utils.appendMapFunctionToView(designDocument, "bydate", "function(doc){emit(doc.date, doc);}");
        Utils.appendMapFunctionToView(designDocument, "byname", "function(doc){emit(doc.name, doc);}");

        final OperationResponse operationResponse = DesignDocumentOperations.createDesignDocument(designDocument);
        assertTrue("createDesignDocumentTest(isOk)", operationResponse.isOk());
        assertEquals("createDesignDocumentTest(getId)", "_design/users", operationResponse.getId());

        final DesignDocument createdDesignDocument = DesignDocumentOperations.getDesignDocument("users");
        assertEquals("createDesignDocumentTest(getId from fetched)", "_design/users", createdDesignDocument.get_id());

        // add a bunch of documents to fetch with the view
        for (int i = 0; i < 10; i++) {
            final MockObject mock = new MockObject();
            mock.setActive(Boolean.TRUE);
            mock.setDate(new DateTime((new DateTime().getMillis() - (10000 * i))));
            mock.setIntValue(i);
            mock.setName("Mock document " + i);
            mock.setType("mock");
            DocumentOperations.createDocument(mock);
        }
        // call a view ...
        final List<MockObject> mockObjects = DesignDocumentOperations.callView("_design/users", "byname", MockObject.class);
        System.out.println("MockObjects : " + mockObjects.size());

        final Map<String, String> parameters = new HashMap<>();
        parameters.put(Constants.PARAM_KEY, "Mock document 3");
        final List<MockObject> calledWithParameters = DesignDocumentOperations.callView("_design/users", "byname", MockObject.class, parameters);
        System.out.println(calledWithParameters.get(0));

    }

    @Test
    public void createValidationDocumentTest() {

        final ValidationDocument validation = new ValidationDocument();
        validation.set_id("_design/uservalidation");
        validation.setValidate_doc_update("function(newDoc, oldDoc, usrCtx){if(!newDoc.type){throw { \"forbidden\":\"Documents need a type\" };}}");

        final OperationResponse createResponse = DesignDocumentOperations.createValidationDocument(validation);
        assertTrue("createValidationDocumentTest()", createResponse.isOk());

    }

    @Test(expected = RuntimeException.class)
    public void safeEncodeUTF8Test() {
        DesignDocumentOperations.safeEncodeUTF8(TEST_DATABASE_NAME, "UNKNOWN");
    }

}
