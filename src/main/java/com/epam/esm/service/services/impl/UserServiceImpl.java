package com.epam.esm.service.services.impl;

import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.converter.UserConverter;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.repository.api.CRUDRepository;
import com.epam.esm.service.services.api.CRUDService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.model.converter.UserConverter.toDto;

@Service
public class UserServiceImpl implements CRUDService<UserDTO, Pagination> {

    private final CRUDRepository<UserEntity, Pagination> repository;

    @Autowired
    public UserServiceImpl(CRUDRepository<UserEntity, Pagination> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<UserDTO> getAll(Pagination pagination) {
        return repository.getAll(pagination).stream()
                .map(UserConverter::toDto)
                .toList();
    }

    @Override
    @Transactional
    public UserDTO getById(long id) {
        UserEntity entity = repository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return toDto(entity);
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional
    public UserDTO delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional
    public UserDTO update(long id, UserDTO userDTO) {
        throw new UnsupportedOperationException();
    }
}
