package com.standardstate.couch4j;

import com.standardstate.couch4j.mock.MockObject;
import com.standardstate.couch4j.response.AllDocuments;
import com.standardstate.couch4j.response.OperationResponse;
import java.io.IOException;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class DocumentOperationsTest extends BaseCouch4JTest {

    @BeforeClass
    public static void createTestDatabase() {
        final OperationResponse createResponse = DatabaseOperations.createDatabase(session, TEST_DATABASE_NAME);
        assertEquals("createTestDatabase", true, createResponse.isOk());
        session.setDatabase(TEST_DATABASE_NAME);
    }

    @AfterClass
    public static void deleteTestDatabase() {
        final OperationResponse deleteResponse = DatabaseOperations.deleteDatabase(session, TEST_DATABASE_NAME);
        assertEquals("deleteTestDatabase", true, deleteResponse.isOk());
        session.setDatabase("");
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

        final OperationResponse create = DocumentOperations.createDocumentWithId(session, mock, mock.get_id());
        mock.set_rev(create.getRev());
        assertTrue("createAndDeleteDocumentWithId", create.isOk());

        final MockObject fetched = DocumentOperations.getDocument(session, mock.get_id(), MockObject.class);
        assertEquals("createAndDeleteDocumentWithId", mock, fetched);

        final OperationResponse deleteDocument = DocumentOperations.deleteDocument(session, mock.get_id(), mock.get_rev());
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

        final OperationResponse createResponse = DocumentOperations.createDocument(session, mock);
        assertEquals("createAndDeleteDocument", true, createResponse.isOk());
        mock.set_id(createResponse.getId());
        mock.set_rev(createResponse.getRev());

        final MockObject fetched = DocumentOperations.getDocument(session, mock.get_id(), MockObject.class);
        assertEquals("createAndDeleteDocument", mock, fetched);

        final OperationResponse deleteDocument = DocumentOperations.deleteDocument(session, mock.get_id(), mock.get_rev());
        assertTrue("createAndDeleteDocument", deleteDocument.isOk());

    }

    @Test(expected = RuntimeException.class)
    public void getNonExistingDocument() {

        final MockObject document = DocumentOperations.getDocument(session, "nonexisting", MockObject.class);
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

        final OperationResponse createResponse = DocumentOperations.createDocument(session, mock);
        mock.set_id(createResponse.getId());
        mock.set_rev(createResponse.getRev());

        final OperationResponse createTwiceResponse = DocumentOperations.createDocumentWithId(session, mock, mock.get_id());
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

        final OperationResponse createResponse = DocumentOperations.createDocument(session, mock);
        mock.set_id(createResponse.getId());
        mock.set_rev(createResponse.getRev());

        mock.setName("This is an UPDATED name .. ");
        final OperationResponse updateResponse = DocumentOperations.updateDocument(session, mock.get_id(), mock);
        assertTrue(updateResponse.isOk());

        final MockObject document = DocumentOperations.getDocument(session, mock.get_id(), MockObject.class);
        assertEquals("updateDocument()", document.get_id(), mock.get_id());
        assertEquals("updateDocument()", document.getName(), mock.getName());

    }

    @Test
    public void getAllDocuments() throws IOException {

        for (int i = 0; i < 100; i++) {
            final MockObject mock = new MockObject();
            mock.setActive(Boolean.TRUE);
            mock.setDate(new DateTime());
            mock.setIntValue(i);
            mock.setName("This is the name of the mock bean ... " + i);
            DocumentOperations.createDocument(session, mock);
        }

        final AllDocuments allDocuments = DocumentOperations.getAllDocuments(session);
        assertEquals("getAllDocuments", 101, allDocuments.getRows().size());

    }

}
