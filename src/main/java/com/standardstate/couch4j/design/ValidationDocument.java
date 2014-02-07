package com.standardstate.couch4j.design;

public class ValidationDocument {

    private String _id = null;
    private String validate_doc_update = null;

    public String get_id() {
        return _id;
    }

    public void set_id(final String _id) {
        this._id = _id;
    }

    public String getValidate_doc_update() {
        return validate_doc_update;
    }

    public void setValidate_doc_update(final String validate_doc_update) {
        this.validate_doc_update = validate_doc_update;
    }
    
}
