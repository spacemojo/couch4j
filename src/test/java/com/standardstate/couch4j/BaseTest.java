package com.standardstate.couch4j;

import com.standardstate.couch4j.response.OperationResponse;
import static org.junit.Assert.assertTrue;

public class BaseTest {

    public static final String TEST_DATABASE_NAME = "c4jtest";

    public static Session newTestSession() {

        final Session session = new Session();
        session.setDatabase(TEST_DATABASE_NAME);
        session.setHost("http://127.0.0.1");
        session.setPassword("nimda");
        session.setPort(Constants.DEFAULT_PORT);
        session.setUsername("admin");
        return session;

    }

    public static void beforeTest() {
        final DatabaseOperations ops = new DatabaseOperations(BaseTest.newTestSession());
        final OperationResponse response = ops.createDatabase(TEST_DATABASE_NAME);
        assertTrue("DatabaseOperationsTest.beforeTest", response.isOk());
    }

}
