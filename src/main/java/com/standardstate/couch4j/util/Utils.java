package com.standardstate.couch4j.util;

import com.standardstate.couch4j.Session;
import java.io.IOException;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.codehaus.jackson.map.ObjectMapper;

public class Utils {

    private final static Pattern REV_INSIDE_PTN = Pattern.compile("(\"_rev\"[\\s]*:[\\s]*\".*\",)");
    private final static Pattern REV_END_PTN = Pattern.compile("(\"_rev\"[\\s]*:[\\s]*\".*\"})");
    
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
        
        final StringBuffer insideBuffer = new StringBuffer();        
        final Matcher insideMatcher = REV_INSIDE_PTN.matcher(json);
        while(insideMatcher.find()) {
            insideMatcher.appendReplacement(insideBuffer, "");
        }
        insideMatcher.appendTail(insideBuffer);
        
        return insideBuffer.toString();
        
    }
    
    public static String createBaseURL(final Session session) {
        return "http://" + session.getUsername() + ":" + session.getPassword() + "@" + session.getHost() + ":" + session.getPort() + "/" + session.getDatabase();
    }
    
}
