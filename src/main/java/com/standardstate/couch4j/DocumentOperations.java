package com.standardstate.couch4j;

import com.standardstate.couch4j.options.AllDocumentsOptions;
import com.standardstate.couch4j.response.AllDocuments;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DocumentOperations {
       
    public static OperationResponse createDocumentWithId(final Session session, final Object toCreate, final String id) {
        
        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session) + "/" + id);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setPUTMethod(couchdbConnection);
        Utils.setJSONContentHeader(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        Utils.writeToConnection(couchdbConnection, Utils.removeRev(Utils.objectToJSON(toCreate)));

        return Utils.readInputStream(couchdbConnection, OperationResponse.class);
        
    }
    
    public static OperationResponse createDocument(final Session session, final Object toCreate) {
        
        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session));
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setPOSTMethod(couchdbConnection);
        Utils.setJSONContentHeader(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        Utils.writeToConnection(couchdbConnection, Utils.removeId(Utils.removeRev(Utils.objectToJSON(toCreate))));

        return Utils.readInputStream(couchdbConnection, OperationResponse.class);
        
    } 
    
    public static AllDocuments getAllDocuments(final Session session) {
        return getAllDocuments(session, 0, Boolean.FALSE);
    }
    
    public static AllDocuments getAllDocuments(final Session session, final int limit) {
        return getAllDocuments(session, limit, Boolean.FALSE);
    }
    
    public static AllDocuments getAllDocuments(final Session session, final int limit, final boolean descending) {
        
        final AllDocumentsOptions options = Utils.initAllDocumentsOptions(limit, descending, Boolean.TRUE);
        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session) + Constants.ALL_DOCUMENTS + Utils.toQueryString(options));
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setGETMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        final Map docs = (Map)Utils.readInputStream(couchdbConnection, Object.class);
        final AllDocuments allDocuments = Utils.initAllDocuments(docs, options);
        
        for(Object row : (List)docs.get(Constants.ROWS)) {            
            final LinkedHashMap documentMap = (LinkedHashMap)((LinkedHashMap)row).get(Constants.DOC);
            final String jsonString = Utils.objectToJSON(documentMap);
            allDocuments.addRow(jsonString);
        }
        
        return allDocuments;
            
    }
    
    public static <T> T getDocument(final Session session, final String id, final Class documentClass) {
        
        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session) + "/" + id);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setGETMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        return (T)Utils.readInputStream(couchdbConnection, documentClass);
        
    }
    
    public static OperationResponse deleteDocument(final Session session, final String id, final String revision) {
        
        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session) + "/" + id + "?rev=" + revision);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setDELETEMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        return Utils.readInputStream(couchdbConnection, OperationResponse.class);
        
    }
    
}
