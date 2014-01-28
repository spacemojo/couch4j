package com.standardstate.couch4j;

import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.net.HttpURLConnection;
import java.net.URL;

public class DesignDocumentOperations {

    public static OperationResponse createDesignDocument(final Session session, final DesignDocument document) {
        
        final String createDesignURL = Utils.createDesignDocumentURL(session, document.get_id());
        
        final URL couchdbURL = Utils.createURL(createDesignURL);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);
        
        Utils.setPUTMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);
        
        final String jsonContent = Utils.removeRev(Utils.objectToJSON(document));
        Utils.writeToConnection(couchdbConnection, jsonContent);

        return Utils.readInputStream(couchdbConnection, OperationResponse.class);
        
    }
    
}
