package com.epam.esm.model.converter;

import com.epam.esm.model.dto.RoleDTO;
import com.epam.esm.model.entity.RoleEntity;

public class RoleConverter {

    private RoleConverter() {

    }

    public static RoleDTO toDto(RoleEntity entity) {
        return new RoleDTO(entity.getId(), entity.getName());
    }

    public static RoleEntity toEntity(RoleDTO dto) {
        return new RoleEntity(dto.getId(), dto.getName());
    }
}
