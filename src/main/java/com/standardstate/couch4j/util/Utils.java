package com.standardstate.couch4j.util;

import com.standardstate.couch4j.Session;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;

public class Utils {

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
        
        final Pattern pattern = Pattern.compile("(\"_rev\"[\\s]*:[\\s]*[\"]?.*[\"]?,)");
        
        final StringBuffer insideBuffer = new StringBuffer();        
        final Matcher insideMatcher = pattern.matcher(json);
        
        while(insideMatcher.find()) {
            insideMatcher.appendReplacement(insideBuffer, "");
        }
        
        insideMatcher.appendTail(insideBuffer);
        
        return insideBuffer.toString();
        
    }
    
    public static String createSystemURL(final Session session) {
        return "http://" + session.getHost() + ":" + session.getPort() + "/";
    }
    
    public static String createBaseURL(final Session session) {
        return "http://" + session.getHost() + ":" + session.getPort() + "/" + session.getDatabase();
    }
    
    public static void setAuthenticationHeader(final HttpURLConnection couchdbConnection, final Session session) {
        final String userCredentials = session.getUsername() + ":" + session.getPassword();
        final String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
        couchdbConnection.setRequestProperty ("Authorization", basicAuth);
    }
    
}
