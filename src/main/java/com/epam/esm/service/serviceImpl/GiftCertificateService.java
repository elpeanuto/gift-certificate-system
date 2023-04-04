package com.epam.esm.service.serviceImpl;

import com.epam.esm.model.modelImpl.GiftCertificate;
import com.epam.esm.repository.CRUDRepository;
import com.epam.esm.repository.repositoryImpl.GiftCertificateRepository;
import com.epam.esm.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class GiftCertificateService implements CRUDService<GiftCertificate> {

    private final CRUDRepository<GiftCertificate> giftCertificateRepository;

    @Autowired
    public GiftCertificateService(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    public List<GiftCertificate> getAll() {
        return giftCertificateRepository.getAll();
    }

    public GiftCertificate getById(int id) {
        return giftCertificateRepository.getById(id);
    }

    public int create(GiftCertificate giftCertificate) {
        return giftCertificateRepository.create(giftCertificate);
    }

    public int update(int id, GiftCertificate updatedGiftCertificate) {
        return giftCertificateRepository.update(id, updatedGiftCertificate);
    }

    public int delete(int id) {
        return giftCertificateRepository.delete(id);
    }
}
