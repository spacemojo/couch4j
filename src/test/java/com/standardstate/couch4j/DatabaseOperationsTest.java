package com.standardstate.couch4j;

import static com.standardstate.couch4j.BaseTest.TEST_DATABASE_NAME;
import com.standardstate.couch4j.response.Information;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class DatabaseOperationsTest {

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
    
        final ArrayList<String> expectedDatabases = new ArrayList<>();
        expectedDatabases.add("unittests-01");
        expectedDatabases.add("unittests-02");
        
        final DatabaseOperations dbOps = mock(DatabaseOperations.class);
        when(dbOps.listAllDatabases()).thenReturn(expectedDatabases);
        
        final List<String> databases = dbOps.listAllDatabases();
        assertTrue("listAllDatabases", databases.size() > 1);
    
    }

    @Test
    public void getSystemInformation() {

        final Information info = new Information();
        info.setDb_name(TEST_DATABASE_NAME);
        
        final DatabaseOperations dbOps = mock(DatabaseOperations.class);
        when(dbOps.getSystemInformation()).thenReturn(info);
        
        final Information systemInformation = dbOps.getSystemInformation();
        assertEquals("getSystemInformation", TEST_DATABASE_NAME, systemInformation.getDb_name());
        
    }

}
