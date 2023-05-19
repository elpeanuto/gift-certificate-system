package com.epam.esm.service.services.impl;

import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.converter.OrderConverter;
import com.epam.esm.model.converter.UserConverter;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.UserOrderDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.repository.api.CRUDRepository;
import com.epam.esm.repository.api.OrderRepository;
import com.epam.esm.service.services.api.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.model.converter.UserConverter.toDto;

@Service
public class UserServiceImpl implements UserService {

    private final CRUDRepository<UserEntity, Pagination> userRepo;
    private final OrderRepository orderRepo;

    @Autowired
    public UserServiceImpl(CRUDRepository<UserEntity, Pagination> userRepo,
                           OrderRepository orderRepo) {
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
    }

    @Override
    @Transactional
    public List<UserDTO> getAll(Pagination pagination) {
        return userRepo.getAll(pagination).stream()
                .map(UserConverter::toDto)
                .toList();
    }

    @Override
    @Transactional
    public UserDTO getById(long id) {
        UserEntity entity = userRepo.getById(id)
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

    @Override
    public List<UserOrderDTO> getOrders(long id, Pagination pagination) {
        UserEntity userEntity = userRepo.getById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        List<OrderEntity> orders = orderRepo.getByUserId(userEntity.getId(), pagination);

        return orders.stream()
                .map(OrderConverter::orderToUserOrder)
                .toList();
    }
}
