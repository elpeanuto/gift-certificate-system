package com.epam.esm.service.services.impl;

import com.epam.esm.exception.exceptions.EntityAlreadyExistsException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.converter.RoleConverter;
import com.epam.esm.model.dto.PaginatedResponse;
import com.epam.esm.model.dto.RoleDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.RoleEntity;
import com.epam.esm.repository.api.RoleRepository;
import com.epam.esm.service.services.api.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.model.converter.RoleConverter.toDto;
import static com.epam.esm.model.converter.RoleConverter.toEntity;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepo;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    @Transactional
    public PaginatedResponse<RoleDTO> getAll(Pagination pagination) {
        List<RoleDTO> roleDTOS = roleRepo.getAll(pagination).stream()
                .map(RoleConverter::toDto)
                .toList();

        return new PaginatedResponse<>(roleDTOS, null);
    }

    @Override
    @Transactional
    public RoleDTO getById(long id) {
        RoleEntity entity = roleRepo.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return toDto(entity);
    }

    @Override
    @Transactional
    public RoleDTO create(RoleDTO roleDTO) {
        if (roleRepo.getByName(roleDTO.getName()).isPresent())
            throw new EntityAlreadyExistsException();

        return toDto(roleRepo.create(toEntity(roleDTO)));
    }

    @Override
    @Transactional
    public RoleDTO delete(long id) {
        RoleEntity entity = roleRepo.delete(id);

        if (entity == null)
            throw new ResourceNotFoundException(id);

        return toDto(entity);
    }

    @Override
    public RoleDTO getByName(String name) {
        RoleEntity entity = roleRepo.getByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(name));

        return RoleConverter.toDto(entity);
    }
}
