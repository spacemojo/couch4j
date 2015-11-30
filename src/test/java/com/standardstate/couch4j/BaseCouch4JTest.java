package com.standardstate.couch4j;

import org.junit.BeforeClass;

public class BaseCouch4JTest {

    protected static final String TEST_DATABASE_NAME = "c4jtest";
    
    @BeforeClass
    public static void resetConfiguration() {
        ConfigurationManager.reset();
    }
    
}
