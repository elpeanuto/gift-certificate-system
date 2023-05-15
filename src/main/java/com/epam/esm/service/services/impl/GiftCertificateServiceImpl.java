package com.epam.esm.service.services.impl;

import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.converter.GiftCertificateConverter;
import com.epam.esm.model.converter.TagConverter;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.filter.GiftCertificateFilter;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.repository.api.GiftCertificateRepository;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.service.services.api.GiftCertificateService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository certificateRepo;
    private final TagRepository tagRepo;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepo, TagRepository tagRepo) {
        this.certificateRepo = certificateRepo;
        this.tagRepo = tagRepo;
    }

    @Override
    @Transactional
    public List<GiftCertificateDTO> getAll(Pagination pagination) {
        return certificateRepo.getAll(pagination).stream()
                .map(GiftCertificateConverter::toDto)
                .toList();
    }

    @Override
    @Transactional
    public List<GiftCertificateDTO> doSearch(GiftCertificateFilter filter) {
        return certificateRepo.doSearch(filter).stream()
                .map(GiftCertificateConverter::toDto)
                .toList();
    }

    @Override
    @Transactional
    public GiftCertificateDTO getById(long id) {
        GiftCertificateEntity entity = certificateRepo.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return toDto(entity);
    }

    @Override
    @Transactional
    public GiftCertificateDTO create(GiftCertificateDTO giftCertificateDTO) {
        LocalDateTime now = LocalDateTime.now();

        giftCertificateDTO.setCreateDate(now);
        giftCertificateDTO.setLastUpdateDate(now);

        GiftCertificateEntity certificate = toEntity(giftCertificateDTO);
        certificate.setTags(createMissingTags(giftCertificateDTO.getTags()));

        return toDto(certificateRepo.create(certificate));
    }

    @Override
    @Transactional
    public GiftCertificateDTO delete(long id) {
        if(certificateRepo.isCertificateOrdered(id))
            throw new DataIntegrityViolationException("This certificate is ordered");

        GiftCertificateEntity entity = certificateRepo.delete(id);

        if (entity == null)
            throw new ResourceNotFoundException(id);

        return toDto(entity);
    }

    @Override
    @Transactional
    public GiftCertificateDTO update(long id, GiftCertificateDTO giftCertificateDTO) {
        GiftCertificateEntity entity = certificateRepo.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        entity.setLastUpdateDate(LocalDateTime.now());

        if (giftCertificateDTO.getName() != null) {
            entity.setName(giftCertificateDTO.getName());
        }
        if (giftCertificateDTO.getDuration() != null) {
            entity.setDuration(giftCertificateDTO.getDuration());
        }
        if (giftCertificateDTO.getDescription() != null) {
            entity.setDescription(giftCertificateDTO.getDescription());
        }
        if (giftCertificateDTO.getPrice() != null) {
            entity.setPrice(giftCertificateDTO.getPrice());
        }
        if (giftCertificateDTO.getTags() != null) {
            entity.setTags(createMissingTags(giftCertificateDTO.getTags()));
        }

        return toDto(certificateRepo.update(entity));
    }

    @Override
    @Transactional
    public List<GiftCertificateDTO> getByParams(String name, String part, String sort) {
        throw new UnsupportedOperationException();
    }

    private Set<TagEntity> createMissingTags(Set<TagDTO> set) {
        return set.stream()
                .map(tag -> {
                    TagEntity entity = tagRepo.getByName(tag.getName());

                    if (entity == null)
                        entity = tagRepo.create(TagConverter.toEntity(tag));

                    return entity;
                })
                .collect(Collectors.toSet());
    }
}
