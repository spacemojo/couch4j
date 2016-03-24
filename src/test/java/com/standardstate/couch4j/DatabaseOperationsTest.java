package com.standardstate.couch4j;

import com.standardstate.couch4j.response.Information;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseOperationsTest extends BaseTest {

  @Test
  public void constructorTest() {
    
    final DatabaseOperations operations = new DatabaseOperations();
    assertNotNull("constructorTest()", operations);
    
    operations.setSession(new Session());
    operations.getSession().setDatabase(TEST_DATABASE_NAME);
    assertEquals("constructorTest", TEST_DATABASE_NAME, operations.getSession().getDatabase());
    
  }

  @Test
  public void listAllDatabases() {

    final List<String> databases = new DatabaseOperations(session).listAllDatabases();
    assertTrue("listAllDatabases", databases.size() > 1);
  }

  @Test
  public void getSystemInformation() {

    final Information systemInformation = new DatabaseOperations(session).getSystemInformation();
    assertNotNull("getSystemInformation()", systemInformation);
  }

  @Test
  public void getDatabaseInformation() {

    final DatabaseOperations operations = new DatabaseOperations(session);
    final Information databaseInformation = operations.getDatabaseInformation(TEST_DATABASE_NAME);
    assertEquals("getDatabaseInformation(get information for db name)", TEST_DATABASE_NAME, databaseInformation.getDb_name());

  }

}
