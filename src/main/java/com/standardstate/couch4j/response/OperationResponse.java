package com.standardstate.couch4j.response;

import com.standardstate.couch4j.util.Utils;

public class OperationResponse {

    private Boolean ok = false;
    private String id = null;
    private String rev = null;

    public Boolean isOk() {
        return ok;
    }

    public void setOk(final Boolean ok) {
        this.ok = ok;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(final String rev) {
        this.rev = rev;
    }
    
    @Override
    public String toString() {
        return Utils.objectToJSON(this);
    }
    
}
