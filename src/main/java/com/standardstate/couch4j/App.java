package com.standardstate.couch4j;

import com.standardstate.couch4j.options.AllDocumentsOptions;
import com.standardstate.couch4j.response.AllDocuments;
import java.util.ArrayList;
import java.util.List;

public class App {
    
    public static void main( String[] args ) throws Exception {
        
        final Session session = new Session();
        session.setDatabase("");
        session.setHost("");
        session.setPort(5984);
        session.setUsername("");
        session.setPassword("");
        
        try {
        
            System.out.println(DatabaseOperations.getSystemInformation(session));

            System.out.println(DatabaseOperations.listAllDatabases(session));

            System.out.println(DatabaseOperations.createDatabase(session, "cdbtest"));

            System.out.println(DatabaseOperations.listAllDatabases(session));

            session.setDatabase("cdbtest");
            Thread.sleep(2000);

            final User user = new User();
            user.setUsername("spacemojo");
            user.set_id("1234567890");
            System.out.println(DocumentOperations.createDocumentWithId(session, user, user.get_id()));

            Thread.sleep(2000);
            user.setUsername("viande ... ");
            System.out.println(DocumentOperations.createDocument(session, user));

            Thread.sleep(2000);

            final AllDocumentsOptions options = new AllDocumentsOptions();
            options.setIncludeDocs(Boolean.TRUE);
            final AllDocuments allDocuments = DocumentOperations.getAllDocuments(session, options);
            System.out.println("allDocuments : " + allDocuments.getTotalRows());

            final User document = DocumentOperations.getDocument(session, "1234567890", User.class);
            System.out.println(document);

            final List<String> keys = new ArrayList<>();
            keys.add("1234567890");
            keys.add("1029384756");

            System.out.println(DocumentOperations.getDocumentsByKeys(session, keys, User.class));
        
        } finally {
            
            System.out.println(DatabaseOperations.deleteDatabase(session, "cdbtest"));
            System.out.println(DatabaseOperations.listAllDatabases(session));
            
        }
    
    }

}
