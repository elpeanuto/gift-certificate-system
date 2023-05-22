package com.epam.esm.service.services.impl;

import com.epam.esm.exception.exceptions.EntityAlreadyExistsException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.converter.TagConverter;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.service.services.api.CRDService;
import com.epam.esm.service.services.api.TagService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.model.converter.TagConverter.toDto;
import static com.epam.esm.model.converter.TagConverter.toEntity;

/**
 * Implementation of the CRDService interface for managing Tag objects.
 * Provides methods for retrieving, creating, and deleting tags.
 *
 * @see CRDService
 */
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepo;

    @Autowired
    public TagServiceImpl(TagRepository tagRepo) {
        this.tagRepo = tagRepo;
    }

    @Override
    @Transactional
    public List<TagDTO> getAll(Pagination pagination) {
        return tagRepo.getAll(pagination).stream()
                .map(TagConverter::toDto)
                .toList();
    }

    @Override
    @Transactional
    public TagDTO getById(long id) {
        TagEntity entity = tagRepo.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return toDto(entity);
    }

    @Override
    @Transactional
    public TagDTO create(TagDTO tagDTO) {
        if (tagRepo.getByName(tagDTO.getName()) != null)
            throw new EntityAlreadyExistsException();

        return toDto(tagRepo.create(toEntity(tagDTO)));
    }

    @Override
    @Transactional
    public TagDTO delete(long id) {
        TagEntity entity = tagRepo.delete(id);

        if (entity == null)
            throw new ResourceNotFoundException(id);

        return toDto(entity);
    }

    @Override
    public TagDTO getWidelyUsedTag() {
        TagEntity widelyUsedTag = tagRepo.getWidelyUsedTag()
                .orElseThrow(() -> new ResourceNotFoundException("Cant find widely used tag"));

        return toDto(widelyUsedTag);
    }
}
