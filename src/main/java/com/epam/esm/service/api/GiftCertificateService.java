package com.epam.esm.service.api;

import com.epam.esm.model.dto.GiftCertificateDTO;

import java.util.List;

/**
 * The GiftCertificateService interface provides methods for performing CRUD (Create, Read, Update, Delete)
 * operations on GiftCertificate objects, as well as a method for retrieving GiftCertificates based on specific parameters.
 * It extends the CRUDService interface, which provides basic CRUD functionality.
 *
 * @param <T> the type of GiftCertificate being managed by this service
 * @see CRUDService
 */
public interface GiftCertificateService<T extends GiftCertificateDTO> extends CRUDService<T> {

    /**
     * Returns a list of GiftCertificates based on the specified parameters.
     *
     * @param name the name to search for (may be null or empty)
     * @param part the part of the tag name to search for (may be null or empty)
     * @param sort the sort order to use (may be null or empty)
     * @return a list of GiftCertificates matching the specified parameters
     */
    List<T> getByParams(String name, String part, String sort);
}
