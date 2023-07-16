package com.epam.esm.service.services.api;

import com.epam.esm.model.dto.AuthenticationRequestDTO;
import com.epam.esm.model.dto.JwtResponseDTO;
import com.epam.esm.model.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public interface AuthService {

    UserDTO registration(UserDTO userDTO);

    Optional<JwtResponseDTO> authentication(AuthenticationRequestDTO requestDTO);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
