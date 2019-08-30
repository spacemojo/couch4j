package com.standardstate.couch4j.design;

import com.standardstate.couch4j.AbstractCouchDBDocument;

public class ValidationDocument extends AbstractCouchDBDocument {

  private String validate_doc_update = null;

  public String getValidate_doc_update() {
    return validate_doc_update;
  }

  public void setValidate_doc_update(final String validate_doc_update) {
    this.validate_doc_update = validate_doc_update;
  }

}
