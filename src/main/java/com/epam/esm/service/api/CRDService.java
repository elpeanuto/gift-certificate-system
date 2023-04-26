package com.epam.esm.service.api;

import com.epam.esm.model.dto.DTO;

import java.util.List;

/**
 * This interface represents a CRD service for entities of type T.
 *
 * @param <T> the type of entity that this service operates on.
 */
public interface CRDService<T extends DTO> {

    /**
     * Retrieves all entities of type T from the repository.
     *
     * @return a list containing all entities of type T.
     */
    List<T> getAll();

    /**
     * Retrieves an entity of type T with the specified ID from the repository.
     *
     * @param id the ID of the entity to retrieve.
     * @return the entity with the specified ID, or null if no entity with that ID exists.
     */
    T getById(long id);

    /**
     * Creates a new entity of type T in the repository.
     *
     * @param t the entity to create.
     * @return the created entity.
     */
    T create(T t);

    /**
     * Deletes an entity of type T with the specified ID from the repository.
     *
     * @param id the ID of the entity to delete.
     * @return the deleted entity, or null if no entity with that ID exists.
     */
    T delete(long id);
}
