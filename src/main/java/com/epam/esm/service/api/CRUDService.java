package com.epam.esm.service.api;

import com.epam.esm.model.filter.Pagination;

/**
 * This interface defines basic CRUD (Create, Read, Update, Delete) operations for an entity T.
 * It extends the CRDService interface which provides operations to get all entities and get an entity by its id.
 *
 * @param <T> The type of entity to perform CRUD operations on.
 * @see CRDService
 */
public interface CRUDService<T, F extends Pagination> extends CRDService<T, F> {

    /**
     * Updates an existing entity with the provided id with the provided entity instance.
     *
     * @param id The id of the entity to update.
     * @param t  The entity instance with updated values.
     * @return The updated entity instance.
     */
    T update(long id, T t);
}
