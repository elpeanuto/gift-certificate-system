package com.epam.esm.service.serviceImpl;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.modelImpl.GiftCertificate;
import com.epam.esm.model.modelImpl.Tag;
import com.epam.esm.repository.CRUDRepository;
import com.epam.esm.repository.repositoryImpl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.repositoryImpl.TagGiftCertificateRepository;
import com.epam.esm.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class GiftCertificateServiceImpl implements CRUDService<GiftCertificate> {

    private final CRUDRepository<GiftCertificate> giftCertificateRepository;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepositoryImpl giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    public List<GiftCertificate> getAll() {
        try {
            return giftCertificateRepository.getAll();
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "getAll()", e));
        }
    }

    public GiftCertificate getById(int id) {
        GiftCertificate giftCertificate;

        try {
            giftCertificate = giftCertificateRepository.getById(id);
        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "getById(int id)", e));
        }

        return giftCertificate;
    }

    public GiftCertificate create(GiftCertificate giftCertificate) {
        GiftCertificate result;

        try {
            giftCertificate.setTags(giftCertificate.getTags().stream()
                    .distinct()
                    .toList());

            result = giftCertificateRepository.create(giftCertificate);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(),
                    "create(GiftCertificate giftCertificate)", e));
        }

        return result;
    }

    public GiftCertificate update(int id, GiftCertificate giftCertificate) {
        GiftCertificate result;

        try {
            GiftCertificate certificateFromDB = giftCertificateRepository.getById(id);

            if (certificateFromDB == null) {
                throw new ResourceNotFoundException(id);
            }

            List<Tag> DBTags = certificateFromDB.getTags();
            List<Tag> requestTags = giftCertificate.getTags();

            List<Tag> temp = requestTags.stream()
                    .distinct()
                    .filter(tag -> !DBTags.contains(tag))
                    .toList();

            if (giftCertificate.getName() != null) {
                certificateFromDB.setName(giftCertificate.getName());
            }
            if (giftCertificate.getDescription() != null) {
                certificateFromDB.setDescription(giftCertificate.getDescription());
            }
            if (giftCertificate.getPrice() != null) {
                certificateFromDB.setPrice(giftCertificate.getPrice());
            }
            if (giftCertificate.getDuration() != null) {
                certificateFromDB.setDuration(giftCertificate.getDuration());
            }
            if (giftCertificate.getTags() != null) {
                certificateFromDB.setTags(temp);
            }

            certificateFromDB.setLastUpdateDate(LocalDateTime.now());

            result = giftCertificateRepository.update(id, certificateFromDB);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "update(int id)", e));
        }

        return result;
    }

    public int delete(int id) {
        int result;

        try {
            result = giftCertificateRepository.delete(id);
        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "delete(int id)", e));
        }

        if (result < 1) {
            throw new ResourceNotFoundException(id);
        }

        return result;
    }
}
