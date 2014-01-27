package com.standardstate.couch4j;

import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.net.HttpURLConnection;
import java.net.URL;

public class DesignDocumentOperations {

    public static OperationResponse createDesignDocument(final Session session, final DesignDocument document) {
        
        final String createDesignURL = Utils.createDesignDocumentURL(session, document.getName());
        final URL couchdbURL = Utils.createURL(createDesignURL);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);
        
        Utils.setPOSTMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);
        
        Utils.writeToConnection(couchdbConnection, Utils.removeId(Utils.removeRev(Utils.objectToJSON(document))));

        return Utils.readInputStream(couchdbConnection, OperationResponse.class);
        
    }
    
}
