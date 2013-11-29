package com.standardstate.couch4j;

import com.standardstate.couch4j.mock.MockObject;
import com.standardstate.couch4j.options.AllDocumentsOptions;
import com.standardstate.couch4j.response.AllDocuments;
import com.standardstate.couch4j.response.OperationResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import org.codehaus.jackson.map.ObjectMapper;
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
        
        final Date now = new Date();
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
        
        //final AllDocuments allDocuments = DocumentOperations.getAllDocuments(session);
        //assertEquals("createAndDeleteDocumentWithId", new Integer(0).intValue(), allDocuments.getTotalRows().intValue());
        
    }
    
    @Test
    public void createAndDeleteDocument() {
        
        final Date now = new Date();
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
    public void createDocumentTwice() {
        
        final Date now = new Date();
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
    public void getAllDocuments() throws IOException {
    
        for(int i = 0 ; i < 100 ; i++) {
            final MockObject mock = new MockObject();
            mock.setActive(Boolean.TRUE);
            mock.setDate(new Date());
            mock.setIntValue(i);
            mock.setName("This is the name of the mock bean ... " + i);
            System.out.println(DocumentOperations.createDocument(session, mock));
        } 
        
//        final AllDocumentsOptions options = new AllDocumentsOptions();
//        options.setIncludeDocs(Boolean.TRUE);
//        options.setLimit(3);
//        final AllDocuments allDocuments = DocumentOperations.getAllDocuments(session, options);
//        for(Object row : allDocuments.getRows()) {
//            final LinkedHashMap rowMap = (LinkedHashMap)row;
//            final LinkedHashMap documentMap = (LinkedHashMap)rowMap.get("doc");
//            final ObjectMapper mapper = new ObjectMapper();
//            final StringWriter stringWriter = new StringWriter();
//            mapper.writeValue(stringWriter, documentMap);
//            System.out.println(stringWriter.toString());
//            final MockObject mockObject = (MockObject)mapper.readValue(stringWriter.toString(), MockObject.class);
//            System.out.println("MOCK OBJECT " + mockObject.toString());
//        }
        
    }
    
}
