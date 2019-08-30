package com.standardstate.couch4j;

public final class Attachment {

  private String content_type = null;
  private int revpos = 0;
  private String digest = null;
  private int length = 0;
  private boolean stub = true;
  
  public String getContent_type() {
    return content_type;
  }

  public void setContent_type(final String content_type) {
    this.content_type = content_type;
  }

  public int getRevpos() {
    return revpos;
  }

  public void setRevpos(final int revpos) {
    this.revpos = revpos;
  }

  public String getDigest() {
    return digest;
  }

  public void setDigest(final String digest) {
    this.digest = digest;
  }

  public int getLength() {
    return length;
  }

  public void setLength(final int length) {
    this.length = length;
  }

  public boolean isStub() {
    return stub;
  }

  public void setStub(final boolean stub) {
    this.stub = stub;
  }

}
