package com.standardstate.couch4j;

import java.io.File;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ConfigurationManagerTest extends BaseCouch4JTest {
    
    @Test
    public void constructorTest() {
        assertNotNull("constructorTest()", new ConfigurationManager());
    }
    
    @Test
    public void configurationTest() throws IOException {

        boolean exceptionCaught = false;
        File configFile = new File(System.getProperty("user.home") + "/.couch4j/config.json");
        File renamed = new File(System.getProperty("user.home") + "/.couch4j/renamedconfig.json");
        assertTrue("Renamed ... ", configFile.renameTo(renamed));
    
        try {
            ConfigurationManager.getSession();
        } catch (RuntimeException e) {
            exceptionCaught = true;
        }
        assertTrue("exception caught ", exceptionCaught);
        assertTrue("rename to original ", renamed.renameTo(configFile));
        
        final Session session = ConfigurationManager.getSession();
        assertEquals("database name ", "c4jtest", session.getDatabase());
    
        final String oldPass = session.getPassword();
        exceptionCaught = false;
        session.setPassword("notvalidpassword");
        try {
            ConfigurationManager.attemptAuthenticatedCall(session);
        } catch(Exception e) {
            exceptionCaught = true;
        }
        session.setPassword(oldPass);
        assertTrue("exception caught ", exceptionCaught);
        
    }
    
}
