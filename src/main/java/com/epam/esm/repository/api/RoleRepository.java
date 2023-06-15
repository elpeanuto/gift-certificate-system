package com.epam.esm.repository.api;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.RoleEntity;

public interface RoleRepository extends CRDRepository<RoleEntity, Pagination>{

    RoleEntity getByName(String name);
}
