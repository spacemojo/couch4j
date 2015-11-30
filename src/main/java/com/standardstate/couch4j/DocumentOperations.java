package com.standardstate.couch4j;

import com.standardstate.couch4j.options.Options;
import com.standardstate.couch4j.response.AllDocuments;
import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DocumentOperations {

    private static final Session session = ConfigurationManager.getSession();
    private static final String REVISION_PARAMETER = "?rev=";

    public static OperationResponse createDocumentWithId(final AbstractCouchDBDocument toCreate, final String id) {

        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session) + "/" + id);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setPUTMethod(couchdbConnection);
        Utils.setJSONContentHeader(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        final String json = Utils.objectToJSONWithoutRev(toCreate);
        Utils.writeToConnection(couchdbConnection, json);

        return Utils.readInputStream(couchdbConnection, OperationResponse.class);

    }

    public static OperationResponse createDocument(final AbstractCouchDBDocument toCreate) {

        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session));
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setPOSTMethod(couchdbConnection);
        Utils.setJSONContentHeader(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        final String json = Utils.objectToJSONWithoutId(toCreate);
        Utils.writeToConnection(couchdbConnection, json);

        return Utils.readInputStream(couchdbConnection, OperationResponse.class);

    }

    public static AllDocuments getAllDocuments() {
        return getAllDocuments(null);
    }

    public static AllDocuments getAllDocuments(final Options options) {

        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session) + Constants.ALL_DOCUMENTS + Utils.toQueryString(options));
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setGETMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        final Map docs = (Map) Utils.readInputStream(couchdbConnection, Object.class);
        final AllDocuments allDocuments = new AllDocuments((Integer) docs.get(Constants.TOTAL_ROWS), (Integer) docs.get(Constants.OFFSET), options);

        for (Object row : (List) docs.get(Constants.ROWS)) {
            final LinkedHashMap documentMap = (LinkedHashMap) ((LinkedHashMap) row).get(Constants.DOC);
            final String jsonString = Utils.objectToJSON(documentMap);
            allDocuments.addRow(jsonString);
        }

        return allDocuments;

    }
    
    public static <T extends AbstractCouchDBDocument> T getDocument(final String id, final Class documentClass) {

        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session) + "/" + id);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setGETMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        return (T) Utils.readInputStream(couchdbConnection, documentClass);

    }

    public static OperationResponse addAttachment(final AbstractCouchDBDocument document, final File file) throws IOException {

        final String url = Utils.createDocumentURL(session) + "/" + document.get_id() + "/" + file.getName() + REVISION_PARAMETER + document.get_rev();
        final URL couchdbURL = Utils.createURL(url);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setPUTMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);
        Utils.setContentTypeHeader(couchdbConnection, Files.probeContentType(file.toPath()));
        Utils.writeToConnection(couchdbConnection, Utils.parseFile(file));

        return Utils.readInputStream(couchdbConnection, OperationResponse.class);
        
    }

    public static OperationResponse deleteAttachment(final AbstractCouchDBDocument document, final String name) {
        
        final String url = Utils.createDocumentURL(session) + "/" + document.get_id() + "/" + name + REVISION_PARAMETER + document.get_rev();
        
        final URL couchdbURL = Utils.createURL(url);
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setDELETEMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        return Utils.readInputStream(couchdbConnection, OperationResponse.class);
        
    }
    
    public static OperationResponse updateDocument(final AbstractCouchDBDocument document) {

        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session) + "/" + document.get_id());
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setPUTMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);
        Utils.setJSONContentHeader(couchdbConnection);

        final String id = document.get_id();
        document.set_id(null);
        final String json = Utils.objectToJSON(document);
        document.set_id(id);
        
        Utils.writeToConnection(couchdbConnection, json);

        return Utils.readInputStream(couchdbConnection, OperationResponse.class);

    }

    public static OperationResponse deleteDocument(final AbstractCouchDBDocument document) {

        final URL couchdbURL = Utils.createURL(Utils.createDocumentURL(session) + "/" + document.get_id() + REVISION_PARAMETER + document.get_rev());
        final HttpURLConnection couchdbConnection = Utils.openURLConnection(couchdbURL);

        Utils.setDELETEMethod(couchdbConnection);
        Utils.setAuthenticationHeader(couchdbConnection, session);

        return Utils.readInputStream(couchdbConnection, OperationResponse.class);

    }

}
