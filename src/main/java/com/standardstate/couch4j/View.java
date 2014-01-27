package com.standardstate.couch4j;

public class View {

    private String name = null;
    private String map = "";
    private String reduce = "";
    
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
