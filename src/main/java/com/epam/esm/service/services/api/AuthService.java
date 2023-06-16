package com.epam.esm.service.services.api;

import com.epam.esm.model.dto.AuthenticationRequestDTO;
import com.epam.esm.model.dto.UserDTO;

import java.util.Optional;

/**
 *  This interface represents an auth service.
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
     * Authenticate user if data is correct
     *
     * @param requestDTO Email and password
     * @return JWT
     */
    Optional<String> authentication(AuthenticationRequestDTO requestDTO);
}
