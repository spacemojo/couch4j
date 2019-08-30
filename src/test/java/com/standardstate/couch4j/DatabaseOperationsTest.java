package com.standardstate.couch4j;

import static com.standardstate.couch4j.BaseTest.TEST_DATABASE_NAME;
import com.standardstate.couch4j.response.Information;
import com.standardstate.couch4j.response.OperationResponse;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class DatabaseOperationsTest {

    @Before
    public void beforeTest() {
        BaseTest.beforeTest();
    }
    
    @After
    public void afterTest() {
        BaseTest.afterTest();
    }
    
    @Test
    public void constructorTest() {

        final DatabaseOperations ops = new DatabaseOperations();
        assertNotNull("constructorTest()", ops);

        ops.setSession(BaseTest.newTestSession());
        assertEquals("constructorTest", TEST_DATABASE_NAME, ops.getSession().getDatabase());

    }
    
    @Test
    public void listAllDatabasesTest() {
        
        final DatabaseOperations ops = new DatabaseOperations(BaseTest.newTestSession());
        final List<String> names = ops.listAllDatabases();
        assertEquals("listAllDatabasesTest", 1, names.size());
        assertEquals("listAllDatabasesTest", BaseTest.TEST_DATABASE_NAME, names.get(0));
        
    }
    
    @Test
    public void getDatabaseInformationTest() {
        
        final DatabaseOperations ops = new DatabaseOperations(BaseTest.newTestSession());
        final Information info = ops.getDatabaseInformation(TEST_DATABASE_NAME);
        assertEquals("getDatabaseInformationTest", BaseTest.TEST_DATABASE_NAME, info.getDb_name());
        
    }
    
    @Test
    public void getSystemInformationTest() {
        
        final DatabaseOperations ops = new DatabaseOperations(BaseTest.newTestSession());
        final Information info = ops.getSystemInformation();
        assertEquals("getSystemInformationTest", "Welcome", info.getCouchdb());
        
    }

}
