package com.epam.esm.exception.handler;

import com.epam.esm.exception.exceptions.EntityAlreadyExistsException;
import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.exception.model.CustomHttpStatus;
import com.epam.esm.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<ErrorResponse> handleMyCustomException(RepositoryException e) {
        CustomHttpStatus status = CustomHttpStatus.REPOSITORY_ERROR;

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),
                Integer.toString(status.getValue()));

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMyCustomException(ResourceNotFoundException e) {
        CustomHttpStatus status = CustomHttpStatus.RESOURCE_NOT_FOUND;

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),
                Integer.toString(status.getValue()));

        e.printStackTrace();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleMyCustomException(EntityAlreadyExistsException e) {
        CustomHttpStatus status = CustomHttpStatus.RESOURCE_NOT_FOUND;

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),
                Integer.toString(status.getValue()));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        CustomHttpStatus status = CustomHttpStatus.NOT_READABLE;

        ErrorResponse errorResponse = new ErrorResponse(status.getReasonPhrase(),
                Integer.toString(status.getValue()));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestBodyException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(InvalidRequestBodyException ex) {
        CustomHttpStatus status = CustomHttpStatus.INVALID_REQUEST_BODY;

        ErrorResponse errorResponse = new ErrorResponse(status.getReasonPhrase() + ": " + ex.getMessage(),
                Integer.toString(status.getValue()));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}