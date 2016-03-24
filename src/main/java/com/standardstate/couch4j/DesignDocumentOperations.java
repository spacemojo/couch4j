package com.standardstate.couch4j;

import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.design.ValidationDocument;
import com.standardstate.couch4j.options.Options;
import com.standardstate.couch4j.response.AllDocuments;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DesignDocumentOperations {

  private Session session = null;

  public DesignDocumentOperations() {
    // default constructor
  }

  public DesignDocumentOperations(final Session session) {
    this.session = session;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(final Session session) {
    this.session = session;
  }

  public OperationResponse createDesignDocument(final DesignDocument document) {

    final String url = Utils.createDesignDocumentURL(session, document.get_id());
    document.setType(null); // type is not required for a design document
    return Utils.put(url, OperationResponse.class, document, session.getUsername(), session.getPassword());

  }
  
  public OperationResponse createValidationDocument(final ValidationDocument document) {

    final String url = Utils.createDesignDocumentURL(session, document.get_id());
    document.setType(null);
    return Utils.put(url, OperationResponse.class, document, session.getUsername(), session.getPassword());

  }
  
  public DesignDocument getDesignDocument(final String documentName) {

    final String url = Utils.createDesignDocumentURL(session, "_design/" + documentName);
    return Utils.get(url, DesignDocument.class, session.getUsername(), session.getPassword());
    
  }
  
  public <T> List<T> callView(final String designDocumentId, final String viewName, final Class documentClass) {
    return callView(designDocumentId, viewName, documentClass, null);
  }
  
  public <T> List<T> callView(final String designDocumentId, final String viewName, final Class documentClass, final Options options) {

    final List<T> documents = new ArrayList<>();
    final String url = Utils.createDesignDocumentURL(session, designDocumentId + "/_view/" + viewName);

    final Map docs = (Map) Utils.get(url, options, Object.class, session.getUsername(), session.getPassword());
    Utils.parseGenericValues(docs, documents, documentClass);

    return documents;

  }
  
  public List<DesignDocument> getAllDesignDocuments() {

    final Options options = new Options();
    options.setStartKey("_design/");
    options.setEndKey("_design0/");
    options.setIncludeDocs(Boolean.TRUE);

    final AllDocuments allDocuments = new DocumentOperations(this.session).getAllDocuments(options);

    final List<DesignDocument> designDocuments = new ArrayList<>();
    for (String row : allDocuments.getRows()) {
      final DesignDocument designDocument = Utils.readString(row, DesignDocument.class);
      designDocuments.add(designDocument);
    }

    return designDocuments;

  }

}
