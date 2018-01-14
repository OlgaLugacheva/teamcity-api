package com.clocktower.teamcity.api.exceptions;

public class TeamCityException extends RuntimeException {

    public TeamCityException(Throwable cause) {
        super(cause);
    }

    public TeamCityException(String message) {
        super(message);
    }

    public TeamCityException(String message, Throwable cause) {
        super(message, cause);
    }
}
