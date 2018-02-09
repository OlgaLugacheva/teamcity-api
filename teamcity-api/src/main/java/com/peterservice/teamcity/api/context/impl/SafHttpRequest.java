package com.peterservice.teamcity.api.context.impl;

import com.peterservice.teamcity.api.exceptions.HttpRequestException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Denis.Fedoseev on 15.03.2016.
 */
public class SafHttpRequest {
    private URI serverAddress;
    private String serverLogin;
    private String serverPassword;
    private String apiPath;


    public static SafHttpRequest init(URI server, String serverLogin, String serverPassword, String apiPath) {
        SafHttpRequest object = new SafHttpRequest(server, serverLogin, serverPassword, apiPath);
        return object;
    }

    public SafHttpRequest(URI server, String serverLogin, String serverPassword, String apiPath) {
        serverAddress = server;
        this.serverLogin = serverLogin;
        this.serverPassword = serverPassword;
        this.apiPath = apiPath;
    }

    public JSONObject createGetRequest(String apiPath, Map<String, String> queryParams) throws URISyntaxException, IOException, HttpRequestException {
        JSONObject jsonObj = null;

        HttpHost target = new HttpHost(this.serverAddress.getHost().toString(), 443, "https");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(target.getHostName(), target.getPort()), new UsernamePasswordCredentials(this.serverLogin, this.serverPassword));
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

        //Убираем путь до рест апи, т.к. он иногда идет по ссылкам в других кусках апи
        int endIndex = 0;
        String pathToTrim = apiPath;
//        System.out.println("path to trim" + pathToTrim);
        try {
            Pattern pattern = Pattern.compile("httpAuth/app/rest");
            Matcher matcher = pattern.matcher(pathToTrim);
            matcher.find();
            endIndex = matcher.end();
        } catch (IllegalStateException e) {
            //No actions needed
        }

        if (endIndex > 0) {
            apiPath = pathToTrim.substring(endIndex, pathToTrim.length());
        }


        try {
            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate BASIC scheme object and add it to the local
            // auth cache
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(target, basicAuth);

            // Add AuthCache to the execution context
            HttpClientContext localContext = HttpClientContext.create();
            localContext.setAuthCache(authCache);

            URIBuilder builder = new URIBuilder();
            String urlPath = "/httpAuth/app/rest" + apiPath;
            urlPath = urlPath.replace("//", "/");
            builder.setScheme("https").setHost(this.serverAddress.getHost().toString()).setPort(443).setPath(urlPath);

            if (queryParams != null) {
                for (Map.Entry<String, String> param : queryParams.entrySet()) {
                    String key = param.getKey();
                    String value = param.getValue();
                    if (value != null && !value.isEmpty()) {
                        builder.setParameter(key, value);
                    }
                }
            }

            HttpGet httpget = new HttpGet(builder.build());
            httpget.setHeader("content-type", "application/json");//"application/x-www-form-urlencoded");
            httpget.setHeader("Accept", "application/json");//"application/x-www-form-urlencoded");

//            System.out.println("BUILDER "+builder.toString());

            CloseableHttpResponse response = httpclient.execute(target, httpget, localContext);
            try {
//                System.out.println("Status " + response.getStatusLine().toString());
                if (response.getStatusLine() != null && response.getStatusLine().toString().contains("200 OK")) {
                    jsonObj = new JSONObject(EntityUtils.toString(response.getEntity()));
                } else {
                    throw new HttpRequestException("Request error! " + response.getStatusLine());
                }
            } catch (IllegalStateException e) {

            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return jsonObj;
    }

    public String createPostRequest(String apiPath, Map<String, String> queryParams, String xmlString) throws URISyntaxException, IOException, HttpRequestException, UnirestException {

        HttpHost target = new HttpHost(this.serverAddress.getHost().toString(), 443, "https");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(target.getHostName(), target.getPort()), new UsernamePasswordCredentials(this.serverLogin, this.serverPassword));
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

        int endIndex = 0;
        String pathToTrim = apiPath;
        try {
            Pattern pattern = Pattern.compile("httpAuth/app/rest");
            Matcher matcher = pattern.matcher(pathToTrim);
            matcher.find();
            endIndex = matcher.end();
        } catch (IllegalStateException e) {
        }

        if (endIndex > 0) {
            apiPath = pathToTrim.substring(endIndex, pathToTrim.length());
        }

        try {
            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate BASIC scheme object and add it to the local
            // auth cache
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(target, basicAuth);

            // Add AuthCache to the execution context
            HttpClientContext localContext = HttpClientContext.create();
            localContext.setAuthCache(authCache);

            URIBuilder builder = new URIBuilder();
            String urlPath = "/httpAuth/app/rest" + apiPath;
            urlPath = urlPath.replace("//", "/");
            builder.setScheme("https").setHost(this.serverAddress.getHost().toString()).setPort(443).setPath(urlPath);

            if (queryParams != null) {
                for (Map.Entry<String, String> param : queryParams.entrySet()) {
                    String key = param.getKey();
                    String value = param.getValue();
                    if (value != null && !value.isEmpty()) {
                        builder.setParameter(key, value);
                    }
                }
            }
            HttpResponse<String> response = Unirest.post(String.valueOf(builder.build()))
                    .header("Content-Type", "application/xml")
                    .header("Cache-Control", "no-cache")
                    .body(xmlString)
                    .asString();
        } finally {
            httpclient.close();
        }
        return "";
    }
}
