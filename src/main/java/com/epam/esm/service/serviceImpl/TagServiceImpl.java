package com.epam.esm.service.serviceImpl;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.modelImpl.Tag;
import com.epam.esm.repository.CRDRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.repositoryImpl.TagRepositoryImpl;
import com.epam.esm.service.CRDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements CRDService<Tag> {

    private final CRDRepository<Tag> tagRepository;

    @Autowired
    public TagServiceImpl(TagRepositoryImpl tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAll() {
        try {
            return tagRepository.getAll();
        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "getAll()", e));
        }
    }

    public Tag getById(int id) {
        Tag tag;

        try {
            tag = tagRepository.getById(id);
        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "getById(int id)", e));
        }

        if (tag == null)
            throw new ResourceNotFoundException(id);

        return tag;
    }

    public Tag create(Tag tag) {
        Tag result;

        try {
            result = tagRepository.create(tag);
        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "create(Tag tag)", e));
        }

        return result;
    }

    public int delete(int id) {
        int result;

        try {
            result = tagRepository.delete(id);
        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "delete(int id)", e));
        }

        if (result < 1) {
            throw new ResourceNotFoundException(id);
        }

        return result;
    }
}
