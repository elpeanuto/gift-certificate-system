package com.epam.esm.controller;

import com.epam.esm.config.JwtUtils;
import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.model.dto.AuthenticationRequestDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.hateoas.UserLinker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class Authentication {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @Autowired
    public Authentication(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(
            @RequestBody AuthenticationRequestDTO request
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());

        if(user == null){
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }

        return  ResponseEntity.status(400).body("error");
    }


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

        UserDTO body = service.create(userDTO);

        UserLinker.bindLinks(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

}
