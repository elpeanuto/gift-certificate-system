package com.epam.esm.repository.api;

import com.epam.esm.model.Entity;

import java.util.List;

/**
 * The CRDRepository interface provides CRD operations for a specific entity type T.
 *
 * @param <T> The entity type that this repository operates on.
 */
public interface CRDRepository<T extends Entity> {

    /**
     * Retrieves a list of all entities of type T from the repository.
     *
     * @return A list of all entities of type T.
     */
    List<T> getAll();

    /**
     * Retrieves an entity of type T with the specified ID from the repository.
     *
     * @param id The ID of the entity to retrieve.
     * @return The entity of type T with the specified ID, or null if no entity was found.
     */
    T getById(long id);

    /**
     * Creates a new entity of type T in the repository.
     *
     * @param t The entity to create.
     * @return The newly created entity of type T.
     */
    T create(T t);

    /**
     * Deletes an entity of type T with the specified ID from the repository.
     *
     * @param id The ID of the entity to delete.
     * @return The number of entities deleted (should always be 1 in this case).
     */
    int delete(long id);
}
