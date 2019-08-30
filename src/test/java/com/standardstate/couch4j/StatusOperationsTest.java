package com.standardstate.couch4j;

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
        final Welcome serverWelcome = new StatusOperations(BaseTest.newTestSession()).getWelcome();
        assertEquals("getWelcome(1)", "Welcome", serverWelcome.getCouchdb());
        assertFalse("getWelcome(2)", serverWelcome.getVersion().isEmpty());
    }

    @Test
    public void getUUIDS() {

        final int count = 3;
        final UUIDS uuids = new StatusOperations(BaseTest.newTestSession()).getUUIDS(count);
        assertEquals("getUUIDS()", count, uuids.getUuids().length);
    }

    @Test
    public void getUUID() {

        final String uuid = new StatusOperations(BaseTest.newTestSession()).getUUID();
        assertNotNull("getUUID()", uuid);
    }

}
