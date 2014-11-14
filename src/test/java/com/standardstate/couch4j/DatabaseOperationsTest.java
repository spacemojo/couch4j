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
        for(String database : databases) {
            System.out.println(database);
        }
        assertTrue("listAllDatabases", databases.size() > 1);
        
    }
    
    @Test
    public void getSystemInformation() {
        
        final Information systemInformation = DatabaseOperations.getSystemInformation();
        assertNotNull("getDatabaseInformation", systemInformation);
        System.out.println(systemInformation.toString());
        System.out.println("Data size : " + systemInformation.getData_size());
        
    }
    
    @Test
    public void createDatabase() {
        
        final OperationResponse createDatabase = DatabaseOperations.createDatabase(TEST_DATABASE_NAME);
        assertTrue("createDatabase", createDatabase.isOk());
        assertTrue("createDatabase", DatabaseOperations.listAllDatabases().contains(TEST_DATABASE_NAME));
        
        final OperationResponse deleteDatabase = DatabaseOperations.deleteDatabase(TEST_DATABASE_NAME);
        assertTrue("createDatabase", deleteDatabase.isOk());
        assertFalse("createDatabase", DatabaseOperations.listAllDatabases().contains(TEST_DATABASE_NAME));
        
    }
    
    @Test
    public void getDatabaseInformation() {
        
        final OperationResponse createDatabase = DatabaseOperations.createDatabase(TEST_DATABASE_NAME);
        assertTrue("getDatabaseInformation", createDatabase.isOk());
        assertTrue("getDatabaseInformation", DatabaseOperations.listAllDatabases().contains(TEST_DATABASE_NAME));
        
        final Information databaseInformation = DatabaseOperations.getDatabaseInformation(TEST_DATABASE_NAME);
        assertEquals("getDatabaseInformation", TEST_DATABASE_NAME, databaseInformation.getDb_name());
        System.out.println(databaseInformation.toString());
        
        final OperationResponse deleteDatabase = DatabaseOperations.deleteDatabase(TEST_DATABASE_NAME);
        assertTrue("getDatabaseInformation", deleteDatabase.isOk());
        assertFalse("getDatabaseInformation", DatabaseOperations.listAllDatabases().contains(TEST_DATABASE_NAME));
        
    }
    
}
