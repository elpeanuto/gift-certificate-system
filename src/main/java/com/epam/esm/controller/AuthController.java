package com.epam.esm.controller;

import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.model.dto.AuthenticationRequestDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.hateoas.UserLinker;
import com.epam.esm.service.services.api.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A RestController class that handles API requests related to authentication.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor for AuthController class
     *
     * @param authService authentication service
     */
    @Autowired
    public AuthController(
            AuthService authService) {
        this.authService = authService;
    }

    /**
     * Method which authenticate users
     *
     * @param request AuthenticationRequestDTO with email and password
     * @return JWT
     */
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(
            @RequestBody AuthenticationRequestDTO request
    ) {
        Optional<String> authentication = authService.authentication(request);

        return authentication.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(400).body("error"));
    }

    /**
     * Method which saves user info in db
     *
     * @param userDTO user info
     * @param bindingResult binding results for validation
     * @return registered user
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registration(@RequestBody @Valid UserDTO userDTO,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            String str = String.join(", ", errorMessages);

            logger.warn(str);
            throw new InvalidRequestBodyException(str);
        }

        UserDTO body = authService.registration(userDTO);

        UserLinker.bindLinks(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

}
