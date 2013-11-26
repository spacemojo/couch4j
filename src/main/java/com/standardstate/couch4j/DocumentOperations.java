package com.standardstate.couch4j;

import com.standardstate.couch4j.options.AllDocumentsOptions;
import com.standardstate.couch4j.response.AllDocuments;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;

public class DocumentOperations {
    
    private final static String ALL_DOCUMENTS = "/_all_docs";
    
    private final static String TOTAL_ROWS = "total_rows";
    private final static String OFFSET = "offset";
    private final static String ROWS = "rows";
    
    public static OperationResponse createDocumentWithId(final Session session, final Object toCreate, final String id) {
        
        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session) + "/" + id);
        final HttpURLConnection couchdbConnection = openURLConnection(couchdbURL);

        Utils.setPUTMethod(couchdbConnection);
        Utils.setJSONContentHeader(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        writeToConnection(couchdbConnection, Utils.removeRev(Utils.objectToJSON(toCreate)));

        return readInputStream(couchdbConnection, OperationResponse.class);
        
    }
    
    private static void writeToConnection(final HttpURLConnection couchdbConnection, final String content) {
        try {
            couchdbConnection.setDoOutput(true);
            final DataOutputStream wr = new DataOutputStream(couchdbConnection.getOutputStream());
            wr.writeBytes(content);
            wr.flush();
            wr.close();
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    private static HttpURLConnection openURLConnection(final URL couchdbURL) {
        try {
            return (HttpURLConnection)couchdbURL.openConnection();
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    private static <T> T readInputStream(final HttpURLConnection couchdbConnection, final Class targetClass) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return (T)mapper.readValue(couchdbConnection.getInputStream(), targetClass);
        } catch(IOException e) {
            throw parseError(couchdbConnection, e);
        }
    }
    
    private static RuntimeException parseError(final HttpURLConnection connection, final Exception cause) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final Map error = (Map)mapper.readValue(connection.getErrorStream(), Object.class);
            return new RuntimeException("{\"responseCode\":" + connection.getResponseCode() + "," +
                    "\"responseMessage\":\"" + connection.getResponseMessage() + "\"," +
                    "\"error\":\"" + error.get("error") + "\"," + 
                    "\"reason\":\"" + error.get("reason") + "\"," + 
                    "\"method\":\"" + connection.getRequestMethod() + "\"}", cause);
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    public static OperationResponse createDocument(final Session session, final Object toCreate) {
        
        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session));
        final HttpURLConnection couchdbConnection = openURLConnection(couchdbURL);

        Utils.setPOSTMethod(couchdbConnection);
        Utils.setJSONContentHeader(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        writeToConnection(couchdbConnection, Utils.removeId(Utils.removeRev(Utils.objectToJSON(toCreate))));

        return readInputStream(couchdbConnection, OperationResponse.class);
        
    } 
    
    public static AllDocuments getAllDocuments(final Session session, final AllDocumentsOptions... options) {
        
        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session) + ALL_DOCUMENTS + ((options != null && options.length > 0)  ? Utils.toQueryString(options[0]) : ""));
        final HttpURLConnection couchdbConnection = openURLConnection(couchdbURL);

        Utils.setGETMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        final Map docs = (Map)readInputStream(couchdbConnection, Object.class);

        final AllDocuments allDocuments = new AllDocuments();
        allDocuments.setTotalRows((Integer)docs.get(TOTAL_ROWS));
        allDocuments.setOffset((Integer)docs.get(OFFSET));
        allDocuments.setRows((List)docs.get(ROWS));
        allDocuments.setOptions((options.length > 0 ? options[0] : null));
        return allDocuments;
            
    }
    
    public static <T> T getDocument(final Session session, final String id, final Class documentClass) {
        
        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session) + "/" + id);
        final HttpURLConnection couchdbConnection = openURLConnection(couchdbURL);

        Utils.setGETMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        return (T)readInputStream(couchdbConnection, documentClass);
        
    }
    
    public static <T> T getDocumentsByKeys(final Session session, final List<String> keys, final Class documentClass) {
        try {
            
            final URL couchdbURL = new URL(Utils.createDocumentURL(session) + ALL_DOCUMENTS);
            final HttpURLConnection couchdbConnection = (HttpURLConnection)couchdbURL.openConnection();
            
            Utils.setPOSTMethod(couchdbConnection);
            Utils.setAuthenticationHeader(couchdbConnection, session);
            
            couchdbConnection.setDoOutput(true);
            final StringBuilder builder = new StringBuilder("{\"keys\":");
            builder.append(Utils.objectToJSON(keys));
            builder.append("}");
            final DataOutputStream wr = new DataOutputStream(couchdbConnection.getOutputStream());
            wr.writeBytes(builder.toString());
            wr.flush();
            wr.close();
            
            final Map docs = (Map)readInputStream(couchdbConnection, Object.class);
            
            return null;
            
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static OperationResponse deleteDocument(final Session session, final String id, final String revision) {
        
        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session) + "/" + id + "?rev=" + revision);
        final HttpURLConnection couchdbConnection = openURLConnection(couchdbURL);

        Utils.setDELETEMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        return readInputStream(couchdbConnection, OperationResponse.class);
        
    }
    
}
