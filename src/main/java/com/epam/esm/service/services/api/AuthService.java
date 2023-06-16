package com.epam.esm.service.services.api;

import com.epam.esm.model.dto.AuthenticationRequestDTO;
import com.epam.esm.model.dto.UserDTO;

import java.util.Optional;

public interface AuthService {

    UserDTO registration(UserDTO userDTO);

    Optional<String> authentication(AuthenticationRequestDTO requestDTO);
}
