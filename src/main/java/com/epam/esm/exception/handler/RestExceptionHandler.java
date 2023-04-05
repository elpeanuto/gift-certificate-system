package com.epam.esm.exception.handler;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<ErrorResponse> handleMyCustomException(RepositoryException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Integer.toString(status.value()));

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, String> handleNotFoundException(HttpServletRequest request, NoHandlerFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
        response.put("error", "Not Found");
        response.put("message", "The requested resource was not found");
        response.put("path", request.getRequestURI());
        return response;
    }
}