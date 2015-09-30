package com.standardstate.couch4j;

import com.standardstate.couch4j.mock.MockObject;
import com.standardstate.couch4j.options.Options;
import com.standardstate.couch4j.response.AllDocuments;
import com.standardstate.couch4j.response.OperationResponse;
import java.io.File;
import java.io.IOException;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class DocumentOperationsTest extends BaseCouch4JTest {

    @BeforeClass
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
    public void constructorTest() {
        assertNotNull("constructorTest()", new DocumentOperations());
    }

    @Test
    public void createAndDeleteDocumentWithId() {

        final DateTime now = new DateTime();
        final MockObject mock = new MockObject();
        mock.setActive(Boolean.TRUE);
        mock.setDate(now);
        mock.setIntValue(12);
        mock.setName("This is the name of the mock bean ... ");
        mock.set_id("1029384756");

        final OperationResponse create = DocumentOperations.createDocumentWithId(mock, mock.get_id());
        mock.set_rev(create.getRev());
        assertTrue("createAndDeleteDocumentWithId", create.isOk());

        final MockObject fetched = DocumentOperations.getDocument(mock.get_id(), MockObject.class);
        assertEquals("createAndDeleteDocumentWithId", mock, fetched);

        final OperationResponse deleteDocument = DocumentOperations.deleteDocument(mock);
        assertTrue("createAndDeleteDocumentWithId", deleteDocument.isOk());

    }

    @Test
    public void createAndDeleteDocument() {

        final DateTime now = new DateTime();
        final MockObject mock = new MockObject();
        mock.setActive(Boolean.TRUE);
        mock.setDate(now);
        mock.setIntValue(12);
        mock.setName("This is the name of the mock bean ... ");

        final OperationResponse createResponse = DocumentOperations.createDocument(mock);
        assertEquals("createAndDeleteDocument", true, createResponse.isOk());
        mock.set_id(createResponse.getId());
        mock.set_rev(createResponse.getRev());

        final MockObject fetched = DocumentOperations.getDocument(mock.get_id(), MockObject.class);
        assertEquals("createAndDeleteDocument", mock, fetched);

        final OperationResponse deleteDocument = DocumentOperations.deleteDocument(mock);
        assertTrue("createAndDeleteDocument", deleteDocument.isOk());

    }

    @Test(expected = RuntimeException.class)
    public void getNonExistingDocument() {

        final MockObject document = DocumentOperations.getDocument("nonexisting", MockObject.class);
        fail("This test should have failed ... " + document);

    }

    @Test(expected = RuntimeException.class)
    public void createDocumentTwice() {

        final DateTime now = new DateTime();
        final MockObject mock = new MockObject();
        mock.setActive(Boolean.TRUE);
        mock.setDate(now);
        mock.setIntValue(12);
        mock.setName("This is the name of the mock bean ... ");

        final OperationResponse createResponse = DocumentOperations.createDocument(mock);
        mock.set_id(createResponse.getId());
        mock.set_rev(createResponse.getRev());

        final OperationResponse createTwiceResponse = DocumentOperations.createDocumentWithId(mock, mock.get_id());
        fail("This test shoudl have failed : " + createTwiceResponse.toString());

    }

    @Test
    public void updateDocument() {

        final DateTime now = new DateTime();
        final MockObject mock = new MockObject();
        mock.setActive(Boolean.TRUE);
        mock.setDate(now);
        mock.setIntValue(12);
        mock.setName("This is the name of the mock bean ... ");

        final OperationResponse createResponse = DocumentOperations.createDocument(mock);
        mock.set_id(createResponse.getId());
        mock.set_rev(createResponse.getRev());

        mock.setName("This is an UPDATED name .. ");
        final OperationResponse updateResponse = DocumentOperations.updateDocument(mock);
        assertTrue(updateResponse.isOk());

        final MockObject document = DocumentOperations.getDocument(mock.get_id(), MockObject.class);
        assertEquals("updateDocument()", document.get_id(), mock.get_id());
        assertEquals("updateDocument()", document.getName(), mock.getName());

    }

    @Test
    public void attachmentTest() throws IOException {

        final DateTime now = new DateTime();
        final MockObject mock = new MockObject();
        mock.setActive(Boolean.TRUE);
        mock.setDate(now);
        mock.setIntValue(12);
        mock.setName("This is the name of the mock bean ... ");

        final OperationResponse createResponse = DocumentOperations.createDocument(mock);

        final MockObject fetched = DocumentOperations.getDocument(createResponse.getId(), MockObject.class);

        final File file = new File("src/test/resources/com/standardstate/couch4j/dog.jpg");

        final OperationResponse addResponse = DocumentOperations.addAttachment(fetched, file);
        assertTrue("addResponse", addResponse.isOk());

        final MockObject withAttachments = DocumentOperations.getDocument(createResponse.getId(), MockObject.class);
        assertEquals("fetchedWithAttachments", 1, withAttachments.get_attachments().size());

        final Attachment attachment = withAttachments.get_attachments().get("dog.jpg");
        assertEquals("ContentType", "image/jpeg", attachment.getContent_type());
        assertEquals("Length", 128642, attachment.getLength());
        assertEquals("Revpos", 2, attachment.getRevpos());
        assertTrue("Stub", attachment.isStub());

        withAttachments.setName("Yet another name ... ");
        DocumentOperations.updateDocument(withAttachments);

        final MockObject modifiedWithAttachments = DocumentOperations.getDocument(createResponse.getId(), MockObject.class);
        final OperationResponse deleteResponse = DocumentOperations.deleteAttachment(modifiedWithAttachments, "dog.jpg");
        assertTrue("deleteResponse", deleteResponse.isOk());

    }

    @Test
    public void getAllDocuments() throws IOException {

        final int startsize = DocumentOperations.getAllDocuments().getRows().size();

        for (int i = 0; i < 100; i++) {
            final MockObject mock = new MockObject();
            mock.setActive(Boolean.TRUE);
            mock.setDate(new DateTime());
            mock.setIntValue(i);
            mock.setName("This is the name of the mock bean ... " + i);
            DocumentOperations.createDocument(mock);
        }

        final AllDocuments allDocuments = DocumentOperations.getAllDocuments();
        assertEquals("getAllDocuments", (startsize + 100), allDocuments.getRows().size());
        assertEquals("getAllDocuments", new Integer((startsize + 100)), allDocuments.getTotalRows());
        assertEquals("getAllDocuments", new Integer(0), allDocuments.getOffset());

        final Options options = new Options();
        options.setLimit(12);
        final AllDocuments twelveDocuments = DocumentOperations.getAllDocuments(options);
        assertEquals("twelveDocuments", 12, twelveDocuments.getRows().size());

    }

}
