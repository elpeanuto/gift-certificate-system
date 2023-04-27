package com.epam.esm.service.impl;

import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.converter.TagConverter;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.service.api.CRDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.model.converter.TagConverter.toDto;
import static com.epam.esm.model.converter.TagConverter.toEntity;

/**
 * Implementation of the CRDService interface for managing Tag objects.
 * Provides methods for retrieving, creating, and deleting tags.
 *
 * @see CRDService
 */
@Service
public class TagServiceImpl implements CRDService<TagDTO> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TagRepository tagRepo;

    @Autowired
    public TagServiceImpl(TagRepository tagRepo) {
        this.tagRepo = tagRepo;
    }

    @Override
    public List<TagDTO> getAll() {
        return tagRepo.findAll().stream()
                .map(TagConverter::toDto)
                .toList();
    }

    @Override
    public TagDTO getById(long id) {
        Optional<TagEntity> result = tagRepo.findById(id);

        if(result.isEmpty()){
            throw new ResourceNotFoundException();
        }

        return toDto(result.get());
    }

    @Override
    public TagDTO create(TagDTO dto) {
        return toDto(tagRepo.save(toEntity(dto)));
    }

    @Transactional
    @Override
    public TagDTO delete(long id) {
        Optional<TagEntity> result = tagRepo.findById(id);

        if(result.isEmpty()){
            throw new ResourceNotFoundException();
        }

        tagRepo.deleteById(id);

        return toDto(result.get());
    }
}
