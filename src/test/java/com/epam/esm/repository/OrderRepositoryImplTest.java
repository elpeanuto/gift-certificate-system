package com.epam.esm.repository;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.repository.api.OrderRepository;
import com.epam.esm.repository.configuration.Config;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = Config.class)
@Transactional
class OrderRepositoryImplTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_users.sql", "/sql/create_certificates.sql", "/sql/create_orders.sql"})
    void testGetAll() {
        Pagination pagination = new Pagination(0, 10);
        List<OrderEntity> result = orderRepository.getAll(pagination);

        Assertions.assertEquals(2, result.size());

        OrderEntity order1 = result.get(0);
        Assertions.assertEquals(1L, order1.getId());
        Assertions.assertEquals(1L, order1.getUser().getId());
        Assertions.assertEquals(10, order1.getPrice());

        OrderEntity order2 = result.get(1);
        Assertions.assertEquals(2L, order2.getId());
        Assertions.assertEquals(2L, order2.getUser().getId());
        Assertions.assertEquals(30, order2.getPrice());
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_users.sql", "/sql/create_certificates.sql", "/sql/create_orders.sql"})
    void testGetById() {
        long orderId = 1L;
        Optional<OrderEntity> result = orderRepository.getById(orderId);

        Assertions.assertTrue(result.isPresent());

        OrderEntity order = result.get();
        Assertions.assertEquals(orderId, order.getId());
        Assertions.assertEquals(1L, order.getUser().getId());
        Assertions.assertEquals(10, order.getPrice());
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_users.sql"})
    void testCreate() {
        UserEntity user = new UserEntity(1L, "name1", "surname1", "email1@example.com", "password1");

        OrderEntity newOrder = new OrderEntity();
        newOrder.setUser(user);
        newOrder.setPrice(50D);

        OrderEntity createdOrder = orderRepository.create(newOrder);

        Optional<OrderEntity> retrievedOrder = orderRepository.getById(createdOrder.getId());

        Assertions.assertTrue(retrievedOrder.isPresent());
        Assertions.assertEquals(createdOrder, retrievedOrder.get());
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_users.sql", "/sql/create_certificates.sql", "/sql/create_orders.sql"})
    void testGetByUserId() {
        long userId = 1L;
        Pagination pagination = new Pagination(0, 10);
        List<OrderEntity> result = orderRepository.getByUserId(userId, pagination);

        Assertions.assertEquals(1, result.size());

        OrderEntity order = result.get(0);
        Assertions.assertEquals(1L, order.getId());
        Assertions.assertEquals(userId, order.getUser().getId());
        Assertions.assertEquals(10, order.getPrice());
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_users.sql", "/sql/create_certificates.sql", "/sql/create_orders.sql"})
    void testGetByUserOrderId() {
        long userId = 1L;
        long orderId = 1L;

        OrderEntity result = orderRepository.getByUserOrderId(userId, orderId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(orderId, result.getId());
        Assertions.assertEquals(userId, result.getUser().getId());
        Assertions.assertEquals(10, result.getPrice());
    }

}
