package com.epam.esm.repository.api;

import com.epam.esm.model.dto.filter.GiftCertificateFilter;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.service.filter.Search;

import java.util.List;

/**
 * The GiftCertificateRepository interface provides methods for CRUD operations and searching on gift certificate entities.
 * It extends the CRUDRepository interface for basic CRUD operations and the Search interface for searching operations.
 *
 * @see CRUDRepository
 */
public interface GiftCertificateRepository extends CRUDRepository<GiftCertificateEntity, Pagination> {

    List<GiftCertificateEntity> doSearch(GiftCertificateFilter filter);

    long getTotalCount();

    long getFilterCount(GiftCertificateFilter filter);

    /**
     * Checks if a gift certificate with the specified ID is ordered.
     *
     * @param id the ID of the gift certificate
     * @return true if the gift certificate is ordered, false otherwise
     */
    boolean isCertificateOrdered(long id);
}
