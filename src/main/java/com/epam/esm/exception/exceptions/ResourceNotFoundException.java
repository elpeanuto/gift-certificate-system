package com.epam.esm.exception.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(int id) {
        super("Requested resource not found (id = " + id + ")");
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
