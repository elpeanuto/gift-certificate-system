package com.epam.esm.service;

import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.converter.OrderConverter;
import com.epam.esm.model.converter.UserConverter;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.UserOrderDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.repository.api.CRUDRepository;
import com.epam.esm.repository.api.OrderRepository;
import com.epam.esm.service.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private CRUDRepository<UserEntity, Pagination> userRepo;

    @Mock
    private OrderRepository orderRepo;

    private UserServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new UserServiceImpl(userRepo, roleRepo, orderRepo);
    }

    @Test
    void getAllTest() {
        List<UserEntity> entityList = List.of(
                new UserEntity(1L, "John", "Doe", "john.doe@example.com", "password1"),
                new UserEntity(2L, "Jane", "Smith", "jane.smith@example.com", "password2")
        );

        List<UserDTO> dtoList = entityList.stream()
                .map(UserConverter::toDto)
                .toList();

        Pagination pagination = new Pagination(0, 5);
        when(userRepo.getAll(pagination)).thenReturn(entityList);
        
        List<UserDTO> result = service.getAll(pagination);
        
        Assertions.assertEquals(dtoList, result);
    }

    @Test
    void getByIdTest() {
        long id = 1L;
        UserEntity entity = new UserEntity(1L, "John", "Doe", "john.doe@example.com", "password1");
        when(userRepo.getById(id)).thenReturn(Optional.of(entity));
        
        UserDTO result = service.getById(id);
        
        Assertions.assertEquals(UserConverter.toDto(entity), result);
    }

    @Test
    void getByIdFailTest() {
        long id = -1L;
        when(userRepo.getById(id)).thenReturn(Optional.empty());
        
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
    }

    @Test
    void getOrdersTest() {
        UserEntity entity = new UserEntity(1L, "John", "Doe", "john.doe@example.com", "password1");
        List<OrderEntity> orderEntityList = List.of(
                new OrderEntity(1L, entity, new HashSet<>(), null, 0.0),
                new OrderEntity(2L, entity, new HashSet<>(), null, 0.0)
        );
        long id = 1L;
        Pagination pagination = new Pagination(0, 5);

        when(userRepo.getById(id)).thenReturn(Optional.of(entity));
        when(orderRepo.getByUserId(entity.getId(), pagination)).thenReturn(orderEntityList);
        
        List<OrderDTO> result = service.getOrders(id, pagination);
        
        Assertions.assertEquals(orderEntityList.stream().map(OrderConverter::toDto).toList(), result);
    }

    @Test
    void getOrdersInfoTest() {
        UserEntity entity = new UserEntity(1L, "John", "Doe", "john.doe@example.com", "password1");
        OrderEntity orderEntity = new OrderEntity(1L, entity, new HashSet<>(), null, 0.0);

        long id = 1L;
        Pagination pagination = new Pagination(0, 5);

        when(userRepo.getById(id)).thenReturn(Optional.of(entity));
        when(orderRepo.getById(id)).thenReturn(Optional.of(orderEntity));
        when(orderRepo.getByUserOrderId(entity.getId(), orderEntity.getId())).thenReturn(orderEntity);

        UserOrderDTO result = service.getOrderInfo(id, id);

        Assertions.assertEquals(OrderConverter.orderToUserOrder(orderEntity),  result);
    }

    @Test
    void createUserTest() {
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", "john.doe@example.com", "password1");
        UserEntity userEntity = UserConverter.toEntity(userDTO);

        when(userRepo.create(userEntity)).thenReturn(userEntity);
        UserDTO result = service.create(userDTO);

        assertEquals(userDTO, result);
    }

    @Test
    void getOrdersFailTest() {
        long id = 1L;
        Pagination pagination = new Pagination(0, 5);
        when(userRepo.getById(id)).thenReturn(Optional.empty());
        
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.getOrders(id, pagination));
    }
}