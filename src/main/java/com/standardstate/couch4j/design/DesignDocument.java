package com.standardstate.couch4j.design;

import java.util.ArrayList;
import java.util.List;

public class DesignDocument {

    private String _id = null;
    private String _rev = null;
    private String language = "javascript";
    
    private List<View> views = new ArrayList<>();
    
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public List<View> getViews() {
        return views;
    }

    public void setViews(final List<View> views) {
        this.views = views;
    }
    
}
