package com.epam.esm.service;

import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.constant.UserRole;
import com.epam.esm.model.converter.OrderConverter;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.model.entity.RoleEntity;
import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.repository.api.CRUDRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.service.services.api.CRUDService;
import com.epam.esm.service.services.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private OrderServiceImpl service;

    @Mock
    private CRUDRepository<OrderEntity, Pagination> orderRepository;

    @Mock
    private GiftCertificateRepositoryImpl certificateRepository;

    @Mock
    private CRUDRepository<UserEntity, Pagination> userRepository;

    @BeforeEach
    void setUp() {
        service = new OrderServiceImpl(orderRepository, certificateRepository, userRepository);
    }

    @Test
    void getAllTest() {
        UserEntity userEntity = new UserEntity(1L, "User", "Lastname", "user@example.com", "password",  new RoleEntity(1L, "ADMIN_ROLE"));
        Pagination pagination = new Pagination(0, 5);
        List<OrderEntity> entityList = List.of(new OrderEntity(1L, userEntity, new HashSet<>(), null, null));
        when(orderRepository.getAll(pagination)).thenReturn(entityList);

        List<OrderDTO> result = service.getAll(pagination).getResponseList();

        assertEquals(entityList.size(), result.size());
    }

    @Test
    void getByIdTest() {
        long id = 1L;
        UserEntity userEntity = new UserEntity(1L, "User", "Lastname", "user@example.com", "password",  new RoleEntity(1L, "ADMIN_ROLE"));
        Set<GiftCertificateEntity> certificates = new HashSet<>();
        LocalDateTime createDate = LocalDateTime.now();
        double price = 100.0;
        OrderEntity entity = new OrderEntity(id, userEntity, certificates, createDate, price);
        when(orderRepository.getById(id)).thenReturn(Optional.of(entity));

        OrderDTO result = service.getById(id);

        assertNotNull(result);
    }

    @Test
    void getByIdFailTest() {
        long id = -1L;
        when(orderRepository.getById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
    }

    @Test
    void createTest() {
        OrderDTO orderDTO = new OrderDTO(
                null,
                new UserDTO(1L, "User", "Lastname", "user@example.com", "password"),
                List.of(new GiftCertificateDTO(1L, "Certificate", "Description", 100D, 10,
                        LocalDateTime.MIN, LocalDateTime.MIN)),
                LocalDateTime.now(),
                100D
        );

        GiftCertificateEntity certificateEntity = new GiftCertificateEntity(1L, "Certificate", "Description",
                100D, 10, LocalDateTime.MIN, LocalDateTime.MIN, new HashSet<>());
        UserEntity userEntity = new UserEntity(1L, "User", "Lastname", "user@example.com", "password",  new RoleEntity(1L, "ADMIN_ROLE"));

        when(certificateRepository.getById(orderDTO.getCertificates().get(0).getId())).thenReturn(Optional.of(certificateEntity));
        when(userRepository.getById(orderDTO.getUser().getId())).thenReturn(Optional.of(userEntity));
        when(orderRepository.create(any(OrderEntity.class))).thenAnswer(invocation -> {
            OrderEntity createdEntity = invocation.getArgument(0);
            createdEntity.setId(1L);
            return createdEntity;
        });

        OrderDTO result = service.create(orderDTO);

        assertNotNull(result.getId());
    }

    @Test
    void createFailCertificateNotFoundTest() {
        OrderDTO orderDTO = new OrderDTO(
                null,
                new UserDTO(1L, "User", "Lastname", "user@example.com", "password"),
                List.of(new GiftCertificateDTO(1L, "Certificate", "Description", 100D, 10, null, null)),
                LocalDateTime.now(),
                100D
        );

        when(certificateRepository.getById(orderDTO.getCertificates().get(0).getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.create(orderDTO));
    }

    @Test
    void createFailUserNotFoundTest() {
        OrderDTO orderDTO = new OrderDTO(
                null,
                new UserDTO(1L, "User", "Lastname", "user@example.com", "password"),
                List.of(new GiftCertificateDTO(1L, "Certificate", "Description", 100D, 10, LocalDateTime.MIN, LocalDateTime.MIN)),
                LocalDateTime.now(),
                100D
        );

        GiftCertificateEntity certificateEntity = new GiftCertificateEntity(1L, "Certificate", "Description", 100D, 10, LocalDateTime.MIN, LocalDateTime.MIN, new HashSet<>());

        when(certificateRepository.getById(orderDTO.getCertificates().get(0).getId())).thenReturn(Optional.of(certificateEntity));
        when(userRepository.getById(orderDTO.getUser().getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.create(orderDTO));
    }
}
