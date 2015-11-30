package com.standardstate.couch4j;

import com.standardstate.couch4j.response.Information;
import com.standardstate.couch4j.response.OperationResponse;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseOperationsTest extends BaseCouch4JTest {

    @Test
    public void constructorTest() {
        assertNotNull("constructorTest()", new DatabaseOperations());
    }
    
    @Test
    public void listAllDatabases() {
                
        final List<String> databases = DatabaseOperations.listAllDatabases();
        assertTrue("listAllDatabases", databases.size() > 1);
        
    }
    
    @Test
    public void getSystemInformation() {
        
        final Information systemInformation = DatabaseOperations.getSystemInformation();
        assertNotNull("getSystemInformation()", systemInformation);
        
    }
    
    @Test
    public void createAndDeleteDatabase() {
        
        final OperationResponse createDatabase = DatabaseOperations.createDatabase(TEST_DATABASE_NAME);
        assertTrue("createDatabase(isOk)", createDatabase.isOk());
        assertTrue("createDatabase(list all databases)", DatabaseOperations.listAllDatabases().contains(TEST_DATABASE_NAME));
        
        final OperationResponse deleteDatabase = DatabaseOperations.deleteDatabase(TEST_DATABASE_NAME);
        assertTrue("createDatabase(delete is ok)", deleteDatabase.isOk());
        assertFalse("createDatabase(list all databases does not contain)", DatabaseOperations.listAllDatabases().contains(TEST_DATABASE_NAME));
        
    }
    
    @Test
    public void getDatabaseInformation() {
        
        final OperationResponse createDatabase = DatabaseOperations.createDatabase(TEST_DATABASE_NAME);
        assertTrue("getDatabaseInformation(isOk)", createDatabase.isOk());
        assertTrue("getDatabaseInformation(list all databases)", DatabaseOperations.listAllDatabases().contains(TEST_DATABASE_NAME));
        
        final Information databaseInformation = DatabaseOperations.getDatabaseInformation(TEST_DATABASE_NAME);
        assertEquals("getDatabaseInformation(get information for db name)", TEST_DATABASE_NAME, databaseInformation.getDb_name());
        
        final OperationResponse deleteDatabase = DatabaseOperations.deleteDatabase(TEST_DATABASE_NAME);
        assertTrue("getDatabaseInformation(delete isOk)", deleteDatabase.isOk());
        assertFalse("getDatabaseInformation(list all databases does not contain)", DatabaseOperations.listAllDatabases().contains(TEST_DATABASE_NAME));
        
    }
    
}
