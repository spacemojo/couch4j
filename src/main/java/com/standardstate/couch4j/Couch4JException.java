package com.standardstate.couch4j;

public class Couch4JException extends RuntimeException {

  public Couch4JException(final Throwable cause) {
    super(cause);
  }

  public Couch4JException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public Couch4JException(final String message) {
    super(message);
  }

}
