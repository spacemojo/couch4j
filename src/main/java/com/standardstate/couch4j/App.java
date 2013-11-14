package com.standardstate.couch4j;

public class App {
    
    public static void main( String[] args ) throws Exception {
        
        final Session session = new Session();
        session.setDatabase("");
        session.setHost("");
        session.setPort(5984);
        session.setUsername("");
        session.setPassword("");
        
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
        
        System.out.println(DatabaseOperations.deleteDatabase(session, "cdbtest"));
        
        System.out.println(DatabaseOperations.listAllDatabases(session));
    
    }

}
