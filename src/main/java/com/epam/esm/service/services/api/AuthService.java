package com.epam.esm.service.services.api;

import com.epam.esm.model.dto.AuthenticationRequestDTO;
import com.epam.esm.model.dto.JwtResponseDTO;
import com.epam.esm.model.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

/**
 * This interface represents an auth service.
 */
public interface AuthService {

    /**
     * Register user if data is correct
     *
     * @param userDTO User info
     * @return Registered user
     */
    UserDTO registration(UserDTO userDTO);

    /**
     * Refreshes the JWT token.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @throws IOException if an I/O error occurs while refreshing the token
     */
    Optional<JwtResponseDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * Authenticate user if data is correct
     *
     * @param requestDTO Email and password
     * @return JWT
     */
    Optional<JwtResponseDTO> authentication(AuthenticationRequestDTO requestDTO);
}
