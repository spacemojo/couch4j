package com.standardstate.couch4j.design;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.standardstate.couch4j.util.ConstrainedMap;
import java.util.HashMap;
import java.util.Map;

public class DesignDocument {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String _id = null;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String _rev = null;
    
    private String language = "javascript";
    
    private Map<String, ConstrainedMap> views = new HashMap<>();
    
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

    public Map<String, ConstrainedMap> getViews() {
        return views;
    }

    public void setViews(final Map<String, ConstrainedMap> views) {
        this.views = views;
    }
    
}
