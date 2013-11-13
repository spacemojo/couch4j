package com.standardstate.couch4j.util;

import com.standardstate.couch4j.Session;
import java.io.IOException;
import java.io.StringWriter;
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
        
        
        
        return json;
        
    }
    
    public static String createBaseURL(final Session session) {
        return "http://" + session.getUsername() + ":" + session.getPassword() + "@" + session.getHost() + ":" + session.getPort() + "/" + session.getDatabase();
    }
    
}
