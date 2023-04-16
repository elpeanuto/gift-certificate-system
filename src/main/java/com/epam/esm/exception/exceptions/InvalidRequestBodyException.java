package com.epam.esm.exception.exceptions;

public class InvalidRequestBodyException extends RuntimeException {

    public InvalidRequestBodyException() {
    }

    public InvalidRequestBodyException(String message) {
        super(message);
    }

    public InvalidRequestBodyException(String message, Throwable cause) {
        super(message, cause);
    }
}
