package com.epam.esm.service.services.api;

import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.filter.Pagination;

/**
 * The TagService interface provides methods for CRUD operations on tag DTOs.
 * It extends the CRDService interface for basic CRUD operations.
 */
public interface TagService extends CRDService<TagDTO, Pagination> {

    /**
     * Retrieves the widely used tag.
     *
     * @return The widely used tag DTO.
     */
    TagDTO getWidelyUsedTag();
}
