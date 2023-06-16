package com.epam.esm.repository.api;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.UserEntity;

import java.util.Optional;

/**
 * The UserRepository interface provides methods for CRUD operations on user entities.
 * It extends the CRUDRepository interface for basic CRUD operations.
 *
 * @see CRUDRepository
 */
public interface UserRepository extends CRUDRepository<UserEntity, Pagination> {

    /**
     * Retrieves user by its email
     *
     * @param email User email
     * @return User entity
     */
    Optional<UserEntity> getByEmail(String email);
}
