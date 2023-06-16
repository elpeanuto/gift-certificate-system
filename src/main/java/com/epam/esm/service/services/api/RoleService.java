package com.epam.esm.service.services.api;

import com.epam.esm.model.dto.RoleDTO;
import com.epam.esm.model.dto.filter.Pagination;

public interface RoleService extends CRDService<RoleDTO, Pagination> {

    RoleDTO getByName(String name);
}
