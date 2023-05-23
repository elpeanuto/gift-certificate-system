package com.epam.esm.service.services.api;

import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.filter.GiftCertificateFilter;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.service.filter.Search;

/**
 * The GiftCertificateService interface provides methods for CRUD operations and searching on gift certificate DTOs.
 * It extends the CRUDService interface for basic CRUD operations and the Search interface for searching operations.
 */
public interface GiftCertificateService extends CRUDService<GiftCertificateDTO, Pagination>,
        Search<GiftCertificateDTO, GiftCertificateFilter> {
}
