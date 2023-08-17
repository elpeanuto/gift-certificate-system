package com.epam.esm.repository.api;

import com.epam.esm.model.dto.filter.GiftCertificateFilter;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.service.filter.Search;

import java.util.List;

/**
 * The GiftCertificateRepository interface extends the CRUDRepository interface, which extends the CRDRepository interface.
 * The CRUDRepository interface provides CRUD (Create, Read, Update, Delete) operations for a specific entity type T, and
 * the CRDRepository interface provides Read and Delete operations for the same entity type.This interface provides additional
 * methods to retrieve a list of GiftCertificate entities by ID list or by part of their name or description.
 *
 * @param <T> The entity type that this repository operates on, which must extend the GiftCertificate class.
 * @see CRUDRepository
 * @see CRDRepository
 */
public interface GiftCertificateRepository extends CRUDRepository<GiftCertificateEntity, Pagination> {

    List<GiftCertificateEntity> doSearch(GiftCertificateFilter filter);

    long getTotalCount();

    long getFilterCount(GiftCertificateFilter filter);

    boolean isCertificateOrdered(long id);
}
