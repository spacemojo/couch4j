package com.standardstate.couch4j;

import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.design.ValidationDocument;
import com.standardstate.couch4j.options.Options;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.test.TestDocument;
import com.standardstate.couch4j.util.Utils;
import java.io.IOException;
import java.util.List;
import org.joda.time.DateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;

@Ignore 
public class DesignDocumentOperationsTest extends BaseTest {

  private static final String VIEW_BY_NAME = "byname";
  private static final String DESIGN_DOCUMENT_ID = "_design/users";

  @Test
  public void constructorTest() {
    
    final DesignDocumentOperations operations = new DesignDocumentOperations();
    assertNotNull("constructorTest()", operations);
    
    operations.setSession(new Session());
    operations.getSession().setDatabase(TEST_DATABASE_NAME);
    assertEquals("constructorTest", TEST_DATABASE_NAME, operations.getSession().getDatabase());
    
  }

  public void createDocuments() throws IOException {

    final DocumentOperations documentOperations = new DocumentOperations(new Session());
    for (int i = 0; i < 10; i++) {
      final TestDocument mock = new TestDocument();
      mock.setActive(Boolean.TRUE);
      mock.setDate(new DateTime());
      mock.setIntValue(i);
      mock.setName("Mock document " + i);
      documentOperations.createDocument(mock);
    }

  }

  @Test
  public void createDesignDocumentTest() throws IOException {

    final DesignDocumentOperations designDocumentOperations = new DesignDocumentOperations(new Session());

    final DesignDocument designDocument = new DesignDocument();
    designDocument.set_id(DESIGN_DOCUMENT_ID);

    Utils.appendMapFunctionToView(designDocument, "all", "function(doc){emit(doc._id, doc);}");
    Utils.appendMapFunctionToView(designDocument, "bydate", "function(doc){emit(doc.date, doc);}");
    Utils.appendMapFunctionToView(designDocument, VIEW_BY_NAME, "function(doc){emit(doc.name, doc);}");

    final OperationResponse operationResponse = designDocumentOperations.createDesignDocument(designDocument);

    createDocuments();

    assertTrue("createDesignDocumentTest(isOk)", operationResponse.isOk());
    assertEquals("createDesignDocumentTest(getId)", DESIGN_DOCUMENT_ID, operationResponse.getId());

    final DesignDocument createdDesignDocument = designDocumentOperations.getDesignDocument("users");
    assertEquals("createDesignDocumentTest(getId from fetched)", DESIGN_DOCUMENT_ID, createdDesignDocument.get_id());

    // call a view ...
    final List<TestDocument> mockObjects = designDocumentOperations.callView(DESIGN_DOCUMENT_ID, VIEW_BY_NAME, TestDocument.class);
    assertNotNull("MockObjects", mockObjects);

    final Options options = new Options();
    options.setKey("Mock document 3");
    final List<TestDocument> calledWithParameters = designDocumentOperations.callView(DESIGN_DOCUMENT_ID, VIEW_BY_NAME, TestDocument.class, options);
    assertEquals("calledWithParameters", "Mock document 3", calledWithParameters.get(0).getName());

    final List<DesignDocument> allDesignDocuments = designDocumentOperations.getAllDesignDocuments();
    assertEquals("allDesignDocuments", 2, allDesignDocuments.size());

  }

  @Test
  public void createValidationDocumentTest() {

    final DesignDocumentOperations designDocumentOperations = new DesignDocumentOperations(new Session());
    
    final ValidationDocument validation = new ValidationDocument();
    validation.set_id("_design/uservalidation");
    validation.setValidate_doc_update("function(newDoc, oldDoc, usrCtx){if(!newDoc.type){throw { \"forbidden\":\"Documents need a type\" };}}");

    final OperationResponse createResponse = designDocumentOperations.createValidationDocument(validation);
    assertTrue("createValidationDocumentTest()", createResponse.isOk());

  }

}
