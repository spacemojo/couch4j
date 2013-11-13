package com.standardstate.couch4j;

import com.standardstate.couch4j.response.DatabaseInformation;
import com.standardstate.couch4j.response.Create;
import com.standardstate.couch4j.response.Delete;
import com.standardstate.couch4j.util.Utils;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.codehaus.jackson.map.ObjectMapper;

public class DatabaseOperations {

    public static DatabaseInformation getDatabaseInformation(final Session session) {
        
        try {
        
            final URL couchdbURL = new URL(Utils.createBaseURL(session));
            final HttpURLConnection couchdbConnection = (HttpURLConnection)couchdbURL.openConnection();
            couchdbConnection.setRequestMethod(Constants.GET);
            
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(couchdbConnection.getInputStream(), DatabaseInformation.class);
            
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public static Create createDocumentWithId(final Session session, final Object toCreate, final String id) {
        
        try {
        
            final URL couchdbURL = new URL(Utils.createBaseURL(session) + "/" + id);
            final HttpURLConnection couchdbConnection = (HttpURLConnection)couchdbURL.openConnection();
            
            couchdbConnection.setRequestMethod(Constants.PUT);
            
            couchdbConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(couchdbConnection.getOutputStream());
            wr.writeBytes("{\"_id\":\"1\",\"username\":\"viande\"}");
            wr.flush();
            wr.close();
            
            System.out.println(couchdbURL);
            System.out.println(Utils.objectToJSON(toCreate));
            
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(couchdbConnection.getInputStream(), Create.class);
            
        } catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
    }
    
    public static <T> T getDocument(final Session session, final String id, final Class documentClass) {
        
        try {
        
            final URL couchdbURL = new URL(Utils.createBaseURL(session) + "/" + id);
            final HttpURLConnection couchdbConnection = (HttpURLConnection)couchdbURL.openConnection();
            
            couchdbConnection.setRequestMethod(Constants.GET);
            
            final ObjectMapper mapper = new ObjectMapper();
            return (T)mapper.readValue(couchdbConnection.getInputStream(), documentClass);
            
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        
        
    }
    
    public static Delete deleteDocument(final Session session, final String id, final String revision) {
        
        try {
        
            final URL couchdbURL = new URL(Utils.createBaseURL(session) + "/" + id + "?rev=" + revision);
            final HttpURLConnection couchdbConnection = (HttpURLConnection)couchdbURL.openConnection();
            
            couchdbConnection.setRequestMethod(Constants.DELETE);
            
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(couchdbConnection.getInputStream(), Delete.class);
            
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    
    
}