package com.standardstate.couch4j;

import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.test.TestDocument;
import java.io.IOException;
import org.joda.time.DateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class DocumentOperationsTest extends BaseTest {

  private static final String MOCK_BEAN_NAME = "This is the name of the mock bean ... ";

  @Test
  public void constructorTest() {
    
    final DocumentOperations operations = new DocumentOperations(session);
    assertNotNull("constructorTest()", operations);
    
    operations.setSession(new Session());
    operations.getSession().setDatabase(TEST_DATABASE_NAME);
    assertEquals("constructorTest", TEST_DATABASE_NAME, operations.getSession().getDatabase());
    
  }

  @Test
  public void createAndDeleteDocumentWithId() {

    final DocumentOperations documentOperations = new DocumentOperations(session);
    final DateTime now = new DateTime();
    final TestDocument mock = new TestDocument();
    mock.setActive(Boolean.TRUE);
    mock.setDate(now);
    mock.setIntValue(12);
    mock.setName(MOCK_BEAN_NAME);
    mock.set_id("1029384756");

    final OperationResponse create = documentOperations.createDocumentWithId(mock, mock.get_id());
    mock.set_rev(create.getRev());
    assertTrue("createAndDeleteDocumentWithId", create.isOk());

    final TestDocument fetched = documentOperations.getDocument(mock.get_id(), TestDocument.class);
    assertEquals("createAndDeleteDocumentWithId", mock, fetched);

    final OperationResponse deleteDocument = documentOperations.deleteDocument(mock);
    assertTrue("createAndDeleteDocumentWithId", deleteDocument.isOk());

  }

  @Test
  public void createAndDeleteDocument() {

    final DocumentOperations documentOperations = new DocumentOperations(session);
    final DateTime now = new DateTime();
    final TestDocument mock = new TestDocument();
    mock.setActive(Boolean.TRUE);
    mock.setDate(now);
    mock.setIntValue(12);
    mock.setName(MOCK_BEAN_NAME);

    final OperationResponse createResponse = documentOperations.createDocument(mock);
    assertEquals("createAndDeleteDocument", true, createResponse.isOk());
    mock.set_id(createResponse.getId());
    mock.set_rev(createResponse.getRev());

    final TestDocument fetched = documentOperations.getDocument(mock.get_id(), TestDocument.class);
    assertEquals("createAndDeleteDocument", mock, fetched);

    final OperationResponse deleteDocument = documentOperations.deleteDocument(mock);
    assertTrue("createAndDeleteDocument", deleteDocument.isOk());

  }

  @Test(expected = Couch4JException.class)
  public void getNonExistingDocument() {

    final DocumentOperations documentOperations = new DocumentOperations(session);
    final TestDocument document = documentOperations.getDocument("nonexisting", TestDocument.class);
    fail("This test should have failed ... " + document);

  }

  @Test
  public void createDocumentTwice() {

    final DocumentOperations documentOperations = new DocumentOperations(session);
    final DateTime now = new DateTime();
    final TestDocument mock = new TestDocument();
    mock.setActive(Boolean.TRUE);
    mock.setDate(now);
    mock.setIntValue(12);
    mock.setName(MOCK_BEAN_NAME);

    final OperationResponse createResponse = documentOperations.createDocument(mock);
    mock.set_id(createResponse.getId());
    mock.set_rev(createResponse.getRev());

    final OperationResponse createTwiceResponse = documentOperations.createDocumentWithId(mock, mock.get_id());
    assertTrue("createDocumentTwice", createTwiceResponse.isOk());

  }

  @Test
  public void updateDocument() {

//        final DocumentOperations documentOperations = new DocumentOperations(ConfigurationManager.getSession());
//        final DateTime now = new DateTime();
//        final TestDocument mock = new TestDocument();
//        mock.setActive(Boolean.TRUE);
//        mock.setDate(now);
//        mock.setIntValue(12);
//        mock.setName(MOCK_BEAN_NAME);
//
//        final OperationResponse createResponse = documentOperations.createDocument(mock);
//        mock.set_id(createResponse.getId());
//        mock.set_rev(createResponse.getRev());
//
//        mock.setName("This is an UPDATED name .. ");
//        final OperationResponse updateResponse = documentOperations.updateDocument(mock);
//        assertTrue(updateResponse.isOk());
//
//        final TestDocument document = documentOperations.getDocument(mock.get_id(), TestDocument.class);
//        assertEquals("updateDocument()", document.get_id(), mock.get_id());
//        assertEquals("updateDocument()", document.getName(), mock.getName());
  }

  @Test
  public void attachmentTest() throws IOException {

//        final DocumentOperations documentOperations = new DocumentOperations(ConfigurationManager.getSession());
//        final DateTime now = new DateTime();
//        final TestDocument mock = new TestDocument();
//        mock.setActive(Boolean.TRUE);
//        mock.setDate(now);
//        mock.setIntValue(12);
//        mock.setName(MOCK_BEAN_NAME);
//
//        final OperationResponse createResponse = documentOperations.createDocument(mock);
//
//        final TestDocument fetched = documentOperations.getDocument(createResponse.getId(), TestDocument.class);
//
//        final File file = new File("src/test/resources/com/standardstate/couch4j/dog.jpg");
//
//        final OperationResponse addResponse = documentOperations.addAttachment(fetched, file);
//        assertTrue("addResponse", addResponse.isOk());
//
//        final TestDocument withAttachments = documentOperations.getDocument(createResponse.getId(), TestDocument.class);
//        assertEquals("fetchedWithAttachments", 1, withAttachments.get_attachments().size());
//
//        final Attachment attachment = withAttachments.get_attachments().get("dog.jpg");
//        assertEquals("ContentType", "image/jpeg", attachment.getContent_type());
//        assertEquals("Length", 128642, attachment.getLength());
//        assertEquals("Revpos", 2, attachment.getRevpos());
//        assertTrue("Stub", attachment.isStub());
//
//        withAttachments.setName("Yet another name ... ");
//        documentOperations.updateDocument(withAttachments);
//
//        final TestDocument modifiedWithAttachments = documentOperations.getDocument(createResponse.getId(), TestDocument.class);
//        final OperationResponse deleteResponse = documentOperations.deleteAttachment(modifiedWithAttachments, "dog.jpg");
//        assertTrue("deleteResponse", deleteResponse.isOk());
  }

  @Test
  public void getAllDocuments() throws IOException {

//        final DocumentOperations documentOperations = new DocumentOperations(ConfigurationManager.getSession());
//        final int startsize = documentOperations.getAllDocuments().getRows().size();
//
//        for (int i = 0; i < 100; i++) {
//            final TestDocument mock = new TestDocument();
//            mock.setActive(Boolean.TRUE);
//            mock.setDate(new DateTime());
//            mock.setIntValue(i);
//            mock.setName(MOCK_BEAN_NAME + i);
//            documentOperations.createDocument(mock);
//        }
//
//        final AllDocuments allDocuments = documentOperations.getAllDocuments();
//        assertEquals("getAllDocuments(no options - row size)", startsize + 100, allDocuments.getRows().size());
//        assertEquals("getAllDocuments(no options - total row size)", new Integer(startsize + 100), allDocuments.getTotalRows());
//        assertEquals("getAllDocuments(no options - offset)", new Integer(0), allDocuments.getOffset());
//
//        final Options options = new Options();
//        options.setLimit(12);
//        options.setDescending(Boolean.TRUE);
//        final AllDocuments twelveDocuments = documentOperations.getAllDocuments(options);
//        assertEquals("twelveDocuments", 12, twelveDocuments.getRows().size());
  }

}
