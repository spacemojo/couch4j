package com.standardstate.couch4j;

import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.design.MapReduceView;
import com.standardstate.couch4j.design.MapView;
import com.standardstate.couch4j.design.RawDesignDocument;
import com.standardstate.couch4j.design.RawView;
import com.standardstate.couch4j.design.View;
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
    
    public static DesignDocument getDesignDocument(final Session session, final String documentName) {
        
        final String getDesignURL = Utils.createDesignDocumentURL(session, "_design/" + documentName);
        
        final URL couchdbURL = Utils.createURL(getDesignURL);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);
        
        Utils.setGETMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);
        
        final RawDesignDocument rawDocument = Utils.readInputStream(couchdbConnection, RawDesignDocument.class);
        return createDesignDocumentFromRaw(rawDocument);
        
    }
    
    private static DesignDocument createDesignDocumentFromRaw(final RawDesignDocument rawDocument) {
        
        final DesignDocument document = new DesignDocument();
        document.set_id(rawDocument.get_id());
        document.setLanguage(rawDocument.getLanguage());
        document.set_rev(rawDocument.get_rev());
        
        for(RawView rawView : rawDocument.getViews()) {
            document.getViews().add(createViewFromRaw(rawView));
        }
        
        return document;
        
    }
    
    private static View createViewFromRaw(final RawView rawView) {
        if(rawView.getReduce() == null || rawView.getReduce().equals("")) {
            return new MapView(rawView.getName(), rawView.getMap());
        } else {
            return new MapReduceView(rawView.getName(), rawView.getMap(), rawView.getReduce());
        }
    }
    
}
