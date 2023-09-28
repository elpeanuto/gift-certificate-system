package com.epam.esm.repository.api;

import com.epam.esm.model.dto.filter.GiftCertificateFilter;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.GiftCertificateEntity;

import java.util.List;

/**
 * The GiftCertificateRepository interface provides methods for CRUD operations and searching on gift certificate entities.
 * It extends the CRUDRepository interface for basic CRUD operations and the Search interface for searching operations.
 *
 * @see CRUDRepository
 */
public interface GiftCertificateRepository extends CRUDRepository<GiftCertificateEntity, Pagination> {

    /**
     * Searches for gift certificates based on the provided filter.
     *
     * @param filter the filter criteria to apply to the search
     * @return a list of gift certificates that match the filter criteria
     */
    List<GiftCertificateEntity> doSearch(GiftCertificateFilter filter);

    /**
     * Retrieves the total count of gift certificates in the repository.
     *
     * @return the total count of gift certificates
     */
    long getTotalCount();

    /**
     * Retrieves the count of gift certificates that match the provided filter.
     *
     * @param filter the filter criteria to apply to the count
     * @return the count of gift certificates that match the filter criteria
     */
    long getFilterCount(GiftCertificateFilter filter);

    /**
     * Checks if a gift certificate with the specified ID is ordered.
     *
     * @param id the ID of the gift certificate
     * @return true if the gift certificate is ordered, false otherwise
     */
    boolean isCertificateOrdered(long id);
}
