package com.epam.esm.jwt;

import com.epam.esm.exception.model.CustomHttpStatus;
import com.epam.esm.exception.model.ErrorResponse;
import com.epam.esm.model.dto.UserDetailsAdapter;
import com.epam.esm.service.services.api.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * JwtAuthFilter is a component responsible for handling JWT (JSON Web Token) authentication
 * within a Spring Security-enabled application. It intercepts incoming HTTP requests,
 * validates JWT tokens, and sets the authenticated user's context.
 * <p>
 * This filter checks for a JWT token in the "Authorization" header of the incoming request.
 * If a valid token is found, it attempts to authenticate the user associated with the token.
 * If successful, it sets the user's authentication context using Spring Security's
 * SecurityContextHolder.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserService userService;

    /**
     * Constructs a JwtAuthFilter with the required dependencies.
     *
     * @param jwtUtils    The utility class for JWT-related operations.
     * @param userService The service responsible for user-related operations.
     */
    @Autowired
    public JwtAuthFilter(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    /**
     * This method is called for each incoming HTTP request. It checks if a valid JWT token
     * is present in the "Authorization" header and attempts to authenticate the user
     * associated with the token.
     *
     * @param request     The HTTP request being processed.
     * @param response    The HTTP response to which authentication-related responses are written.
     * @param filterChain The filter chain for processing the request.
     * @throws ServletException If a servlet exception occurs during processing.
     * @throws IOException      If an I/O error occurs during processing.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull
            HttpServletResponse response,
            @NonNull
            FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String userEmail;
        final String jwtToken;

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);

        try {
            userEmail = jwtUtils.extractUsername(jwtToken);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = new UserDetailsAdapter(userService.getByEmail(userEmail));

                boolean accessTokenValid = jwtUtils.isAccessTokenValid(jwtToken, userDetails);

                if (accessTokenValid) {

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (JwtException e) {
            ErrorResponse errorResponse = new ErrorResponse(
                    CustomHttpStatus.WRONG_TOKEN_ERROR.getReasonPhrase(),
                    CustomHttpStatus.WRONG_TOKEN_ERROR.getValue()
            );

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getOutputStream().write(jsonResponse.getBytes());
            return;
        }

        filterChain.doFilter(request, response);
    }

}
