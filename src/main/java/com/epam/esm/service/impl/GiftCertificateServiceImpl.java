package com.epam.esm.service.impl;

import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.repository.api.GiftCertificateRepository;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.service.api.GiftCertificateService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<GiftCertificateDTO> getAll() {
        return null;
    }

    @Override
    public GiftCertificateDTO getById(long id) {
        return null;
    }

    @Override
    public GiftCertificateDTO create(GiftCertificateDTO giftCertificateDTO) {
        return null;
    }

    @Override
    public GiftCertificateDTO delete(long id) {
        return null;
    }

    @Override
    public GiftCertificateDTO update(long id, GiftCertificateDTO giftCertificateDTO) {
        return null;
    }

    @Override
    public List<GiftCertificateDTO> getByParams(String name, String part, String sort) {
        return null;
    }
}
