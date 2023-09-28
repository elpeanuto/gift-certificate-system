package com.epam.esm.service.services.impl;

import com.epam.esm.exception.exceptions.EntityAlreadyExistsException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.converter.TagConverter;
import com.epam.esm.model.dto.PaginatedResponse;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.service.services.api.CRDService;
import com.epam.esm.service.services.api.TagService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TagServiceImpl(TagRepository tagRepo) {
        this.tagRepo = tagRepo;
    }

    @Override
    @Transactional
    public PaginatedResponse<TagDTO> getAll(Pagination pagination) {
        List<TagDTO> list = tagRepo.getAll(pagination).stream()
                .map(TagConverter::toDto)
                .toList();

        return new PaginatedResponse<>(list, null);
    }

    @Override
    @Transactional
    public TagDTO getById(long id) {
        TagEntity entity = tagRepo.getById(id)
                .orElseThrow(() -> {
                    logger.error("Tag with ID {} not found.", id);
                    return new ResourceNotFoundException(id);
                });
        return toDto(entity);
    }

    @Override
    @Transactional
    public TagDTO create(TagDTO tagDTO) {
        if (tagRepo.getByName(tagDTO.getName()) != null) {
            logger.error("Tag with name {} already exists.", tagDTO.getName());
            throw new EntityAlreadyExistsException();
        }

        return toDto(tagRepo.create(toEntity(tagDTO)));
    }

    @Override
    @Transactional
    public TagDTO delete(long id) {
        TagEntity entity = tagRepo.delete(id);

        if (entity == null) {
            logger.error("Tag with ID {} not found.", id);
            throw new ResourceNotFoundException(id);
        }
        return toDto(entity);
    }

    @Override
    public TagDTO getWidelyUsedTag() {
        TagEntity widelyUsedTag = tagRepo.getWidelyUsedTag()
                .orElseThrow(() -> {
                    logger.error("Can't find widely used tag.");
                    throw new ResourceNotFoundException("Can't find widely used tag");
                });
        return toDto(widelyUsedTag);
    }
}
