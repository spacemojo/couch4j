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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

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

  private static void setBasicAuthentication(final String[] credentials, final HttpRequestBase request) {
    if (credentials.length == 2) {
      request.setHeader(HttpHeaders.AUTHORIZATION, createBasicAuthenticationValue(credentials[0], credentials[1]));
    }
  }

  private static void setJSONContentType(final HttpRequestBase request) {
    request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
    request.setHeader(HttpHeaders.ACCEPT, "application/json");
  }

  private static void setFileContentType(final HttpRequestBase request, final File file) {
    try {
      request.setHeader(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file.toPath()));
    } catch (IOException e) {
      throw new Couch4JException(e);
    }
  }

  public static <T> T get(final String url, final Options options, final Class targetClass, final String... credentials) {

    final HttpGet get = (options != null) ? new HttpGet(buildURI(url, options)) : new HttpGet(url);
    return executeHttpRequest(targetClass, get, null, null, credentials);
    
  }

  public static <T> T get(final String url, final Class targetClass, final String... credentials) {

    final HttpGet get = new HttpGet(url);
    return executeHttpRequest(targetClass, get, null, null, credentials);

  }

  public static <T> T put(final String url, final Class targetClass, final AbstractCouchDBDocument toPut, final String... credentials) {

    final HttpPut put = new HttpPut(url);
    return executeHttpRequest(targetClass, put, toPut, null, credentials);

  }

  public static <T> T put(final String url, final Class targetClass, final File file, final String... credentials) {

    final HttpPut put = new HttpPut(url);
    return executeHttpRequest(targetClass, put, null, file, credentials);

  }

  public static <T> T post(final String url, final Class targetClass, final AbstractCouchDBDocument toPost, final String... credentials) {

    final HttpPost put = new HttpPost(url);
    return executeHttpRequest(targetClass, put, toPost, null, credentials);

  }

  public static <T> T delete(final String url, final Class targetClass, final String... credentials) {

    final HttpDelete delete = new HttpDelete(url);
    return executeHttpRequest(targetClass, delete, null, null, credentials);

  }

  private static <T> T executeHttpRequest(final Class targetClass, final HttpRequestBase request, final AbstractCouchDBDocument requestBody, final File file, final String... credentials) {

    final CloseableHttpClient client = HttpClients.createDefault();
    setBasicAuthentication(credentials, request);
    setJSONContentType(request);

    if (requestBody != null) {
      if (request.getMethod().equals("POST")) {
        final HttpPost post = (HttpPost) request;
        setEntity(post, requestBody);
        return execute(client, post, targetClass);
      } else if (request.getMethod().equals("PUT")) {
        final HttpPut put = (HttpPut) request;
        setEntity(put, requestBody);
        return execute(client, put, targetClass);
      }
    }

    if (file != null) {
      setFileContentType(request, file);
      final HttpPut put = (HttpPut) request;
      setEntity(put, file);
      return execute(client, put, targetClass);
    }

    return execute(client, request, targetClass);

  }

  private static void setEntity(final HttpPut put, final File file) {
    put.setEntity(new FileEntity(file));
  }

  private static void setEntity(final HttpPut put, final AbstractCouchDBDocument requestBody) {
    try {
      put.setEntity(new StringEntity(objectToJSON(requestBody)));
    } catch (UnsupportedEncodingException e) {
      throw new Couch4JException(e);
    }
  }

  private static void setEntity(final HttpPost post, final AbstractCouchDBDocument requestBody) {
    try {
      post.setEntity(new StringEntity(objectToJSON(requestBody)));
    } catch (UnsupportedEncodingException e) {
      throw new Couch4JException(e);
    }
  }

  private static <T> T execute(final CloseableHttpClient client, final HttpRequestBase request, final Class targetClass) {
    CloseableHttpResponse response = null;
    try {
      response = client.execute(request);
      if(response.getStatusLine().getStatusCode() == 404) {
        throw new Couch4JException("Object not found");
      }
      return readInputStream(response.getEntity().getContent(), targetClass);
    } catch (IOException e) {
      throw new Couch4JException(e);
    } finally {
      safeClose(response);
    }
  }

  private static String createBasicAuthenticationValue(final String username, final String password) {
    final String userCredentials = username + ":" + password;
    final String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
    return basicAuth;
  }

  public static void safeClose(final CloseableHttpResponse response) {
    try {
      response.close();
    } catch (Exception e) {
      // nothing to be done with exception
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
