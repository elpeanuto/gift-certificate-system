package com.epam.esm.service.serviceImpl;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.modelImpl.GiftCertificate;
import com.epam.esm.repository.CRUDRepository;
import com.epam.esm.repository.repositoryImpl.GiftCertificateRepository;
import com.epam.esm.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GiftCertificateService implements CRUDService<GiftCertificate> {

    private final CRUDRepository<GiftCertificate> giftCertificateRepository;

    @Autowired
    public GiftCertificateService(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    public List<GiftCertificate> getAll() {
        try {
            return giftCertificateRepository.getAll();
        } catch (DataAccessException e) {
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

        if (giftCertificate == null)
            throw new ResourceNotFoundException(Integer.toString(id));


        return giftCertificate;
    }

    public int create(GiftCertificate giftCertificate) {
        int result;

        try {
            result = giftCertificateRepository.create(giftCertificate);
        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "create(Tag tag)", e));
        }

        if (result < 1) {
            throw new RepositoryException("Failed to create giftCertificate.");
        }

        return result;
    }

    public int update(int id, GiftCertificate updatedGiftCertificate) {

        int result;

        try {
            result = giftCertificateRepository.update(id, updatedGiftCertificate);
        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "update(int id)", e));
        }

        if (result < 1) {
            throw new RepositoryException("Failed to update giftCertificate.");
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
            throw new RepositoryException("Failed to delete giftCertificate.");
        }

        return result;
    }
}
