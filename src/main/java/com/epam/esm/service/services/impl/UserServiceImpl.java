package com.epam.esm.service.services.impl;

import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.converter.OrderConverter;
import com.epam.esm.model.converter.UserConverter;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.PaginatedResponse;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.UserOrderDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.model.entity.RoleEntity;
import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.repository.api.OrderRepository;
import com.epam.esm.repository.api.RoleRepository;
import com.epam.esm.repository.api.UserRepository;
import com.epam.esm.service.services.api.CRDService;
import com.epam.esm.service.services.api.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.model.converter.UserConverter.toDto;

/**
 * Implementation of the UserService interface for managing User objects.
 * Provides methods for retrieving, creating, updating and deleting users.
 *
 * @see UserService
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final OrderRepository orderRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo,
                           RoleRepository roleRepo,
                           OrderRepository orderRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.orderRepo = orderRepo;
    }

    @Override
    @Transactional
    public PaginatedResponse<UserDTO> getAll(Pagination pagination) {
        List<UserDTO> userDTOS = userRepo.getAll(pagination).stream()
                .map(UserConverter::toDto)
                .toList();

        return new PaginatedResponse<>(userDTOS, null);
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
        UserEntity entity = UserConverter.toEntity(userDTO);

        RoleEntity userRole = roleRepo.getByName(entity.getRole().getName())
                .orElseThrow(() -> new ResourceNotFoundException(entity.getRole().getName()));

        entity.setRole(userRole);

        return UserConverter.toDto(userRepo.create(entity));
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
    public UserOrderDTO getOrderInfo(long id, long orderId) {
        UserEntity userEntity = userRepo.getById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        OrderEntity orderEntity = orderRepo.getById(orderId).orElseThrow(() -> new ResourceNotFoundException(id));

        OrderEntity order = orderRepo.getByUserOrderId(userEntity.getId(), orderEntity.getId());

        return OrderConverter.orderToUserOrder(order);
    }

    @Override
    public UserDTO getByEmail(String email) {
        UserEntity entity = userRepo.getByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(email));

        return toDto(entity);
    }

    @Override
    public List<OrderDTO> getOrders(long id, Pagination pagination) {
        UserEntity userEntity = userRepo.getById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        List<OrderEntity> orders = orderRepo.getByUserId(userEntity.getId(), pagination);

        return orders.stream()
                .map(OrderConverter::toDto)
                .toList();
    }
}
