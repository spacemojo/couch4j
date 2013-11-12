package com.standardstate.couch4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class App {
    
    private final static String DB_NAME = "deco3";
    private final static String HOSTNAME = "127.0.0.1";
    private final static String PORT = "5984";
    
    public static void main( String[] args ) throws Exception {
        
        final URL couchdbURL = new URL("http://" + HOSTNAME + ":" + PORT + "/" + DB_NAME);
        final HttpURLConnection couchdbConnection = (HttpURLConnection)couchdbURL.openConnection();
        couchdbConnection.setRequestMethod(Constants.GET);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(couchdbConnection.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }
        in.close();
    
    }

}
