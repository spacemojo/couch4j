package com.standardstate.couch4j;

import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.design.ValidationDocument;
import com.standardstate.couch4j.options.Options;
import com.standardstate.couch4j.response.AllDocuments;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DesignDocumentOperations {

    private static final Session session = ConfigurationManager.getSession();
    
    public static OperationResponse createDesignDocument(final DesignDocument document) {
        
        final String createDesignURL = Utils.createDesignDocumentURL(session, document.get_id());
        
        final URL couchdbURL = Utils.createURL(createDesignURL);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);
        
        Utils.setPUTMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);
        final String json = Utils.objectToJSONWithoutRev(document);
        Utils.writeToConnection(couchdbConnection, json);
        
        return Utils.readInputStream(couchdbConnection, OperationResponse.class);
        
    }
    
    public static OperationResponse createValidationDocument(final ValidationDocument document) {
        
        final String createDesignURL = Utils.createDesignDocumentURL(session, document.get_id());
        
        final URL couchdbURL = Utils.createURL(createDesignURL);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);
        
        Utils.setPUTMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);
        
        final String json = Utils.objectToJSON(document);
        Utils.writeToConnection(couchdbConnection, json);

        return Utils.readInputStream(couchdbConnection, OperationResponse.class);
        
    }
    
    public static DesignDocument getDesignDocument(final String documentName) {
        
        final String getDesignURL = Utils.createDesignDocumentURL(session, "_design/" + documentName);
        
        final URL couchdbURL = Utils.createURL(getDesignURL);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);
        
        Utils.setGETMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);
        
        return Utils.readInputStream(couchdbConnection, DesignDocument.class);
        
    }

    public static <T> List<T> callView(final String designDocumentId, final String viewName, final Class documentClass) {
        return callView(designDocumentId, viewName, documentClass, null);
    }
    
    public static <T> List<T> callView(final String designDocumentId, final String viewName, final Class documentClass, final Options options) {
        
        final List<T> documents = new ArrayList<>();
        final String getDesignURL = Utils.createDesignDocumentURL(session, designDocumentId + "/_view/" + viewName + Utils.toQueryString(options));
        
        final URL couchdbURL = Utils.createURL(getDesignURL);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);
        
        Utils.setGETMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);
        
        final Map docs = (Map)Utils.readInputStream(couchdbConnection, Object.class);
        Utils.parseGenericValues(docs, documents, documentClass);
        
        return documents;
        
    }
    
    public static List<DesignDocument> getAllDesignDocuments() {
        
        final Options options = new Options();
        options.setStartKey("_design/");
        options.setEndKey("_design0/");
        options.setIncludeDocs(Boolean.TRUE);
        
        final AllDocuments allDocuments = DocumentOperations.getAllDocuments(options);
        
        final List<DesignDocument> designDocuments = new ArrayList<>();
        for (String row : allDocuments.getRows()) {
            final DesignDocument designDocument = Utils.readString(row, DesignDocument.class);
            designDocuments.add(designDocument);
        }

        return designDocuments;
        
    }
    
}
