package com.standardstate.couch4j;

import static com.standardstate.couch4j.BaseTest.TEST_DATABASE_NAME;
import com.standardstate.couch4j.options.Options;
import com.standardstate.couch4j.response.AllDocuments;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.test.TestDocument;
import org.junit.After;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static org.junit.Assert.assertFalse;

public class DocumentOperationsTest {

    @Before
    public void beforeTest() {
        BaseTest.beforeTest();
        BaseTest.bulkInsert();
    }

    @After
    public void afterTest() {
        BaseTest.afterTest();
    }

    @Test
    public void constructorTest() {

        final DocumentOperations operations = new DocumentOperations(BaseTest.newTestSession());
        operations.setSession(BaseTest.newTestSession());
        assertEquals("constructorTest", TEST_DATABASE_NAME, operations.getSession().getDatabase());

    }

    @Test
    public void getAllDocumentsTest() {

        final DocumentOperations docOps = new DocumentOperations(BaseTest.newTestSession());
        AllDocuments allDocuments = docOps.getAllDocuments();
        assertEquals("getAllDocumentsTest", Integer.valueOf(20), allDocuments.getTotalRows());

        final Options options = new Options();
        options.setDescending(Boolean.TRUE);
        options.setIncludeDocs(Boolean.FALSE);
        options.setLimit(3);

        allDocuments = docOps.getAllDocuments(options);
        assertEquals("getAllDocumentsTest", options.getLimit(), Integer.valueOf(allDocuments.getRows().size()));

    }

    @Test
    public void getDocumentTest() {

        final DocumentOperations docOps = new DocumentOperations(BaseTest.newTestSession());
        final TestDocument document = docOps.getDocument("doc-2", TestDocument.class);
        assertEquals("getDocumentTest", "This is document #2", document.getName());
        assertEquals("getDocumentTest", "doc-2", document.get_id());

    }
    
    @Test
    public void getDocumentFailureTest() {
        
        final DocumentOperations docOps = new DocumentOperations(BaseTest.newTestSession());
        final TestDocument document = docOps.getDocument("viande", TestDocument.class);
        assertEquals("getDocumentFailureTest", "not_found", document.getError());
        assertEquals("getDocumentFailureTest", "missing", document.getReason());
        
    }

    @Test
    public void updateDocumentTest() {

        final DocumentOperations docOps = new DocumentOperations(BaseTest.newTestSession());
        final TestDocument document = docOps.getDocument("doc-2", TestDocument.class);

        document.setName(document.getName() + " UPDATED");
        final OperationResponse updateDocument = docOps.updateDocument(document);
        assertTrue("updateDocumentTest", updateDocument.isOk());
        assertNotEquals("updateDocumentTest", document.get_rev(), updateDocument.getRev());

    }

    @Test
    public void deleteDocumentTest() {

        final DocumentOperations docOps = new DocumentOperations(BaseTest.newTestSession());
        final TestDocument document = docOps.getDocument("doc-2", TestDocument.class);
        final OperationResponse deleteDocument = docOps.deleteDocument(document);

        assertTrue("deleteDocumentTest", deleteDocument.isOk());
        assertEquals("deleteDocumentTest", Integer.valueOf(19), docOps.getAllDocuments().getTotalRows());

    }

    @Test
    public void attachmentTest() {

        final DocumentOperations docOps = new DocumentOperations(BaseTest.newTestSession());
        final TestDocument startDocument = docOps.getDocument("doc-2", TestDocument.class);
        final File attachment = readFromClasspath();
        
        final OperationResponse addAttachment = docOps.addAttachment(startDocument, attachment);
        assertTrue("attachmentTest", addAttachment.isOk());
        
        final TestDocument docWithAttachments = docOps.getDocument("doc-2", TestDocument.class);
        docWithAttachments.get_attachments().entrySet().forEach((entry) -> {
            System.out.println("Name : " + entry.getKey());
            System.out.println("Length : " + entry.getValue().getLength());
            System.out.println("IsStub : " + entry.getValue().isStub());
            System.out.println("RevPos : " + entry.getValue().getRevpos());
            System.out.println("Digest : " + entry.getValue().getDigest());
            System.out.println(" ---------- \n\n");
        });
        
        docWithAttachments.set_rev("pwelle");
        final OperationResponse deleteAttachment = docOps.deleteAttachment(docWithAttachments, attachment.getName());
        assertFalse("attachmentTest", deleteAttachment.isOk());
        
        docWithAttachments.set_rev(addAttachment.getRev());
        final OperationResponse delete = docOps.deleteAttachment(docWithAttachments, attachment.getName());
        assertTrue("attachmentTest", delete.isOk());
        
    }

    private File readFromClasspath() {

        InputStream is = null;
        FileOutputStream out = null;
        File file = null;
        
        try {

            final ClassLoader loader = getClass().getClassLoader();
            is = loader.getResourceAsStream("com/standardstate/couch4j/dog.jpg");
            file = File.createTempFile("unittest", ".jpg");
            
            out = new FileOutputStream(file);
            int read;
            byte[] buffer = new byte[1024];
            
            while((read = is.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            safeClose(is);
            safeClose(out);
        }

        return file;

    }
    
    private void safeClose(InputStream is) {
        if(is != null) {
            try {
                is.close();
            } catch(IOException e) {
                // nothing to do here ... 
            }
        }
    }
    
    private void safeClose(FileOutputStream out) {
        if(out != null) {
            try {
                out.close();
            } catch(IOException e) {
                // nothing to do here ... 
            }
        }
    }

}
