package com.epam.esm.repository.api;

import com.epam.esm.model.Entity;

/**
 * The CRUDRepository interface extends the CRDRepository interface, which provides CRUD (Create, Read, Update, Delete) operations for a specific entity type T.
 * For more information on the CRDRepository interface, see its Javadoc documentation.
 *
 * @param <T> The entity type that this repository operates on.
 * @see CRDRepository
 */
public interface CRUDRepository<T extends Entity> extends CRDRepository<T> {

    /**
     * Updates an existing entity of type T with the specified ID in the repository.
     *
     * @param id The ID of the entity to update.
     * @param t  The updated entity to replace the old entity with.
     * @return The updated entity of type T.
     */
    T update(long id, T t);
}
