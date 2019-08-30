package com.standardstate.couch4j;

import com.standardstate.couch4j.options.Options;
import com.standardstate.couch4j.response.AllDocuments;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.HTTP;
import com.standardstate.couch4j.util.Utils;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DocumentOperations {

  private Session session = null;

  public DocumentOperations(final Session session) {
    this.session = session;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(final Session session) {
    this.session = session;
  }

  public OperationResponse createDocumentWithId(final AbstractCouchDBDocument toCreate, final String id) {

    final String url = Utils.createDocumentURL(session) + "/" + id;
    return HTTP.put(url, OperationResponse.class, toCreate, session.getUsername(), session.getPassword());

  }

  public OperationResponse createDocument(final AbstractCouchDBDocument toCreate) {

    final String url = Utils.createDocumentURL(session);
    return HTTP.post(url, OperationResponse.class, toCreate, session.getUsername(), session.getPassword());

  }

  public AllDocuments getAllDocuments() {
    return getAllDocuments(null);
  }

  public AllDocuments getAllDocuments(final Options options) {

    final String url = Utils.createDocumentURL(session) + Constants.ALL_DOCUMENTS;
    final Map docs = (Map) HTTP.get(url, options, Object.class, session.getUsername(), session.getPassword());

    final AllDocuments allDocuments = new AllDocuments((Integer) docs.get(Constants.TOTAL_ROWS), (Integer) docs.get(Constants.OFFSET), options);

    for (Object row : (List) docs.get(Constants.ROWS)) {
      final LinkedHashMap documentMap = (LinkedHashMap) ((LinkedHashMap) row).get(Constants.DOC);
      final String jsonString = Utils.objectToJSON(documentMap);
      allDocuments.addRow(jsonString);
    }

    return allDocuments;

  }

  public <T extends AbstractCouchDBDocument> T getDocument(final String id, final Class documentClass) {

    final String url = Utils.createDocumentURL(session) + "/" + id;
    return (T) HTTP.get(url, documentClass, session.getUsername(), session.getPassword());

  }

  public OperationResponse addAttachment(final AbstractCouchDBDocument document, final File file) {

    final String url = Utils.createDocumentURL(session) + "/" + document.get_id() + "/" + file.getName() + Constants.REVISION_PARAMETER + document.get_rev();
    return HTTP.put(url, OperationResponse.class, file, session.getUsername(), session.getPassword());

  }

  public OperationResponse deleteAttachment(final AbstractCouchDBDocument document, final String name) {

    final String url = Utils.createDocumentURL(session) + "/" + document.get_id() + "/" + name + Constants.REVISION_PARAMETER + document.get_rev();
    return HTTP.delete(url, OperationResponse.class, session.getUsername(), session.getPassword());

  }

  public OperationResponse updateDocument(final AbstractCouchDBDocument document) {

    final String url = Utils.createDocumentURL(session) + "/" + document.get_id();
    return HTTP.put(url, OperationResponse.class, document, session.getUsername(), session.getPassword());

  }

  public OperationResponse deleteDocument(final AbstractCouchDBDocument document) {

    final String url = Utils.createDocumentURL(session) + "/" + document.get_id() + Constants.REVISION_PARAMETER + document.get_rev();
    return HTTP.delete(url, OperationResponse.class, session.getUsername(), session.getPassword());

  }

}
