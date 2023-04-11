package com.epam.esm.service.impl;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.CRDRepository;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.CRDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements CRDService<Tag> {

    private final CRDRepository<Tag> tagRepo;

    @Autowired
    public TagServiceImpl(TagRepositoryImpl tagRepository) {
        this.tagRepo = tagRepository;
    }

    @Override
    public List<Tag> getAll() {
        try {
            return tagRepo.getAll();
        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "getAll()", e));
        }
    }

    @Override
    public Tag getById(int id) {
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

    @Override
    public Tag create(Tag tag) {
        Tag result;

        try {
            result = tagRepo.create(tag);
        } catch (DuplicateKeyException e) {
            throw new RepositoryException("Tag with this name already exists");
        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "create(Tag tag)", e));
        }

        return result;
    }

    @Override
    public Tag delete(int id) {
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
