package com.clocktower.teamcity.api.context.impl;

import com.clocktower.teamcity.api.exceptions.TeamCityException;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpHostConnectException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.ConnectException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestServiceTest {

    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse httpResponse;
    @Mock
    private StatusLine statusLine;
    @Mock
    private ResponseParser responseParser;

    @Test
    public void testGetRequest() throws IOException {
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(httpResponse);
        when(responseParser.parseJsonResponse(httpResponse, String.class)).thenReturn("Some string");

        RestService restService = new RestService("https://www.teamcity.billing.ru", httpClient, responseParser);
        assertEquals("Some string", restService.sendGetRequest("/someResource", String.class));
    }

    @Test(expected = TeamCityException.class)
    public void testGetRequestWithFailedConnection() throws IOException {
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(httpClient.execute(any(HttpUriRequest.class))).thenThrow(ConnectException.class);
        when(responseParser.parseJsonResponse(httpResponse, String.class)).thenReturn("Some string");

        RestService restService = new RestService("https://www.teamcity.billing.ru", httpClient, responseParser);
        restService.sendGetRequest("/someResource", String.class);
    }

    @Test(expected = TeamCityException.class)
    public void testGetRequestWithWrongStatusCode() throws IOException {
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_UNAUTHORIZED);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(httpResponse);
        when(responseParser.parseJsonResponse(httpResponse, String.class)).thenReturn("Some string");

        RestService restService = new RestService("https://www.teamcity.billing.ru", httpClient, responseParser);
        restService.sendGetRequest("/someResource", String.class);
    }
}