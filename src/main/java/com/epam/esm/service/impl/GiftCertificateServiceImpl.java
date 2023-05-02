package com.epam.esm.service.impl;

import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.converter.GiftCertificateConverter;
import com.epam.esm.model.converter.TagConverter;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.repository.api.GiftCertificateRepository;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.service.api.GiftCertificateService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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

    private GiftCertificateRepository certificateRepo;
    private TagRepository tagRepo;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepo, TagRepository tagRepo) {
        this.certificateRepo = certificateRepo;
        this.tagRepo = tagRepo;
    }

    @Override
    @Transactional
    public List<GiftCertificateDTO> getAll() {
        return certificateRepo.getAll().stream()
                .map(GiftCertificateConverter::toDto)
                .toList();
    }

    @Override
    @Transactional
    public GiftCertificateDTO getById(long id) {
        return toDto(certificateRepo.getById(id));
    }

    @Override
    @Transactional
    public GiftCertificateDTO create(GiftCertificateDTO giftCertificateDTO) {
        LocalDateTime now = LocalDateTime.now();

        giftCertificateDTO.setCreateDate(now);
        giftCertificateDTO.setLastUpdateDate(now);

        Set<TagEntity> tagEntities = new HashSet<>();

        for (TagDTO tagDTO : giftCertificateDTO.getTags()) {
            TagEntity tagEntity = tagRepo.getByName(tagDTO.getName());

            if (tagEntity == null) {
                tagEntity = tagRepo.create(TagConverter.toEntity(tagDTO));
            }

            tagEntities.add(tagEntity);
        }

        GiftCertificateEntity certificate = toEntity(giftCertificateDTO);
        certificate.setTags(tagEntities);

        return toDto(certificateRepo.create(certificate));
    }

    @Override
    @Transactional
    public GiftCertificateDTO delete(long id) {
        GiftCertificateEntity entity = certificateRepo.delete(id);

        if(entity == null)
            throw new ResourceNotFoundException(id);

        return toDto(entity);
    }

    @Override
    @Transactional
    public GiftCertificateDTO update(long id, GiftCertificateDTO giftCertificateDTO) {
        GiftCertificateEntity entity = certificateRepo.getById(id);

        giftCertificateDTO.setId(entity.getId());
        giftCertificateDTO.setCreateDate(entity.getCreateDate());
        giftCertificateDTO.setLastUpdateDate(LocalDateTime.now());

        if (giftCertificateDTO.getName() == null) {
            giftCertificateDTO.setName(entity.getName());
        }
        if (giftCertificateDTO.getDuration() == null) {
            giftCertificateDTO.setDuration(entity.getDuration());
        }
        if (giftCertificateDTO.getDescription() == null) {
            giftCertificateDTO.setDescription(entity.getDescription());
        }
        if (giftCertificateDTO.getPrice() == null) {
            giftCertificateDTO.setPrice(entity.getPrice());
        }
        if(giftCertificateDTO.getTags() == null) {
            giftCertificateDTO.setTags(entity.getTags().stream()
                    .map(TagConverter::toDto)
                    .collect(Collectors.toSet()));
        } else {
            Set<TagEntity> tagEntities = new HashSet<>();

            for (TagDTO tagDTO : giftCertificateDTO.getTags()) {
                TagEntity tagEntity = tagRepo.getByName(tagDTO.getName());

                if (tagEntity == null) {
                    tagEntity = tagRepo.create(TagConverter.toEntity(tagDTO));
                }

                tagEntities.add(tagEntity);
            }

            giftCertificateDTO.setTags(tagEntities.stream().map(TagConverter::toDto).collect(Collectors.toSet()));
        }

        return toDto(certificateRepo.update(id, toEntity(giftCertificateDTO)));
    }

    @Override
    @Transactional
    public List<GiftCertificateDTO> getByParams(String name, String part, String sort) {
        return null;
    }
}
