package com.epam.esm.repository.api;

import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.filter.GiftCertificateFilter;

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
public interface GiftCertificateRepository extends CRUDRepository<GiftCertificateEntity, GiftCertificateFilter> {

    /**
     * Retrieves a list of GiftCertificate entities with the specified IDs from the repository.
     *
     * @param idList The list of IDs of the GiftCertificate entities to retrieve.
     * @return A list of GiftCertificate entities with the specified IDs.
     */
    List<GiftCertificateEntity> getByIdList(List<Long> idList);

    /**
     * Retrieves a list of GiftCertificate entities that contain the specified pattern in their name or description.
     *
     * @param pattern The pattern to search for in the GiftCertificate entities' name or description.
     * @return A list of GiftCertificate entities that contain the specified pattern in their name or description.
     */
    List<GiftCertificateEntity> getByPartOfNameDescription(String pattern);
}
