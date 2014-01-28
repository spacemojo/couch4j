package com.standardstate.couch4j.design;

public class MapView implements View {

    private String name = null;
    private String map = "";

    public MapView() {  }

    public MapView(final String name, final String map) {
        this.name = name;
        this.map = map;
    }
    
    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final String getMap() {
        return map;
    }

    public final void setMap(final String map) {
        this.map = map;
    }
    
}
