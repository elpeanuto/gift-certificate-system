package com.epam.esm.repository;

import com.epam.esm.model.dto.filter.GiftCertificateFilter;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.repository.api.GiftCertificateRepository;
import com.epam.esm.repository.configuration.Config;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = Config.class)
@Transactional
class GiftCertificateRepositoryImplTest {

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;
    private List<GiftCertificateEntity> certificateList;

    @BeforeEach
    void setUp() {
        certificateList = new ArrayList<>();

        for (long i = 1; i < 6; i++) {
            GiftCertificateEntity certificate = new GiftCertificateEntity();
            certificate.setId(i);
            certificate.setName("certificate" + i);
            certificate.setDescription("description" + i);
            certificate.setPrice(10D);
            certificate.setDuration(10);
            certificate.setCreateDate(LocalDateTime.parse("2022-01-01T00:00:00"));
            certificate.setLastUpdateDate(LocalDateTime.parse("2022-01-01T00:00:00"));
            certificateList.add(certificate);
        }
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_certificates.sql"})
    void testGetAll() {
        List<GiftCertificateEntity> all = giftCertificateRepository.getAll(new Pagination(0, 10));
        Assertions.assertEquals(certificateList, all);
    }

    @Test
    @Sql({"/sql/clear_tables.sql"})
    void testGetAllEmpty() {
        List<GiftCertificateEntity> all = giftCertificateRepository.getAll(new Pagination(0, 10));
        Assertions.assertTrue(all.isEmpty());
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_certificates.sql"})
    void testGetById() {
        Optional<GiftCertificateEntity> certificate = giftCertificateRepository.getById(1L);
        Assertions.assertTrue(certificate.isPresent());
        Assertions.assertEquals(certificateList.get(0), certificate.get());
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_certificates.sql"})
    void testGetByIdNonExistent() {
        Optional<GiftCertificateEntity> certificate = giftCertificateRepository.getById(10L);
        Assertions.assertTrue(certificate.isEmpty());
    }

    @Test
    @Sql({"/sql/clear_tables.sql"})
    void testGetByIdEmpty() {
        Optional<GiftCertificateEntity> certificate = giftCertificateRepository.getById(1L);
        Assertions.assertTrue(certificate.isEmpty());
    }

    @Test
    @Sql({"/sql/clear_tables.sql"})
    void testCreate() {
        GiftCertificateEntity certificate = new GiftCertificateEntity();
        certificate.setName("New Certificate");
        certificate.setDescription("New Description");
        certificate.setPrice(19.99);
        certificate.setDuration(60);
        certificate.setCreateDate(LocalDateTime.MIN);
        certificate.setLastUpdateDate(LocalDateTime.MIN);

        GiftCertificateEntity createdCertificate = giftCertificateRepository.create(certificate);

        Optional<GiftCertificateEntity> retrievedCertificate = giftCertificateRepository.getById(createdCertificate.getId());

        Assertions.assertTrue(retrievedCertificate.isPresent());
        Assertions.assertEquals(createdCertificate, retrievedCertificate.get());
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_certificates.sql"})
    void testDelete() {
        long idToDelete = 1L;

        Optional<GiftCertificateEntity> certificateBeforeDeletion = giftCertificateRepository.getById(idToDelete);
        Assertions.assertTrue(certificateBeforeDeletion.isPresent());

        GiftCertificateEntity deletedCertificate = giftCertificateRepository.delete(idToDelete);

        Optional<GiftCertificateEntity> certificateAfterDeletion = giftCertificateRepository.getById(idToDelete);
        Assertions.assertFalse(certificateAfterDeletion.isPresent());
        Assertions.assertEquals(certificateBeforeDeletion.get(), deletedCertificate);
    }
    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_certificates.sql"})
    void testUpdate() {
        GiftCertificateEntity certificate = certificateList.get(0);
        certificate.setDescription("Updated description");
        GiftCertificateEntity updatedCertificate = giftCertificateRepository.update(certificate);
        Assertions.assertEquals("Updated description", updatedCertificate.getDescription());
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_certificates.sql"})
    void testDoSearch() {
        GiftCertificateFilter filter = new GiftCertificateFilter(new HashSet<>(), "description1", null, 0, 10);
        List<GiftCertificateEntity> result = giftCertificateRepository.doSearch(filter);

        Assertions.assertEquals(1, result.size());
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_certificates.sql"})
    void testIsCertificateOrdered() {
        boolean ordered = giftCertificateRepository.isCertificateOrdered(1L);
        Assertions.assertFalse(ordered);
    }
}