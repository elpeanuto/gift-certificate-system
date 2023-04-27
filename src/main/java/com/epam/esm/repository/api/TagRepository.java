package com.epam.esm.repository.api;

import com.epam.esm.model.dto.TagDTO;

import java.util.List;

/**
 * The TagRepository interface provides methods for managing Tag entities in the repository, including creating, retrieving, and deleting Tag entities.
 * This interface extends the CRDRepository interface, which provides basic CRD operations for working with entities.
 *
 * @see CRDRepository
 */
public interface TagRepository<T extends TagDTO> extends CRDRepository<T> {

    /**
     * Retrieves a list of Tag entities with the specified IDs from the repository.
     *
     * @param idList A list of IDs of the Tag entities to retrieve.
     * @return A list of Tag entities with the specified IDs.
     */
    List<T> getByIdList(List<Long> idList);

    /**
     * Retrieves a Tag entity with the specified name from the repository.
     *
     * @param name The name of the Tag entity to retrieve.
     * @return The Tag entity with the specified name, or null if no entity was found.
     */
    T getByName(String name);
}
