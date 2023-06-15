package com.epam.esm.repository.api;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.RoleEntity;

import java.util.Optional;

public interface RoleRepository extends CRDRepository<RoleEntity, Pagination> {

    Optional<RoleEntity> getByName(String name);
}
