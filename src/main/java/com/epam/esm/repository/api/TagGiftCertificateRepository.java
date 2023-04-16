package com.epam.esm.repository.api;

import java.util.List;

/**
 * The TagGiftCertificateRepository interface provides methods for creating and retrieving associations between Tag and GiftCertificate entities.
 */
public interface TagGiftCertificateRepository {

    /**
     * Creates an association between a Tag entity with the specified ID and a GiftCertificate entity with the specified ID in the repository.
     *
     * @param certificateId The ID of the GiftCertificate entity to associate with the Tag entity.
     * @param tagId         The ID of the Tag entity to associate with the GiftCertificate entity.
     * @return The number of rows affected by the creation operation (should always be 1 in this case).
     */
    int createTagGiftCertificate(int certificateId, int tagId);

    /**
     * Retrieves a list of Tag entity IDs that are associated with a GiftCertificate entity with the specified ID in the repository.
     *
     * @param certificateId The ID of the GiftCertificate entity to retrieve associated Tag entity IDs for.
     * @return A list of Tag entity IDs that are associated with the specified GiftCertificate entity.
     */
    List<Integer> getAllTagsIdByGiftCertificate(int certificateId);

    /**
     * Retrieves a list of GiftCertificate entity IDs that are associated with a Tag entity with the specified ID in the repository.
     *
     * @param tagId The ID of the Tag entity to retrieve associated GiftCertificate entity IDs for.
     * @return A list of GiftCertificate entity IDs that are associated with the specified Tag entity.
     */
    List<Integer> getAllCertificateIdByTag(int tagId);
}
