package com.standardstate.couch4j.util;

import com.standardstate.couch4j.AbstractCouchDBDocument;
import com.standardstate.couch4j.Couch4JException;
import com.standardstate.couch4j.options.Options;
import static com.standardstate.couch4j.util.Utils.buildURI;
import static com.standardstate.couch4j.util.Utils.objectToJSON;
import static com.standardstate.couch4j.util.Utils.readInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HTTP {

    private static CloseableHttpClient CLIENT = HttpClients.createDefault();
    
    public static void setHttpClient(final CloseableHttpClient client) {
        CLIENT = client;
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

        setBasicAuthentication(credentials, request);
        setJSONContentType(request);

        if (requestBody != null) {
            if (request.getMethod().equals("POST")) {
                final HttpPost post = (HttpPost) request;
                setEntity(post, requestBody);
                return execute(post, targetClass);
            } else if (request.getMethod().equals("PUT")) {
                final HttpPut put = (HttpPut) request;
                setEntity(put, requestBody);
                return execute(put, targetClass);
            }
        }

        if (file != null) {
            setFileContentType(request, file);
            final HttpPut put = (HttpPut) request;
            setEntity(put, file);
            return execute(put, targetClass);
        }

        return execute(request, targetClass);

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

    private static <T> T execute(final HttpRequestBase request, final Class targetClass) {
        
        CloseableHttpResponse response = null;
        
        // Here we see if we init an http client or take the one already in 
        // the class. This is an attempt for better unit testing.
        if(CLIENT == null) {
            CLIENT = HttpClients.createDefault();
        }
        
        try {
            response = CLIENT.execute(request);
            if (response.getStatusLine().getStatusCode() == 404) {
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
    
}
