package com.standardstate.couch4j;

public class BaseCouch4JTest {

    protected final static String TEST_DATABASE_NAME = "c4jtest";
    protected final static Session session = new Session();

    static {

        session.setDatabase("c4jtest");
        session.setHost("http://127.0.0.1");
        session.setPassword("rosadiso;102938*");
        session.setPort(5984);
        session.setUsername("couchForJay");

    }

}
