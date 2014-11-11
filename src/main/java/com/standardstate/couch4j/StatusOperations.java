package com.standardstate.couch4j;

import com.standardstate.couch4j.response.UUIDS;
import com.standardstate.couch4j.response.Welcome;
import com.standardstate.couch4j.util.Utils;
import java.net.HttpURLConnection;
import java.net.URL;

public class StatusOperations {

    public static Welcome getWelcome(final Session session) {
        
        final URL couchdbURL = Utils.createURL(Utils.createDatabaseURL(session));
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);
        
        Utils.setGETMethod(couchdbConnection);
        
        return Utils.readInputStream(couchdbConnection, Welcome.class);
        
    }
    
    public static UUIDS getUUIDS(final Session session, int count) {
        
        final URL couchdbURL = Utils.createURL(Utils.createDatabaseURL(session) + Constants.UUIDS + "?count=" + count);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);
        
        Utils.setAuthenticationHeader(couchdbConnection, session);
        Utils.setGETMethod(couchdbConnection);
        
        return Utils.readInputStream(couchdbConnection, UUIDS.class);
        
    }
    
}
