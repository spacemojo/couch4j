package com.standardstate.couch4j.design;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.standardstate.couch4j.AbstractCouchDBDocument;
import com.standardstate.couch4j.util.ConstrainedMap;
import java.util.HashMap;
import java.util.Map;

public class DesignDocument extends AbstractCouchDBDocument {

  private String language = "javascript";

  private Map<String, ConstrainedMap> views = new HashMap<>();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String validate_doc_update = null;

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

  public String getValidate_doc_update() {
    return validate_doc_update;
  }

  public void setValidate_doc_update(final String validate_doc_update) {
    this.validate_doc_update = validate_doc_update;
  }

}
