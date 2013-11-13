package com.standardstate.couch4j;

import com.standardstate.couch4j.response.Create;

public class App {
    
    public static void main( String[] args ) throws Exception {
        
        final Session session = new Session();
        session.setDatabase("deco3");
        session.setHost("127.0.0.1");
        session.setPort(5984);
        session.setUsername("3decoration");
        session.setPassword("rosadiso");
        
        System.out.println(DatabaseOperations.getDatabaseInformation(session));
        
//        final User user = DatabaseOperations.getDocument(session, "db482334ffdeb7b93aabd5ea9d2ed7f6615bf199", User.class);
//        System.out.println(user);
        
        final User newUser = new User();
        newUser.setUsername("viande");
        newUser.set_id("1");
        
        final Create create = DatabaseOperations.createDocumentWithId(session, newUser, newUser.get_id());
        System.out.println(create);
    }

}
