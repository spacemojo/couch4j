package com.standardstate.couch4j;

public class App {
    
    public static void main( String[] args ) throws Exception {
        
        final Session session = new Session();
        session.setDatabase("deco3");
        session.setHost("standardstate.com");
        session.setPort(5984);
        session.setUsername("3decoration");
        session.setPassword("rosadiso");
        
        System.out.println(DatabaseOperations.getDatabaseInformation(session));
        
        final User user = DatabaseOperations.getDocument(session, "db482334ffdeb7b93aabd5ea9d2ed7f6615bf199", User.class);
        System.out.println(user);
        
    }

}
