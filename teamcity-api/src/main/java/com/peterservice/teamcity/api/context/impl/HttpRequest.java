package com.peterservice.teamcity.api.context.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class HttpRequest implements AutoCloseable {

    private CloseableHttpClient httpClient;
    private String serviceURL;
    private HttpClientContext context;
    private HttpHost targetHost;
    private BasicHeader originHeader;

    private final static String HTTPS_SCHEME = "https";
    private final static int HTTPS_PORT = 443;
    private final static String APPLICATION_JSON_HEADER = "application/json";
    private final static String APPLICATION_XML_HEADER = "application/xml";

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);

    public HttpRequest(String serviceURL, String user, String password) {
        this.serviceURL = serviceURL;
        this.originHeader = new BasicHeader("Origin", HTTPS_SCHEME + "://" + serviceURL);

        initHttpClient(user, password);
    }

    private void initHttpClient(String user, String password) {

        targetHost = new HttpHost(serviceURL, HTTPS_PORT, HTTPS_SCHEME);

        // Create CredentialsProvider
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, password);
        credentialsProvider.setCredentials(new AuthScope(targetHost), credentials);

        // Create AuthCache to store cookies for a particular host
        AuthCache authCache = new BasicAuthCache();
        authCache.put(targetHost, new BasicScheme());

        // Add credentials and auth cache to context
        context = HttpClientContext.create();
        context.setCredentialsProvider(credentialsProvider);
        context.setAuthCache(authCache);

        // Secure connection (Optional: to specify protocols)
        try {
            SSLContext sslContext = SSLContexts.custom().build();
            SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"},
                    null,
                    NoopHostnameVerifier.INSTANCE);

            httpClient = HttpClientBuilder.create()
                    .setSSLSocketFactory(sf)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public JsonObject doGet(String apiPath) throws IOException, URISyntaxException {

        // Build URI
        URIBuilder builder = new URIBuilder();
        builder.setScheme(HTTPS_SCHEME).setHost(serviceURL).setPort(HTTPS_PORT).setPath(apiPath);

        HttpUriRequest httpGet = RequestBuilder.get()
                .setUri(builder.build())
                .addHeader(HttpHeaders.ACCEPT, APPLICATION_JSON_HEADER)
                .addHeader(originHeader)
                .build();

        try (CloseableHttpResponse httpResponse = httpClient.execute(targetHost, httpGet, context)) {

            LOGGER.debug(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            LOGGER.debug(httpResponse.getStatusLine().getReasonPhrase());

            try {
                return new JsonParser().parse(EntityUtils.toString(httpResponse.getEntity())).getAsJsonObject();
            } catch (JsonSyntaxException | IOException | ParseException e) {
                return new JsonObject();
            }
        }
    }

    // TODO: boolean or JsonObject?
    public boolean doPost(String apiPath, String postBody, boolean isJson) throws IOException, URISyntaxException {

        // Build URI
        URIBuilder builder = new URIBuilder();
        builder.setScheme(HTTPS_SCHEME).setHost(this.serviceURL).setPort(HTTPS_PORT).setPath(apiPath);

        StringEntity entity = new StringEntity(postBody);

        HttpUriRequest httpPost = RequestBuilder.post()
                .setUri(builder.build())
                .setEntity(entity)
                .addHeader(HttpHeaders.CONTENT_TYPE, isJson ? APPLICATION_JSON_HEADER : APPLICATION_XML_HEADER)
                .addHeader(HttpHeaders.ACCEPT, APPLICATION_JSON_HEADER)
                .addHeader(originHeader)
                .build();

        try (CloseableHttpResponse httpResponse = httpClient.execute(targetHost, httpPost, context)) {

            LOGGER.debug(EntityUtils.toString(httpResponse.getEntity()));
            LOGGER.debug(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            LOGGER.debug(httpResponse.getStatusLine().getReasonPhrase());

            return httpResponse.getStatusLine().getStatusCode() == 200;
        }
    }

    public boolean doPut(String apiPath, String postBody, boolean isJson) throws IOException, URISyntaxException {

        // Build URI
        URIBuilder builder = new URIBuilder();
        builder.setScheme(HTTPS_SCHEME).setHost(this.serviceURL).setPort(HTTPS_PORT).setPath(apiPath);

        StringEntity entity = new StringEntity(postBody);

        HttpUriRequest httpPost = RequestBuilder.put()
                .setUri(builder.build())
                .setEntity(entity)
                .addHeader(HttpHeaders.CONTENT_TYPE, isJson ? APPLICATION_JSON_HEADER : APPLICATION_XML_HEADER)
                .addHeader(HttpHeaders.ACCEPT, APPLICATION_JSON_HEADER)
                .addHeader(originHeader)
                .build();

        try (CloseableHttpResponse httpResponse = httpClient.execute(targetHost, httpPost, context)) {

            LOGGER.debug(EntityUtils.toString(httpResponse.getEntity()));
            LOGGER.debug(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            LOGGER.debug(httpResponse.getStatusLine().getReasonPhrase());

            return httpResponse.getStatusLine().getStatusCode() == 200;
        }
    }

    public boolean doDelete(String apiPath) throws IOException, URISyntaxException {

        // Build URI
        URIBuilder builder = new URIBuilder();
        builder.setScheme(HTTPS_SCHEME).setHost(serviceURL).setPort(HTTPS_PORT).setPath(apiPath);

        HttpUriRequest httpDelete = RequestBuilder.delete()
                .addHeader(originHeader)
                .setUri(builder.build())
                .build();

        try (CloseableHttpResponse httpResponse = httpClient.execute(targetHost, httpDelete, context)) {

            LOGGER.debug(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            LOGGER.debug(httpResponse.getStatusLine().getReasonPhrase());

            return httpResponse.getStatusLine().getStatusCode() == 204;
        }
    }

    @Override
    public void close() throws Exception {
        httpClient.close();
    }
}
