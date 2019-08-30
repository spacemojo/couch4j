package com.standardstate.couch4j.util;

import com.standardstate.couch4j.BaseTest;
import com.standardstate.couch4j.Couch4JException;
import com.standardstate.couch4j.Session;
import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.options.Options;
import java.net.URI;
import static org.junit.Assert.*;
import org.junit.Test;

public class HTTPTest {

    @Test
    public void constructorTest() {
        assertNotNull("constructorTest", new HTTP());
    }

    @Test
    public void safeCloseTest() {
        HTTP.safeClose(null);
    }

}
