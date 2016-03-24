package com.standardstate.couch4j;

import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;

public class BaseTest {

  protected static final String TEST_DATABASE_NAME = "c4jtest";
  protected static final Session session = new Session();

  @BeforeClass
  public static void configureSession() {
    session.setDatabase(TEST_DATABASE_NAME);
    session.setHost("http://127.0.0.1");
    session.setPassword("rosadiso");
    session.setUsername("admin");
    session.setPort(5984);
    
    assertTrue("cleanup", new DatabaseOperations(session).createDatabase(TEST_DATABASE_NAME).isOk());
    
  }
  
  @AfterClass
  public static void cleanup() {
    assertTrue("cleanup", new DatabaseOperations(session).deleteDatabase(TEST_DATABASE_NAME).isOk());
  }
  
}
