package com.standardstate.couch4j.design;

public class MapReduceView extends MapView {
    
    private String reduce = null;

    public MapReduceView() {
        super();
    }

    public MapReduceView(final String name, final String map, final String reduce) {
        super(name, map);
        this.reduce = reduce;
    }
    
    public String getReduce() {
        return this.reduce;
    }
    
    public void setReduce(final String reduce) {
        this.reduce = reduce;
    }
    
}
