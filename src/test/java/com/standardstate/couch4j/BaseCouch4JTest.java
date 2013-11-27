package com.standardstate.couch4j;

import org.junit.Before;

public class BaseCouch4JTest {

    protected final static String TEST_DATABASE_NAME = "c4jtest";
    protected Session session = new Session();
    
    @Before
    public void sessionSetUp() {
        
        session.setDatabase("");
        session.setUsername("");
        session.setPassword("");
                
    }
    
}
