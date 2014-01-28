package com.standardstate.couch4j.design;

public class MapReduceView extends MapView {
    
    private String reduce = null;
    
    public String getReduce() {
        return this.reduce;
    }
    
    public void setReduce(final String reduce) {
        this.reduce = reduce;
    }
    
}
