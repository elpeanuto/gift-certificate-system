package com.epam.esm.exception.handler;

import com.epam.esm.exception.exceptions.EntityAlreadyExistsException;
import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.exception.model.CustomHttpStatus;
import com.epam.esm.exception.model.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<ErrorResponse> handler(RepositoryException e) {
        CustomHttpStatus status = CustomHttpStatus.REPOSITORY_ERROR;

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),
               status.getValue());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handler(ResourceNotFoundException e) {
        CustomHttpStatus status = CustomHttpStatus.RESOURCE_NOT_FOUND;

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),
               status.getValue());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handler(EntityAlreadyExistsException e) {
        CustomHttpStatus status = CustomHttpStatus.RESOURCE_NOT_FOUND;

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),
               status.getValue());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handler(HttpMessageNotReadableException ex) {
        CustomHttpStatus status = CustomHttpStatus.NOT_READABLE;

        ErrorResponse errorResponse = new ErrorResponse(status.getReasonPhrase(),
               status.getValue());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestBodyException.class)
    public ResponseEntity<ErrorResponse> handler(InvalidRequestBodyException ex) {
        CustomHttpStatus status = CustomHttpStatus.INVALID_REQUEST_BODY;

        ErrorResponse errorResponse = new ErrorResponse(status.getReasonPhrase() + ": " + ex.getMessage(),
               status.getValue());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handler(DataIntegrityViolationException ex) {
        CustomHttpStatus status = CustomHttpStatus.DATA_INTEGRITY_VIOLATION;

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),
               status.getValue());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex) {
        CustomHttpStatus status = CustomHttpStatus.INVALID_ARGUMENT_TYPE;

        ErrorResponse errorResponse = new ErrorResponse(status.getReasonPhrase(),status.getValue());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handle(EmptyResultDataAccessException ex) {
        CustomHttpStatus status = CustomHttpStatus.NO_RESULT;

        ErrorResponse errorResponse = new ErrorResponse(status.getReasonPhrase(),status.getValue());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handle(AccessDeniedException ex) {
        CustomHttpStatus status = CustomHttpStatus.ACCESS_DENIED;

        ErrorResponse errorResponse = new ErrorResponse(status.getReasonPhrase(),status.getValue());

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}