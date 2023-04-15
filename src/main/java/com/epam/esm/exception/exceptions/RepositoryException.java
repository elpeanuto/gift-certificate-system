package com.epam.esm.exception.exceptions;

public class RepositoryException extends RuntimeException {

    public RepositoryException() {
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryException(Throwable cause) {
        super(cause);
    }

    public static String standardMessage(String className, String methodName, Throwable cause) {
        return cause.getClass().getSimpleName() + " in " + className + " " + methodName + " method.";
    }
}
