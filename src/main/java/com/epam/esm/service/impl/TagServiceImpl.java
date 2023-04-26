package com.epam.esm.service.impl;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.dto.Tag;
import com.epam.esm.repository.api.CRDRepository;
import com.epam.esm.service.api.CRDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the CRDService interface for managing Tag objects.
 * Provides methods for retrieving, creating, and deleting tags.
 *
 * @see CRDService
 */
@Service
public class TagServiceImpl implements CRDService<Tag> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CRDRepository<Tag> tagRepo;

    /**
     * Constructor for the TagServiceImpl class.
     *
     * @param tagRepository The repository used to access the underlying data store for Tag objects.
     */
    @Autowired
    public TagServiceImpl(CRDRepository<Tag> tagRepository) {
        this.tagRepo = tagRepository;
    }

    /**
     * Retrieves all Tag objects from the data store.
     *
     * @return A list of all Tag objects in the data store.
     * @throws RepositoryException If there is an error retrieving the Tag objects.
     */
    @Override
    public List<Tag> getAll() {
        try {
            return tagRepo.getAll();
        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "getAll()", e));
        }
    }

    /**
     * Retrieves a Tag object from the data store by its ID.
     *
     * @param id The ID of the Tag object to retrieve.
     * @return The Tag object with the specified ID.
     * @throws RepositoryException       If there is an error retrieving the Tag object.
     * @throws ResourceNotFoundException If a Tag object with the specified ID does not exist in the data store.
     */
    @Override
    public Tag getById(long id) {
        Tag tag;

        try {
            tag = tagRepo.getById(id);
        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "getById(int id)", e));
        }

        if (tag == null)
            throw new ResourceNotFoundException(id);

        return tag;
    }

    /**
     * Creates a new Tag object in the data store.
     *
     * @param tag The Tag object to create in the data store.
     * @return The newly created Tag object.
     * @throws RepositoryException If there is an error creating the Tag object.
     * @throws RepositoryException If a Tag object with the same name already exists in the data store.
     */
    @Override
    public Tag create(Tag tag) {
        Tag result;

        try {
            result = tagRepo.create(tag);
        } catch (DuplicateKeyException e) {
            logger.error(e.getMessage());
            throw new RepositoryException("Tag with this name already exists");
        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "create(Tag tag)", e));
        }

        return result;
    }

    /**
     * Deletes a Tag object from the data store by its ID.
     *
     * @param id The ID of the Tag object to delete.
     * @return The deleted Tag object.
     * @throws RepositoryException       If there is an error deleting the Tag object.
     * @throws ResourceNotFoundException If a Tag object with the specified ID does not exist in the data store.
     */
    @Override
    public Tag delete(long id) {
        Tag result;

        try {
            result = getById(id);

            if (tagRepo.delete(id) < 1) {
                throw new RepositoryException("Failed to delete tag");
            }

        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "delete(int id)", e));
        }

        return result;
    }
}
