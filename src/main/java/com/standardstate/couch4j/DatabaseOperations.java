package com.standardstate.couch4j;

import com.standardstate.couch4j.response.Information;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.util.List;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;

public class DatabaseOperations {

  private Session session = null;

  public DatabaseOperations() {
    // default constructor
  }

  public DatabaseOperations(final Session session) {
    this.session = session;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(final Session session) {
    this.session = session;
  }

  public List<String> listAllDatabases() {

    final String url = Utils.createDatabaseURL(session) + Constants.ALL_DBS;
    return Utils.get(url, List.class, session.getUsername(), session.getPassword());

  }

  public Information getSystemInformation() {

    final String url = Utils.createDatabaseURL(session);
    return Utils.get(url, Information.class, session.getUsername(), session.getPassword());

  }

  public OperationResponse createDatabase(final String name) {
    return createOrDeleteDatabase(name, HttpPut.METHOD_NAME);
  }

  public OperationResponse deleteDatabase(final String name) {
    return createOrDeleteDatabase(name, HttpDelete.METHOD_NAME);
  }

  private OperationResponse createOrDeleteDatabase(final String name, final String method) {

    final String url = Utils.createDatabaseURL(session) + name;

    if (method.equals(HttpPut.METHOD_NAME)) {
      final AbstractCouchDBDocument nullDocument = null;
      return Utils.put(url, OperationResponse.class, nullDocument, session.getUsername(), session.getPassword());
    } else {
      return Utils.delete(url, OperationResponse.class, session.getUsername(), session.getPassword());
    }

  }

  public Information getDatabaseInformation(final String databaseName) {

    final String url = Utils.createDatabaseURL(session) + databaseName;
    return Utils.get(url, Information.class, session.getUsername(), session.getPassword());

  }

}
