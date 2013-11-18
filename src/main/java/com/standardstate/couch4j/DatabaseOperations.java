package com.standardstate.couch4j;

import com.standardstate.couch4j.response.Information;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

public class DatabaseOperations {

    private final static String ALL_DBS = "_all_dbs";
    
    public static List<String> listAllDatabases(final Session session) {
        
        try {
            
            final URL couchdbURL = new URL(Utils.createDatabaseURL(session) + ALL_DBS);
            final HttpURLConnection couchdbConnection = (HttpURLConnection)couchdbURL.openConnection();
            couchdbConnection.setRequestMethod(Constants.GET);
    
            Utils.setAuthenticationHeader(couchdbConnection, session);
            
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(couchdbConnection.getInputStream(), List.class);
            
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public static Information getSystemInformation(final Session session) {
        
        try {
        
            final URL couchdbURL = new URL(Utils.createDatabaseURL(session));
            final HttpURLConnection couchdbConnection = (HttpURLConnection)couchdbURL.openConnection();
            
            Utils.setGETMethod(couchdbConnection);
            Utils.setAuthenticationHeader(couchdbConnection, session);
            
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(couchdbConnection.getInputStream(), Information.class);
            
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public static Information getDatabaseInformation(final Session session, final String databaseName) {
        
        try {
        
            final URL couchdbURL = new URL(Utils.createDatabaseURL(session) + databaseName);
            final HttpURLConnection couchdbConnection = (HttpURLConnection)couchdbURL.openConnection();
            
            Utils.setGETMethod(couchdbConnection);
            Utils.setAuthenticationHeader(couchdbConnection, session);
            
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(couchdbConnection.getInputStream(), Information.class);
            
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public static OperationResponse createDatabase(final Session session, final String name) {
        
        return createOrDeleteDatabase(session, name, Constants.PUT);
        
    }
    
    public static OperationResponse deleteDatabase(final Session session, final String name) {
        
        return createOrDeleteDatabase(session, name, Constants.DELETE);
        
    }
    
    private static OperationResponse createOrDeleteDatabase(final Session session, final String name, final String method) {
        
        try {
            
            final URL couchdbURL = new URL(Utils.createDatabaseURL(session) + name);
            
            final HttpURLConnection couchdbConnection = (HttpURLConnection)couchdbURL.openConnection();
            couchdbConnection.setRequestMethod(method);
            couchdbConnection.setRequestProperty("Content-Type", "text/plain");

            Utils.setAuthenticationHeader(couchdbConnection, session);
            
            return new ObjectMapper().readValue(couchdbConnection.getInputStream(), OperationResponse.class);
            
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
}
