package com.epam.esm.exception.model;

import org.springframework.http.HttpStatus;

public enum CustomHttpStatus {

    INVALID_REQUEST_BODY(40001, HttpStatus.Series.CLIENT_ERROR, "Invalid request body"),
    NOT_READABLE(40002, HttpStatus.Series.CLIENT_ERROR, "Http message not readable"),
    ALREADY_EXISTS_ERROR(40003, HttpStatus.Series.CLIENT_ERROR, "Already Exists"),
    INVALID_ARGUMENT_TYPE(40004, HttpStatus.Series.CLIENT_ERROR, "Invalid argument type"),

    UNAUTHORIZED(40101, HttpStatus.Series.CLIENT_ERROR, "Unauthorized"),

    RESOURCE_NOT_FOUND(40201, HttpStatus.Series.CLIENT_ERROR, "Resource not found"),
    NO_RESULT(40202, HttpStatus.Series.CLIENT_ERROR, "No result found"),

    ACCESS_DENIED(40301, HttpStatus.Series.CLIENT_ERROR, "Access denied"),

    DATA_INTEGRITY_VIOLATION(40901, HttpStatus.Series.CLIENT_ERROR, "Resource not found"),
    WRONG_TOKEN_ERROR(40901, HttpStatus.Series.CLIENT_ERROR, "Incorrect token"),

    REPOSITORY_ERROR(50001, HttpStatus.Series.SERVER_ERROR, "Repository error");


    private final int value;
    private final HttpStatus.Series series;
    private final String reasonPhrase;

    CustomHttpStatus(int value, HttpStatus.Series series, String reasonPhrase) {
        this.value = value;
        this.series = series;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return value;
    }

    public HttpStatus.Series getSeries() {
        return series;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
