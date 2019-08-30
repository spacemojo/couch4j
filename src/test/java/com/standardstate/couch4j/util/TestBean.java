package com.standardstate.couch4j.util;

import com.standardstate.couch4j.AbstractCouchDBDocument;

public class TestBean extends AbstractCouchDBDocument {

    private int id = 0;
    private String name = null;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    
}
