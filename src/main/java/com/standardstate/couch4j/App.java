package com.standardstate.couch4j;

public class App {
    
    public static void main( String[] args ) throws Exception {
        
        final Session session = new Session();
        session.setDatabase("");
        session.setHost("127.0.0.1");
        session.setPort(5984);
        session.setUsername("uname");
        session.setPassword("pw");
        
        System.out.println(DocumentOperations.getDatabaseInformation(session));
        
        System.out.println(DatabaseOperations.listAllDatabases(session));
        
        System.out.println(DatabaseOperations.createDatabase(session, "cdbtest"));
        
        System.out.println(DatabaseOperations.listAllDatabases(session));
        
        session.setDatabase("cdbtest");
        Thread.sleep(5000);
        
        final User user = new User();
        user.setUsername("spacemojo");
        user.set_id("1234567890");
        DocumentOperations.createDocumentWithId(session, user, user.get_id());
        
        Thread.sleep(5000);
        
        System.out.println(DatabaseOperations.deleteDatabase(session, "cdbtest"));
        
        System.out.println(DatabaseOperations.listAllDatabases(session));
    
    }

}
