package com.standardstate.couch4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class App {
    
    public static void main( String[] args ) throws Exception {
        
        URL couchdbURL = new URL("http://127.0.0.1:5984/deco3");
        URLConnection yc = couchdbURL.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }
        in.close();
    
    }

}
