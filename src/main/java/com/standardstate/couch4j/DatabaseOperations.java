package com.standardstate.couch4j;

import com.standardstate.couch4j.response.Information;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DatabaseOperations {
    
    private final static Session session = ConfigurationManager.getSession();
    
    public static List<String> listAllDatabases() {
        
        final URL couchdbURL = Utils.createURL(Utils.createDatabaseURL(session) + Constants.ALL_DBS);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);
        
        Utils.setGETMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        return Utils.readInputStream(couchdbConnection, List.class);
        
    }
    
    public static Information getSystemInformation() {
        
        final URL couchdbURL = Utils.createURL(Utils.createDatabaseURL(session));
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setGETMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        return Utils.readInputStream(couchdbConnection, Information.class);
        
    }
    
    public static OperationResponse createDatabase(final String name) {    
        return createOrDeleteDatabase(name, Constants.PUT);        
    }
    
    public static OperationResponse deleteDatabase(final String name) {    
        return createOrDeleteDatabase(name, Constants.DELETE);        
    }
    
    private static OperationResponse createOrDeleteDatabase(final String name, final String method) {
        
        final URL couchdbURL = Utils.createURL(Utils.createDatabaseURL(session) + name);
            
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);
        
        setAppropriateRequestMethod(couchdbConnection, method);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        return Utils.readInputStream(couchdbConnection, OperationResponse.class);
        
    }
    
    private static void setAppropriateRequestMethod(final HttpURLConnection couchdbConnection, final String method) {
        if(method.equals(Constants.PUT)) {
            Utils.setPUTMethod(couchdbConnection);
        } else if(method.equals(Constants.DELETE)) {
            Utils.setDELETEMethod(couchdbConnection);
        }                
    }
    
    public static Information getDatabaseInformation(final String databaseName) {
        
        final URL couchdbURL = Utils.createURL(Utils.createDatabaseURL(session) + databaseName);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setGETMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        return Utils.readInputStream(couchdbConnection, Information.class);
        
    }
    
}
