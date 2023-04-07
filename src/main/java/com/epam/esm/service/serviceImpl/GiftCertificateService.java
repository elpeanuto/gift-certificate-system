package com.epam.esm.service.serviceImpl;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.modelImpl.GiftCertificate;
import com.epam.esm.repository.CRUDRepository;
import com.epam.esm.repository.repositoryImpl.GiftCertificateRepository;
import com.epam.esm.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            throw new ResourceNotFoundException(id);


        return giftCertificate;
    }

    public GiftCertificate create(GiftCertificate giftCertificate) {
        GiftCertificate result;

        try {
            result = giftCertificateRepository.create(giftCertificate);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(),
                    "create(GiftCertificate giftCertificate)", e));
        }

        return result;
    }

    public GiftCertificate update(int id, GiftCertificate updatedGiftCertificate) {
        GiftCertificate result;

        try {
            GiftCertificate certificateToUpdate = giftCertificateRepository.getById(id);

            if (certificateToUpdate == null){
                throw new ResourceNotFoundException(id);
            }

            if (updatedGiftCertificate.getName() != null)
                certificateToUpdate.setName(updatedGiftCertificate.getName());
            if (updatedGiftCertificate.getDescription() != null)
                certificateToUpdate.setDescription(updatedGiftCertificate.getDescription());
            if (updatedGiftCertificate.getPrice() != 0.0)
                certificateToUpdate.setPrice(updatedGiftCertificate.getPrice());
            if (updatedGiftCertificate.getDuration() != 0)
                certificateToUpdate.setDuration(updatedGiftCertificate.getDuration());

            certificateToUpdate.setLastUpdateDate(LocalDateTime.now());

            result = giftCertificateRepository.update(id, certificateToUpdate);
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
