package com.standardstate.couch4j;

import com.standardstate.couch4j.response.Information;
import com.standardstate.couch4j.response.OperationResponse;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseOperationsTest extends BaseCouch4JTest {
    
    @Test
    public void listAllDatabases() {
                
        final List<String> databases = DatabaseOperations.listAllDatabases(session);
        for(String database : databases) {
            System.out.println(database);
        }
        assertTrue("listAllDatabases", databases.size() > 1);
        
    }
    
    @Test
    public void getSystemInformation() {
        
        final Information systemInformation = DatabaseOperations.getSystemInformation(session);
        assertNotNull("getDatabaseInformation", systemInformation);
        
    }
    
    @Test
    public void createDatabase() {
        
        final OperationResponse createDatabase = DatabaseOperations.createDatabase(session, TEST_DATABASE_NAME);
        assertTrue("createDatabase", createDatabase.isOk());
        assertTrue("createDatabase", DatabaseOperations.listAllDatabases(session).contains(TEST_DATABASE_NAME));
        
        final OperationResponse deleteDatabase = DatabaseOperations.deleteDatabase(session, TEST_DATABASE_NAME);
        assertTrue("createDatabase", deleteDatabase.isOk());
        assertFalse("createDatabase", DatabaseOperations.listAllDatabases(session).contains(TEST_DATABASE_NAME));
        
    }
    
    @Test
    public void getDatabaseInformation() {
        
        final OperationResponse createDatabase = DatabaseOperations.createDatabase(session, TEST_DATABASE_NAME);
        assertTrue("getDatabaseInformation", createDatabase.isOk());
        assertTrue("getDatabaseInformation", DatabaseOperations.listAllDatabases(session).contains(TEST_DATABASE_NAME));
        
        final Information databaseInformation = DatabaseOperations.getDatabaseInformation(session, TEST_DATABASE_NAME);
        assertEquals("getDatabaseInformation", TEST_DATABASE_NAME, databaseInformation.getDb_name());
        
        final OperationResponse deleteDatabase = DatabaseOperations.deleteDatabase(session, TEST_DATABASE_NAME);
        assertTrue("getDatabaseInformation", deleteDatabase.isOk());
        assertFalse("getDatabaseInformation", DatabaseOperations.listAllDatabases(session).contains(TEST_DATABASE_NAME));
        
    }
    
}
