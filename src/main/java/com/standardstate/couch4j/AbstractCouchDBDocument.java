package com.standardstate.couch4j;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

public abstract class AbstractCouchDBDocument {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String _id = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String _rev = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type = this.getClass().getName();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Attachment> _attachments = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reason = null;

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

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Map<String, Attachment> get_attachments() {
        return this._attachments;
    }

    public void set_attachments(final Map<String, Attachment> attachments) {
        this._attachments = attachments;
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

}
