package com.epam.esm.service.services.api;

import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.filter.Pagination;

public interface TagService extends CRDService<TagDTO, Pagination> {

    TagDTO getWidelyUsedTag();
}
