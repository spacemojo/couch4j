package com.standardstate.couch4j;

import com.standardstate.couch4j.response.DatabaseInformation;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.codehaus.jackson.map.ObjectMapper;

public class DocumentOperations {

    public static DatabaseInformation getDatabaseInformation(final Session session) {
        
        try {
        
            final URL couchdbURL = new URL(Utils.createDocumentURL(session));
            final HttpURLConnection couchdbConnection = (HttpURLConnection)couchdbURL.openConnection();
            
            Utils.setGETMethod(couchdbConnection);
            Utils.setAuthenticationHeader(couchdbConnection, session);
            
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(couchdbConnection.getInputStream(), DatabaseInformation.class);
            
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public static OperationResponse createDocumentWithId(final Session session, final Object toCreate, final String id) {
        
        try {
        
            final URL couchdbURL = new URL(Utils.createDocumentURL(session) + "/" + id);
            final HttpURLConnection couchdbConnection = (HttpURLConnection)couchdbURL.openConnection();
            
            Utils.setPUTMethod(couchdbConnection);
            Utils.setJSONContentHeader(couchdbConnection);
            Utils.setAuthenticationHeader(couchdbConnection, session);
            
            couchdbConnection.setDoOutput(true);
            final DataOutputStream wr = new DataOutputStream(couchdbConnection.getOutputStream());
            wr.writeBytes(Utils.removeRev(Utils.objectToJSON(toCreate)));
            wr.flush();
            wr.close();
            
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(couchdbConnection.getInputStream(), OperationResponse.class);
            
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public static OperationResponse createDocument(final Session session, final Object toCreate) {
        
        try {
        
            final URL couchdbURL = new URL(Utils.createDocumentURL(session));
            final HttpURLConnection couchdbConnection = (HttpURLConnection)couchdbURL.openConnection();
            
            Utils.setPOSTMethod(couchdbConnection);
            Utils.setJSONContentHeader(couchdbConnection);
            Utils.setAuthenticationHeader(couchdbConnection, session);
            
            couchdbConnection.setDoOutput(true);
            final DataOutputStream wr = new DataOutputStream(couchdbConnection.getOutputStream());
            wr.writeBytes(Utils.removeId(Utils.removeRev(Utils.objectToJSON(toCreate))));
            wr.flush();
            wr.close();
            
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(couchdbConnection.getInputStream(), OperationResponse.class);
            
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        
    } 
    
    public static <T> T getDocument(final Session session, final String id, final Class documentClass) {
        
        try {
        
            final URL couchdbURL = new URL(Utils.createDocumentURL(session) + "/" + id);
            final HttpURLConnection couchdbConnection = (HttpURLConnection)couchdbURL.openConnection();
            
            Utils.setGETMethod(couchdbConnection);
            Utils.setAuthenticationHeader(couchdbConnection, session);
            
            final ObjectMapper mapper = new ObjectMapper();
            return (T)mapper.readValue(couchdbConnection.getInputStream(), documentClass);
            
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public static OperationResponse deleteDocument(final Session session, final String id, final String revision) {
        
        try {
        
            final URL couchdbURL = new URL(Utils.createDocumentURL(session) + "/" + id + "?rev=" + revision);
            final HttpURLConnection couchdbConnection = (HttpURLConnection)couchdbURL.openConnection();
            
            Utils.setDELETEMethod(couchdbConnection);
            Utils.setAuthenticationHeader(couchdbConnection, session);
            
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(couchdbConnection.getInputStream(), OperationResponse.class);
            
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
}
