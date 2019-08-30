package com.standardstate.couch4j.response;

public class Welcome {

  private String couchdb = null;
  private String version = null;

  public String getCouchdb() {
    return couchdb;
  }

  public void setCouchdb(final String couchdb) {
    this.couchdb = couchdb;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(final String version) {
    this.version = version;
  }

}
