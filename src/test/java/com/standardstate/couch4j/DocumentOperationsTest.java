package com.standardstate.couch4j;

import static com.standardstate.couch4j.BaseTest.TEST_DATABASE_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class DocumentOperationsTest {

    @Test
    public void constructorTest() {

        final DocumentOperations operations = new DocumentOperations(new Session());
        assertNotNull("constructorTest()", operations);

        operations.getSession().setDatabase(TEST_DATABASE_NAME);
        assertEquals("constructorTest", TEST_DATABASE_NAME, operations.getSession().getDatabase());

    }

}
