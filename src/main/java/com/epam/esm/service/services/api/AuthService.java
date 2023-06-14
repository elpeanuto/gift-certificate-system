package com.epam.esm.service.services.api;

import com.epam.esm.model.dto.UserDTO;
import org.apache.catalina.User;

public interface AuthService {

    UserDTO registration(UserDTO userDTO);

    String authentication(UserDTO userDTO);
}
