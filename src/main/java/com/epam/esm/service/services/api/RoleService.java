package com.epam.esm.service.services.api;

import com.epam.esm.model.dto.RoleDTO;
import com.epam.esm.model.dto.filter.Pagination;

/**
 * This interface represents extension of Role CRD service.
 */
public interface RoleService extends CRDService<RoleDTO, Pagination> {

    /**
     * Retrieves an entity of type RoleDTO with the specified ID from the repository by name.
     *
     * @param name Role name
     * @return Role obj
     */
    RoleDTO getByName(String name);
}
