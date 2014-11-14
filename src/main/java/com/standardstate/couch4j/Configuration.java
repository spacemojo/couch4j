package com.standardstate.couch4j;

public class Configuration {

    private Session test = null;
    private Session current = null;
    
    public Session getTest() {
        return test;
    }

    public void setTest(final Session test) {
        this.test = test;
    }

    public Session getCurrent() {
        return current;
    }

    public void setCurrent(final Session current) {
        this.current = current;
    }
    
}
