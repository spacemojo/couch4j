package com.standardstate.couch4j;

import static com.standardstate.couch4j.BaseCouch4JTest.session;
import com.standardstate.couch4j.response.UUIDS;
import com.standardstate.couch4j.response.Welcome;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class StatusOperationsTest {

    @Test
    public void consructorTest() {
        assertNotNull("constructorTest()", new StatusOperations());
    }
    
    @Test
    public void getWelcome() {

        final Welcome serverWelcome = StatusOperations.getWelcome(session);
        assertEquals("getWelcome(1)", "Welcome", serverWelcome.getCouchdb());
        assertFalse("getWelcome(2)", serverWelcome.getVersion().isEmpty());

    }

    @Test
    public void getUUIDS() {
        
        final int count = 3;
        final UUIDS uuids = StatusOperations.getUUIDS(session, count);
        assertEquals("getUUIDS()", count, uuids.getUuids().length);
        
    }
    
}
