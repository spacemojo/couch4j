package com.standardstate.couch4j.design;

public class RawView {

    private String name = null;
    private String map = null;
    private String reduce = null;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getMap() {
        return map;
    }

    public void setMap(final String map) {
        this.map = map;
    }

    public String getReduce() {
        return reduce;
    }

    public void setReduce(final String reduce) {
        this.reduce = reduce;
    }
    
}
