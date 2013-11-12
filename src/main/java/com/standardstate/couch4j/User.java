package com.standardstate.couch4j;

import com.standardstate.couch4j.util.Utils;

public class User {

    private String _id = null;
    private String _rev = null;
    private String username = null;

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
    
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
    
    @Override
    public String toString() {
        return Utils.objectToJSON(this);
    }
    
}
