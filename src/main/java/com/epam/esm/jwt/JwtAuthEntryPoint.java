package com.epam.esm.jwt;

import com.epam.esm.exception.model.CustomHttpStatus;
import com.epam.esm.exception.model.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * JwtAuthEntryPoint is a component that handles authentication failures by returning
 * an error response with a status code of 401 (Unauthorized) in JSON format.
 * <p>
 * This class implements the AuthenticationEntryPoint interface to provide a custom
 * behavior when authentication fails. It serializes an ErrorResponse object to JSON
 * and sends it as the response to the client.
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    /**
     * Commence method is called when authentication fails. It prepares and sends
     * an error response to the client.
     *
     * @param request        The HTTP request that resulted in the authentication failure.
     * @param response       The HTTP response to which the error response will be written.
     * @param authException  The exception representing the authentication failure.
     * @throws IOException  If an I/O error occurs while writing the error response.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(
                authException.getMessage(),
                CustomHttpStatus.UNAUTHORIZED.getValue()
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(jsonResponse);
    }
}

