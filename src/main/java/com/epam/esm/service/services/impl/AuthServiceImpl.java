package com.epam.esm.service.services.impl;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.service.services.api.AuthService;
import com.epam.esm.service.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO registration(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userService.create(userDTO);
    }

    @Override
    public String authentication(UserDTO userDTO) {
        return null;
    }
}
