package com.standardstate.couch4j;

public abstract class AbstractCouchDBDocument {
    
    private String _id = null;
    private String _rev = null;

    public String get_id() {
        return _id;
    }

    public void set_id(final String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(final String _rev) {
        this._rev = _rev;
    }
    
}
