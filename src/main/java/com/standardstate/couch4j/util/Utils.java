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
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URIBuilder;

public class Utils {

    public static String objectToJSONWithoutRev(final DesignDocument document) {
        document.set_rev(null);
        return objectToJSON(document);
    }

    public static String objectToJSONWithoutRev(final AbstractCouchDBDocument document) {
        document.set_rev(null);
        return objectToJSON(document);
    }

    public static String objectToJSONWithoutId(final AbstractCouchDBDocument document) {
        document.set_id(null);
        return objectToJSON(document);
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

    public static URI buildURI(final String url, final Options options) {

        try {

            final URIBuilder builder = new URIBuilder(url);
            if (options.getKey() != null) {
                builder.addParameter("key", "\"" + options.getKey() + "\"");
            }
            if (options.isDescending() != null) {
                builder.addParameter("descending", String.valueOf(options.isDescending()));
            }
            if (options.getLimit() != null) {
                builder.addParameter("limit", String.valueOf(options.getLimit()));
            }
            if (options.isIncludeDocs() != null) {
                builder.addParameter("include_docs", String.valueOf(options.isIncludeDocs()));
            }
            if (options.getStartKey() != null) {
                builder.addParameter("startkey", "\"" + options.getStartKey() + "\"");
            }
            if (options.getEndKey() != null) {
                builder.addParameter("endkey", "\"" + options.getEndKey() + "\"");
            }

            return builder.build();

        } catch (URISyntaxException e) {
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
            final String jsonString = objectToJSON(documentMap);
            final T document = readString(jsonString, documentClass);
            documents.add(document);
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
        } catch (Exception e) {
            throw new Couch4JException(e);
        }

        return writer.toString();

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

    public static <T> T readString(final String jsonString, final Class targetClass) {
        return readInputStream(IOUtils.toInputStream(jsonString), targetClass);
    }

}
