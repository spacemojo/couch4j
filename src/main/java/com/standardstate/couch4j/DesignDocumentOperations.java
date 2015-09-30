package com.standardstate.couch4j;

import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.design.ValidationDocument;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DesignDocumentOperations {

    private final static String ENCODING = "UTF-8";
    private final static Session session = ConfigurationManager.getSession();
    
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
    
    public static <T> List<T> callView(final String designDocumentId, final String viewName, final Class documentClass, final Map<String, String> parameters) {
        
        final List<T> documents = new ArrayList<>();
        final String getDesignURL = Utils.createDesignDocumentURL(session, designDocumentId + "/_view/" + viewName + parametersToQueryString(parameters));
        
        final URL couchdbURL = Utils.createURL(getDesignURL);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);
        
        Utils.setGETMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);
        
        final Map docs = (Map)Utils.readInputStream(couchdbConnection, Object.class);
        Utils.parseGenericValues(docs, documents, documentClass);
        
        return documents;
        
    }
    
    public static Object getAllDesignDocuments() {
        
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("start_key", "_design/");
        parameters.put("end_key", "_design0/");
        final String getAllDesignDocsURL = Utils.createDocumentURL(session) + "/_all_docs" + parametersToQueryString(parameters);
        System.out.println(getAllDesignDocsURL);
        
        final URL couchdbURL = Utils.createURL(getAllDesignDocsURL);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);
        
        Utils.setGETMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);
        
        Utils.readInputStream(couchdbConnection, Object.class);
        
        return Utils.readInputStream(couchdbConnection, Object.class);
        
    }
    
    private static String parametersToQueryString(final Map<String, String> parameters) {

        if(parameters == null || parameters.isEmpty()) {
            return "";
        } else {
            final StringBuilder builder = new StringBuilder("?");
            parameters.keySet().stream().forEach((key) -> {
                builder.append(key).append("=\"").append(safeEncodeUTF8(parameters.get(key))).append("\"&");
            });
            return builder.toString().substring(0, (builder.length() - 1));
        }
        
    }
    
    public static String safeEncodeUTF8(final String toEncode, final String... encoding) {
        try {
            return URLEncoder.encode(toEncode, (encoding.length == 1 ? encoding[0] : ENCODING));
        } catch(UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    
}
