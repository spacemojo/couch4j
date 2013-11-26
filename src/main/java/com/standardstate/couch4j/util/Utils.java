package com.standardstate.couch4j.util;

import com.standardstate.couch4j.Constants;
import com.standardstate.couch4j.Session;
import com.standardstate.couch4j.options.AllDocumentsOptions;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;

public class Utils {

    private final static String REVISION_FIELD = "_rev";
    private final static String ID_FIELD = "_id";
    
    public static String objectToJSON(final Object object) {
        final ObjectMapper mapper = new ObjectMapper();
        final StringWriter writer = new StringWriter();
        try { 
            mapper.writeValue(writer, object);        
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        return writer.toString();
    }
    
    public static String removeRev(final String json) {
        return removeJSONField(json, REVISION_FIELD);        
    }
    
    public static String removeId(final String json) {
        return removeJSONField(json, ID_FIELD);        
    }
    
    public static String removeJSONField(final String json, final String fieldName) {
        
        final Pattern pattern = Pattern.compile("(\"" + fieldName + "\":\"[^\"]*[\"]?[,}]?)");
        
        final StringBuffer insideBuffer = new StringBuffer();        
        final Matcher insideMatcher = pattern.matcher(json.replace("\":null", "\":\"null\""));
        
        while(insideMatcher.find()) {
            insideMatcher.appendReplacement(insideBuffer, "");
        }
        
        insideMatcher.appendTail(insideBuffer);
        
        return insideBuffer.toString();
        
    }
    
    public static String createDatabaseURL(final Session session) {
        return "http://" + session.getHost() + ":" + session.getPort() + "/";
    }
    
    public static String createDocumentURL(final Session session) {
        return "http://" + session.getHost() + ":" + session.getPort() + "/" + session.getDatabase();
    }
    
    public static URL createURL(final String url) {
        try {
            return new URL(url);
        } catch(MalformedURLException mue) {
            throw new RuntimeException(mue);
        }
    }
    
    private static void setRequestMethod(final HttpURLConnection couchdbConnection, final String method) {
        try {
            couchdbConnection.setRequestMethod(method);
        } catch(ProtocolException pe) {
            throw new RuntimeException(pe);
        }
    }
    
    public static void setDELETEMethod(final HttpURLConnection couchdbConnection) {
        setRequestMethod(couchdbConnection, Constants.DELETE);
    }
    
    public static void setGETMethod(final HttpURLConnection couchdbConnection) {
        setRequestMethod(couchdbConnection, Constants.GET);
    }
    
    public static void setPOSTMethod(final HttpURLConnection couchdbConnection) {
        setRequestMethod(couchdbConnection, Constants.POST);
    }
    
    public static void setPUTMethod(final HttpURLConnection couchdbConnection) {
        setRequestMethod(couchdbConnection, Constants.PUT);
    }
    
    public static void setJSONContentHeader(final HttpURLConnection couchdbConnection) {
        setHeader(couchdbConnection, "Content-Type", "application/json");
    }
    
    public static void setAuthenticationHeader(final HttpURLConnection couchdbConnection, final Session session) {
        final String userCredentials = session.getUsername() + ":" + session.getPassword();
        final String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
        setHeader(couchdbConnection, "Authorization", basicAuth);
    }
    
    private static void setHeader(final HttpURLConnection couchdbConnection, final String headerName, final String headerValue) {
        couchdbConnection.setRequestProperty(headerName, headerValue);
    }
    
    public static String toQueryString(final AllDocumentsOptions options) {
        return "?descending=" + options.isDescending() + "&include_docs=" + options.isIncludeDocs() + (options.getLimit() > 0 ? "&limit=" + options.getLimit() : "");
    }
    
}
