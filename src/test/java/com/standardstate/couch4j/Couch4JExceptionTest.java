package com.standardstate.couch4j;

import org.junit.Test;

public class Couch4JExceptionTest {
    
    @Test(expected = Couch4JException.class)
    public void exceptionWithMessageTest() {
        throw new Couch4JException("This is thrown in a unit test");
    }
    
    @Test(expected = Couch4JException.class)
    public void exceptionWithCauseAndMessageTest() {
        throw new Couch4JException("This is thrown in a unit test with a cause", new RuntimeException("This is the cause"));
    }
    
    @Test(expected = Couch4JException.class)
    public void exceptionWithCauseTest() {
        throw new Couch4JException(new RuntimeException("This is the only cause and no message"));
    }
    
}
