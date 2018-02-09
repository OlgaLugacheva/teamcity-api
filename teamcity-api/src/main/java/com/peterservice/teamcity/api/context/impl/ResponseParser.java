package com.peterservice.teamcity.api.context.impl;

import com.peterservice.teamcity.api.exceptions.TeamCityException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ResponseParser {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T parseJsonResponse(HttpResponse response, Class<T> responseClass) {
        if (!ContentType.get(response.getEntity()).getMimeType().equals(ContentType.APPLICATION_JSON.getMimeType())) {
            throw new TeamCityException("TeamCity response is not JSON as expected");
        }
        try {
            String responseString = EntityUtils.toString(response.getEntity());
            return mapper.readValue(responseString, responseClass);
        } catch (IOException e) {
            throw new TeamCityException("Reading/parsing TeamCity response has failed", e);
        }
    }
}
