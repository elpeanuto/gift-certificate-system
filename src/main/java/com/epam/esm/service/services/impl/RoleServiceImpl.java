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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.model.converter.RoleConverter.toDto;
import static com.epam.esm.model.converter.RoleConverter.toEntity;

/**
 * Implementation of the RoleService interface for managing Role objects.
 * Provides methods for retrieving, creating, and deleting roles.
 *
 * @see RoleService
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepo;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
                .orElseThrow(() -> {
                    logger.error("Role with ID {} not found.", id);
                    throw new ResourceNotFoundException(id);
                });

        return toDto(entity);
    }

    @Override
    @Transactional
    public RoleDTO create(RoleDTO roleDTO) {
        if (roleRepo.getByName(roleDTO.getName()).isPresent()) {
            logger.error("Role with name {} already exists.", roleDTO.getName());
            throw new EntityAlreadyExistsException();
        }
        return toDto(roleRepo.create(toEntity(roleDTO)));
    }

    @Override
    @Transactional
    public RoleDTO delete(long id) {
        RoleEntity entity = roleRepo.delete(id);

        if (entity == null) {
            logger.error("Role with ID {} not found.", id);
            throw new ResourceNotFoundException(id);
        }

        return toDto(entity);
    }

    @Override
    @Transactional
    public RoleDTO getByName(String name) {
        RoleEntity entity = roleRepo.getByName(name)
                .orElseThrow(() -> {
                    logger.error("Role with name {} not found.", name);
                    return new ResourceNotFoundException(name);
                });

        return RoleConverter.toDto(entity);
    }
}
