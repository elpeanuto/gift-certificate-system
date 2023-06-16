package com.epam.esm.repository;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.repository.api.CRUDRepository;
import com.epam.esm.repository.configuration.Config;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = Config.class)
@Transactional
class UserRepositoryImplTest {

    private final CRUDRepository<UserEntity, Pagination> userRepository;
    private static List<UserEntity> userList;

    @Autowired
    public UserRepositoryImplTest(CRUDRepository<UserEntity, Pagination> userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeAll
    static void setUp() {
        userList = new ArrayList<>();

        for (long i = 1; i < 6; i++) {
            userList.add(new UserEntity(i, "name" + i, "surname" + i, "email" + i + "@example.com", "password" + i, null));
        }
    }

    @Test
    @Sql({"/sql/clear_tables.sql"})
    void testCreate() {
        UserEntity newUser = new UserEntity(null, "John", "Doe", "john@example.com", "password", null);
        UserEntity createdUser = userRepository.create(newUser);

        Optional<UserEntity> retrievedUser = userRepository.getById(createdUser.getId());

        Assertions.assertTrue(retrievedUser.isPresent());
        Assertions.assertEquals(createdUser, retrievedUser.get());
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_users.sql"})
    void testGetAll() {
        List<UserEntity> all = userRepository.getAll(new Pagination(0, 10));
        Assertions.assertEquals(userList, all);
    }

    @Test
    @Sql({"/sql/clear_tables.sql"})
    void testGetAllEmpty() {
        List<UserEntity> all = userRepository.getAll(new Pagination(0, 10));
        Assertions.assertTrue(all.isEmpty());
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_users.sql"})
    void testGetById() {
        Optional<UserEntity> user = userRepository.getById(1L);
        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(new UserEntity(1L, "name1", "surname1", "email1@example.com", "password1", null), user.get());
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_users.sql"})
    void testGetByIdNonExistent() {
        Optional<UserEntity> user = userRepository.getById(10L);
        Assertions.assertTrue(user.isEmpty());
    }

    @Test
    @Sql({"/sql/clear_tables.sql"})
    void testGetByIdEmpty() {
        Optional<UserEntity> user = userRepository.getById(1L);
        Assertions.assertTrue(user.isEmpty());
    }
}