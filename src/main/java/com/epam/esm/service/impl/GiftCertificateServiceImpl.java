package com.epam.esm.service.impl;

import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.converter.GiftCertificateConverter;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.repository.api.GiftCertificateRepository;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.service.api.GiftCertificateService;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.model.converter.GiftCertificateConverter.toDto;
import static com.epam.esm.model.converter.GiftCertificateConverter.toEntity;

/**
 * This class provides implementation of GiftCertificateService interface.
 * It performs operations related to GiftCertificate, using GiftCertificateRepository, TagRepository and TagGiftCertificateRepository.
 * It also implements the methods of GiftCertificateService interface.
 *
 * @see GiftCertificateService
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService<GiftCertificateDTO> {

    @PersistenceContext
    private EntityManager entityManager;
    private GiftCertificateRepository certificateRepo;
    private TagRepository tagRepo;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepo, TagRepository tagRepo) {
        this.certificateRepo = certificateRepo;
        this.tagRepo = tagRepo;
    }

    @Override
    public List<GiftCertificateDTO> getAll() {
        return certificateRepo.findAll().stream()
                .map(GiftCertificateConverter::toDto)
                .toList();
    }

    @Override
    public GiftCertificateDTO getById(long id) {
        Optional<GiftCertificateEntity> result = certificateRepo.findById(id);

        if (result.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return toDto(result.get());
    }

    @Transactional
    @Override
    public GiftCertificateDTO create(GiftCertificateDTO dto) {
        GiftCertificateEntity entity = toEntity(dto);

        LocalDateTime now = LocalDateTime.now();
        entity.setCreateDate(now);
        entity.setLastUpdateDate(now);

        Set<TagEntity> tags = entity.getTags();

        Set<TagEntity> tagsToCreate = tags.stream()
                .map(tag -> {
                    Optional<TagEntity> tagFromDb = tagRepo.findByName(tag.getName());

                    if (tagFromDb.isPresent()) {
                        tag = tagFromDb.get();
                    } else {
                        tag = tagRepo.save(tag);
                    }

                    return tag;
                }).collect(Collectors.toSet());

        entity.setTags(null);

        Long createdId = certificateRepo.save(entity).getId();

        entity.setId(createdId);
        entity.setTags(tagsToCreate);

        entityManager.merge(entity);
        return toDto(entity);
    }

    @Transactional
    @Override
    public GiftCertificateDTO delete(long id) {
        Optional<GiftCertificateEntity> result = certificateRepo.findById(id);

        if (result.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        certificateRepo.deleteById(id);

        return toDto(result.get());
    }

    @Transactional
    @Override
    public GiftCertificateDTO update(long id, GiftCertificateDTO dto) {
        GiftCertificateEntity entityFromDTO = toEntity(dto);
        GiftCertificateEntity entityFromDB = certificateRepo.findById(id).orElseThrow(ResourceNotFoundException::new);

        entityFromDTO.setId(id);

        if(entityFromDB.getName() != null)
            entityFromDTO.setName(entityFromDB.getName());
        if(entityFromDB.getDescription() != null)
            entityFromDTO.setDescription(entityFromDB.getName());
        if(entityFromDB.getPrice() != null)
            entityFromDTO.setPrice(entityFromDB.getPrice());
        if(entityFromDB.getDuration() != null)
            entityFromDTO.setDuration(entityFromDB.getDuration());

        entityFromDTO.setLastUpdateDate(LocalDateTime.now());
        entityFromDTO.setCreateDate(entityFromDB.getCreateDate());

        Set<TagEntity> tagsFromDTO = new HashSet<>();
        for (TagEntity tagEntity : entityFromDTO.getTags()) {
            Optional<TagEntity> optionalTagEntity = tagRepo.findByName(tagEntity.getName());
            if (optionalTagEntity.isPresent()) {
                tagsFromDTO.add(optionalTagEntity.get());
            } else {
                TagEntity newTagEntity = new TagEntity();
                newTagEntity.setName(tagEntity.getName());
                tagsFromDTO.add(newTagEntity);
            }
        }
        entityFromDB.setTags(tagsFromDTO);

        return toDto(entityFromDB);
    }

    @Override
    public List<GiftCertificateDTO> getByParams(String name, String part, String sort) {
        return null;
    }
}
