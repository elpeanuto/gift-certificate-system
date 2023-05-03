package com.epam.esm.repository.api;

import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.model.filter.GiftCertificateFilter;
import com.epam.esm.model.filter.TagFilter;

import java.util.List;

/**
 * The TagRepository interface provides methods for managing Tag entities in the repository, including creating, retrieving, and deleting Tag entities.
 * This interface extends the CRDRepository interface, which provides basic CRD operations for working with entities.
 *
 * @see CRDRepository
 */
public interface TagRepository extends CRDRepository<TagEntity, TagFilter> {

    List<TagEntity> getAll(TagFilter filter);

    /**
     * Retrieves a list of Tag entities with the specified IDs from the repository.
     *
     * @param idList A list of IDs of the Tag entities to retrieve.
     * @return A list of Tag entities with the specified IDs.
     */
    List<TagEntity> getByIdList(List<Long> idList);

    /**
     * Retrieves a Tag entity with the specified name from the repository.
     *
     * @param name The name of the Tag entity to retrieve.
     * @return The Tag entity with the specified name, or null if no entity was found.
     */
    TagEntity getByName(String name);
}
