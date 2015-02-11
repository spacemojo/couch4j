package com.standardstate.couch4j.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.standardstate.couch4j.Constants;
import com.standardstate.couch4j.Session;
import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.options.AllDocumentsOptions;
import com.standardstate.couch4j.response.AllDocuments;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;

public class Utils {

    public static byte[] parseFile(final File file) {

        FileInputStream input = null;
        ByteArrayOutputStream output = null;

        try {

            byte[] fileBytes = new byte[8192];
            input = new FileInputStream(file);
            output = new ByteArrayOutputStream();

            int b = 0;
            while ((b = input.read(fileBytes)) != -1) {
                output.write(fileBytes, 0, b);
            }

            return output.toByteArray();
            
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } finally {
            safeClose(input);
            safeClose(output);
        }

    }

    private static void safeClose(final InputStream stream) {
        if(stream != null) {
            try { stream.close(); } catch (IOException e) { }
        }
    }
    
    private static void safeClose(final OutputStream stream) {
        if(stream != null) {
            try { stream.flush(); stream.close(); } catch (IOException e) { }
        }
    }
    
    public static String objectToJSON(final Object object) {

        final ObjectMapper mapper = new ObjectMapper();
        final StringWriter writer = new StringWriter();

        mapper.registerModule(new JodaModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setTimeZone(TimeZone.getDefault());

        try {
            mapper.writeValue(writer, object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

    public static String removeRev(final String json) {
        return removeJSONField(json, Constants.REVISION_FIELD);
    }

    public static String removeId(final String json) {
        return removeJSONField(json, Constants.ID_FIELD);
    }

    public static String removeJSONField(final String json, final String fieldName) {

        final Pattern pattern = Pattern.compile("(\"" + fieldName + "\":\"[^\"]*[\"]?[,}]?)");

        final StringBuffer insideBuffer = new StringBuffer();
        final Matcher insideMatcher = pattern.matcher(json.replace("\":null", "\":\"null\""));

        while (insideMatcher.find()) {
            insideMatcher.appendReplacement(insideBuffer, "");
        }

        insideMatcher.appendTail(insideBuffer);

        return insideBuffer.toString();

    }

    public static String createDatabaseURL(final Session session) {
        return session.getHost() + ":" + session.getPort() + "/";
    }

    public static String createDocumentURL(final Session session) {
        return session.getHost() + ":" + session.getPort() + "/" + session.getDatabase();
    }

    public static String createDesignDocumentURL(final Session session, final String designDocumentName) {
        return session.getHost() + ":" + session.getPort() + "/" + session.getDatabase() + "/" + designDocumentName;
    }

    public static URL createURL(final String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException mue) {
            throw new RuntimeException(mue);
        }
    }

    private static void setRequestMethod(final HttpURLConnection couchdbConnection, final String method) {
        try {
            couchdbConnection.setRequestMethod(method);
        } catch (ProtocolException pe) {
            throw new RuntimeException(pe);
        }
    }

    public static void setDELETEMethod(final HttpURLConnection couchdbConnection) {
        setRequestMethod(couchdbConnection, Constants.DELETE);
    }

    public static void setGETMethod(final HttpURLConnection couchdbConnection) {
        setRequestMethod(couchdbConnection, Constants.GET);
    }

    public static void setPOSTMethod(final HttpURLConnection couchdbConnection) {
        setRequestMethod(couchdbConnection, Constants.POST);
    }

    public static void setPUTMethod(final HttpURLConnection couchdbConnection) {
        setRequestMethod(couchdbConnection, Constants.PUT);
    }

    public static void setJSONContentHeader(final HttpURLConnection couchdbConnection) {
        setHeader(couchdbConnection, "Content-Type", "application/json");
    }

    public static void setContentTypeHeader(final HttpURLConnection couchdbConnection, final String contentType) {
        setHeader(couchdbConnection, "Content-Type", contentType);
    }
    
    public static void setAuthenticationHeader(final HttpURLConnection couchdbConnection, final Session session) {
        final String userCredentials = session.getUsername() + ":" + session.getPassword();
        final String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
        setHeader(couchdbConnection, "Authorization", basicAuth);
    }

    private static void setHeader(final HttpURLConnection couchdbConnection, final String headerName, final String headerValue) {
        couchdbConnection.setRequestProperty(headerName, headerValue);
    }

    public static String toQueryString(final AllDocumentsOptions options) {
        return "?descending=" + options.isDescending() + "&include_docs=" + options.isIncludeDocs() + (options.getLimit() > 0 ? "&limit=" + options.getLimit() : "");
    }

    public static void writeToConnection(final HttpURLConnection couchdbConnection, final String content) {
        try {
            couchdbConnection.setDoOutput(true);
            final DataOutputStream dataOutputStream = new DataOutputStream(couchdbConnection.getOutputStream());
            dataOutputStream.writeBytes(content);
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    public static void writeToConnection(final HttpURLConnection couchdbConnection, final byte[] content) {
        try {
            couchdbConnection.setDoOutput(true);
            final DataOutputStream dataOutputStream = new DataOutputStream(couchdbConnection.getOutputStream());
            dataOutputStream.write(content, 0, content.length);
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static HttpURLConnection openURLConnection(final URL couchdbURL) {
        try {
            return (HttpURLConnection) couchdbURL.openConnection();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static <T> T readInputStream(final HttpURLConnection couchdbConnection, final Class targetClass) {
        try {
            final ObjectMapper mapper = new ObjectMapper();

            mapper.registerModule(new JodaModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.setTimeZone(TimeZone.getDefault());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return (T) mapper.readValue(couchdbConnection.getInputStream(), targetClass);

        } catch (IOException e) {
            throw parseError(couchdbConnection, e);
        }
    }

    public static <T> T readString(final String jsonString, final Class targetClass) {
        try {
            final ObjectMapper mapper = new ObjectMapper();

            mapper.registerModule(new JodaModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.setTimeZone(TimeZone.getDefault());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return (T) mapper.readValue(jsonString, targetClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AllDocuments initAllDocuments(final Map docs, final AllDocumentsOptions options) {
        final AllDocuments allDocuments = new AllDocuments();
        allDocuments.setTotalRows((Integer) docs.get(Constants.TOTAL_ROWS));
        allDocuments.setOffset((Integer) docs.get(Constants.OFFSET));
        allDocuments.setOptions(options);
        return allDocuments;
    }

    public static AllDocumentsOptions initAllDocumentsOptions(final int limit, final boolean descending, final boolean includeDocs) {
        final AllDocumentsOptions options = new AllDocumentsOptions();
        options.setDescending(descending);
        options.setIncludeDocs(includeDocs);
        options.setLimit(limit);
        return options;
    }

    public static void appendMapFunctionToView(final DesignDocument document, final String viewName, final String mapFunction) {
        if (!document.getViews().containsKey(viewName)) {
            document.getViews().put(viewName, new ConstrainedMap());
        }
        document.getViews().get(viewName).put("map", mapFunction);
    }

    public static <T> void parseGenericValues(final Map rawDocuments, final List<T> documents, final Class documentClass) {
        for (Object row : (List) rawDocuments.get(Constants.ROWS)) {
            final LinkedHashMap documentMap = (LinkedHashMap) ((LinkedHashMap) row).get(Constants.VALUE);
            final String jsonString = Utils.objectToJSON(documentMap);
            final T document = Utils.readString(jsonString, documentClass);
            documents.add(document);
        }
    }

    public static RuntimeException parseError(final HttpURLConnection connection, final Exception cause) {

        try {

            final ObjectMapper mapper = new ObjectMapper();

            mapper.registerModule(new JodaModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.setTimeZone(TimeZone.getDefault());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            final Map error = (Map) mapper.readValue(connection.getErrorStream(), Object.class);
            final String message = "{\"responseCode\":" + connection.getResponseCode() + ","
                    + "\"responseMessage\":\"" + connection.getResponseMessage() + "\","
                    + "\"error\":\"" + error.get("error") + "\","
                    + "\"reason\":\"" + error.get("reason") + "\","
                    + "\"method\":\"" + connection.getRequestMethod() + "\"}";
            return new RuntimeException(message, cause);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

    }

    public static String listToString(final List<String> list) {
        final StringBuilder builder = new StringBuilder();
        for (String str : list) {
            builder.append(str);
        }
        return builder.toString();
    }

}
