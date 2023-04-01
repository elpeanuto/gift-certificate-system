package com.epam.esm.service;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;

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
