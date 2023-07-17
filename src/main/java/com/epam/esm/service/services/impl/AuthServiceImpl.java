package com.epam.esm.service.services.impl;

import com.epam.esm.config.JwtUtils;
import com.epam.esm.exception.model.CustomHttpStatus;
import com.epam.esm.exception.model.ErrorResponse;
import com.epam.esm.model.constant.UserRole;
import com.epam.esm.model.dto.AuthenticationRequestDTO;
import com.epam.esm.model.dto.JwtResponseDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.UserDetailsAdapter;
import com.epam.esm.service.services.api.AuthService;
import com.epam.esm.service.services.api.GiftCertificateService;
import com.epam.esm.service.services.api.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * This class provides implementation of AuthService interface.
 * It performs operations related to Authentication and authorization.
 *
 * @see AuthService
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthServiceImpl(
            UserService userService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtUtils jwtUtils) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDTO registration(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setRole(UserRole.USER_ROLE);

        return userService.create(userDTO);
    }

    @Override
    public Optional<JwtResponseDTO> authentication(AuthenticationRequestDTO requestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword())
        );

        UserDetails user = userDetailsService.loadUserByUsername(requestDTO.getEmail());

        if (user != null) {
            JwtResponseDTO jwtResponse = new JwtResponseDTO(jwtUtils.generateAccessToken(user),
                    jwtUtils.generateRefreshToken(user));

            return Optional.of(jwtResponse);
        }

        return Optional.empty();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String userEmail;
        final String refreshToken;

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return;
        }

        refreshToken = authHeader.substring(7);

        try {
            userEmail = jwtUtils.extractUsername(refreshToken);

            if (userEmail != null) {
                UserDetails userDetails = new UserDetailsAdapter(userService.getByEmail(userEmail));

                boolean refreshTokenValid = jwtUtils.isRefreshTokenValid(refreshToken, userDetails);

                if (refreshTokenValid) {
                    String accessToken = jwtUtils.generateAccessToken(userDetails);
                    JwtResponseDTO authResponse = new JwtResponseDTO(
                            accessToken,
                            refreshToken
                    );

                    new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                }
                else {
                    throw new JwtException("Wrong jwt");
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
        }
    }
}
