package com.standardstate.couch4j.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.standardstate.couch4j.AbstractCouchDBDocument;
import com.standardstate.couch4j.Constants;
import com.standardstate.couch4j.Couch4JException;
import com.standardstate.couch4j.Session;
import com.standardstate.couch4j.design.DesignDocument;
import com.standardstate.couch4j.options.Options;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.codec.binary.Base64;

public class Utils {

    private static final String ENCODING = "UTF-8";

    public static byte[] parseFile(final File file) {

        FileInputStream input = null;
        ByteArrayOutputStream output = null;

        try {

            byte[] fileBytes = new byte[8192];
            input = new FileInputStream(file);
            output = new ByteArrayOutputStream();

            int b;
            while ((b = input.read(fileBytes)) != -1) {
                output.write(fileBytes, 0, b);
            }

            return output.toByteArray();

        } catch (IOException ioe) {
            throw new Couch4JException(ioe);
        } finally {
            safeClose(input);
            safeClose(output);
        }

    }

    private static void safeClose(final InputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
    }

    private static void safeClose(final OutputStream stream) {
        if (stream != null) {
            try {
                stream.flush();
                stream.close();
            } catch (IOException e) {
            }
        }
    }

    public static String objectToJSONWithoutRev(final DesignDocument document) {
        document.set_rev(null);
        return Utils.objectToJSON(document);
    }

    public static String objectToJSONWithoutRev(final AbstractCouchDBDocument document) {
        document.set_rev(null);
        return Utils.objectToJSON(document);
    }

    public static String objectToJSONWithoutId(final AbstractCouchDBDocument document) {
        document.set_id(null);
        return Utils.objectToJSON(document);
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
            throw new Couch4JException(e);
        }
        return writer.toString();
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
            throw new Couch4JException(mue);
        }
    }

    private static void setRequestMethod(final HttpURLConnection couchdbConnection, final String method) {
        try {
            couchdbConnection.setRequestMethod(method);
        } catch (ProtocolException pe) {
            throw new Couch4JException(pe);
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

    public static String toQueryString(final Options options) {

        if (options != null) {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("key", options.getKey());
            parameters.put("descending", options.isDescending());
            parameters.put("limit", options.getLimit());
            parameters.put("include_docs", options.isIncludeDocs());
            parameters.put("startkey", options.getStartKey());
            parameters.put("endkey", options.getEndKey());
            return toQueryString(parameters);
        }
        return "";

    }

    private static String toQueryString(final Map<String, Object> parameters) {

        final StringBuilder builder = new StringBuilder("?");
        for (String key : parameters.keySet()) {
            if (parameters.get(key) != null) {
                if (parameters.get(key).getClass().equals(Boolean.class) || parameters.get(key).getClass().equals(Integer.class)) {
                    builder.append(key).append("=").append(safeEncodeUTF8(parameters.get(key).toString())).append("&");
                } else {
                    builder.append(key).append("=\"").append(safeEncodeUTF8(parameters.get(key).toString())).append("\"&");
                }
            }
        }
        return builder.toString().substring(0, builder.length() - 1);

    }

    public static String safeEncodeUTF8(final String toEncode, final String... encoding) {
        try {
            return URLEncoder.encode(toEncode, encoding.length == 1 ? encoding[0] : ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new Couch4JException(e);
        }
    }

    public static void writeToConnection(final HttpURLConnection couchdbConnection, final String content) {
        try {
            couchdbConnection.setDoOutput(true);
            final DataOutputStream dataOutputStream = new DataOutputStream(couchdbConnection.getOutputStream());
            dataOutputStream.writeBytes(content);
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (IOException ioe) {
            throw new Couch4JException(ioe);
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
            throw new Couch4JException(ioe);
        }
    }

    public static HttpURLConnection openURLConnection(final URL couchdbURL) {
        try {
            return (HttpURLConnection) couchdbURL.openConnection();
        } catch (IOException ioe) {
            throw new Couch4JException(ioe);
        }
    }

    public static <T> T readInputStream(final InputStream stream, final Class targetClass) {
        try {

            final ObjectMapper mapper = new ObjectMapper();

            mapper.registerModule(new JodaModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.setTimeZone(TimeZone.getDefault());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return (T) mapper.readValue(stream, targetClass);

        } catch (IOException e) {
            throw new Couch4JException(e);
        }
    }

    public static <T> T readInputStream(final HttpURLConnection couchdbConnection, final Class targetClass) {
        try {
            return readInputStream(couchdbConnection.getInputStream(), targetClass);
        } catch (Exception e) {
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
            throw new Couch4JException(e);
        }
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

        } catch (Exception e) {
            throw new Couch4JException(e);
        }

    }

}
