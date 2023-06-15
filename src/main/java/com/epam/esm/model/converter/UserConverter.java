package com.epam.esm.model.converter;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.RoleEntity;
import com.epam.esm.model.entity.UserEntity;

public class UserConverter {

    private UserConverter() {

    }

    public static UserDTO toDto(UserEntity entity) {
        return new UserDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                null
        );
    }

    public static UserEntity toEntity(UserDTO dto) {
        return new UserEntity(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPassword(),
                new RoleEntity(null, dto.getRole().getName())
        );
    }
}
