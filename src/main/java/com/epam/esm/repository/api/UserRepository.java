package com.epam.esm.repository.api;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends CRUDRepository<UserEntity, Pagination> {

    Optional<UserEntity> getByEmail(String email);
}
