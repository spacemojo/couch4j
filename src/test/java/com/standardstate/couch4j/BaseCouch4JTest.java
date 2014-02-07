package com.standardstate.couch4j;

public class BaseCouch4JTest {

    protected final static String TEST_DATABASE_NAME = "c4jtest";
    protected final static Session session = new Session();

    static {

        session.setDatabase("");
        session.setHost("127.0.0.1");
        session.setUsername("");
        session.setPassword("");

    }

}
