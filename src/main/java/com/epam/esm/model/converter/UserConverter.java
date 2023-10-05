package com.epam.esm.model.converter;

import com.epam.esm.model.constant.UserRole;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.RoleEntity;
import com.epam.esm.model.entity.UserEntity;

/**
 * The UserConverter class provides static methods for converting between
 * UserEntity and UserDTO objects.
 */
public class UserConverter {

    private UserConverter() {

    }

    public static UserDTO toDto(UserEntity entity) {
        return new UserDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPassword(),
                UserRole.valueOf(entity.getRole().getName())
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
