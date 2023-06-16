package com.epam.esm.repository.api;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.RoleEntity;

import java.util.Optional;

/**
 * The RoleRepository interface provides methods for CRD operations on role entities.
 * It extends the CRDRepository interface for basic CRD operations.
 *
 * @see CRDRepository
 */
public interface RoleRepository extends CRDRepository<RoleEntity, Pagination> {

    /**
     * Retrieves role by its name
     *
     * @param name Role name
     * @return The role entity
     */
    Optional<RoleEntity> getByName(String name);
}
