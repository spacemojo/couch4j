package com.standardstate.couch4j;

import com.standardstate.couch4j.options.AllDocumentsOptions;
import com.standardstate.couch4j.response.AllDocuments;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.net.HttpURLConnection;
import java.net.URL;
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
    
    public static <T> List<T> getAllDocuments(final Session session, final Class documentClass, final AllDocumentsOptions... options) {
        
        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session) + Constants.ALL_DOCUMENTS + ((options != null && options.length > 0)  ? Utils.toQueryString(options[0]) : ""));
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setGETMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        final Map docs = (Map)Utils.readInputStream(couchdbConnection, Object.class);
        final AllDocuments allDocuments = new AllDocuments();
        allDocuments.setTotalRows((Integer)docs.get(Constants.TOTAL_ROWS));
        allDocuments.setOffset((Integer)docs.get(Constants.OFFSET));
        allDocuments.setRows((List)docs.get(Constants.ROWS));
        allDocuments.setOptions((options.length > 0 ? options[0] : null));
        
        return null;
            
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
