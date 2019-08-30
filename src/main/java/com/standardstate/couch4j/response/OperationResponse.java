package com.standardstate.couch4j.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.standardstate.couch4j.util.Utils;

public class OperationResponse {
    
    private Boolean ok = false;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id = null;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rev = null;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error = null;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reason = null;

    public OperationResponse() {
        // default constructor
    }

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return Utils.objectToJSON(this);
    }

}
