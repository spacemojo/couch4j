package com.standardstate.couch4j;

import com.standardstate.couch4j.response.UUIDS;
import com.standardstate.couch4j.response.Welcome;
import com.standardstate.couch4j.util.Utils;

public class StatusOperations {

  private Session session;

  public StatusOperations() {
    // default constructor
  }

  public StatusOperations(final Session session) {
    this.session = session;
  }

  public Welcome getWelcome() {
    return Utils.get(Utils.createDatabaseURL(session), Welcome.class);
  }

  public UUIDS getUUIDS(int count) {

    final String url = Utils.createDatabaseURL(session) + Constants.UUIDS + "?count=" + count;
    return Utils.get(url, UUIDS.class, session.getUsername(), session.getPassword());

  }

  public String getUUID() {
    return getUUIDS(1).getUuids()[0];
  }

}
