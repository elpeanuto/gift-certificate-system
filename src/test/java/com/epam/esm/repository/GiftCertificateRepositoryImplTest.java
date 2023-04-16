package com.epam.esm.repository;

import com.epam.esm.config.TestAppConfig;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.repository.api.GiftCertificateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppConfig.class)
@Sql(scripts = "/sql/setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
class GiftCertificateRepositoryImplTest {

    @Autowired
    private GiftCertificateRepository<GiftCertificate> repository;

    @Test
    void testGetAll() {
        List<GiftCertificate> requestList = new ArrayList<>();
        requestList.add(new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificate(2, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificate(3, "Test", "Test Description", 1.0, 1, null, null));

        requestList.forEach(certificate -> repository.create(certificate));

        List<GiftCertificate> response = repository.getAll();

        assertEquals(response, requestList);
    }

    @Test
    void testGetById() {
        List<GiftCertificate> requestList = new ArrayList<>();
        requestList.add(new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificate(2, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificate(3, "Test", "Test Description", 1.0, 1, null, null));

        requestList.forEach(certificate -> repository.create(certificate));

        assertEquals(repository.getById(1), requestList.get(0));
        assertEquals(repository.getById(2), requestList.get(1));
        assertEquals(repository.getById(3), requestList.get(2));
    }

    @Test
    void testGetByIdList() {
        List<GiftCertificate> requestList = new ArrayList<>();
        requestList.add(new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificate(2, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificate(3, "Test", "Test Description", 1.0, 1, null, null));

        requestList.forEach(certificate -> repository.create(certificate));

        List<GiftCertificate> response = repository.getByIdList(List.of(1, 3));

        assertEquals(response.get(0), requestList.get(0));
        assertEquals(response.get(1), requestList.get(2));
    }

    @Test
    void testGetByPartOfNameDescription() {
        List<GiftCertificate> requestList = new ArrayList<>();
        requestList.add(new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificate(2, "Rest", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificate(3, "Shopping", "Test Description", 1.0, 1, null, null));

        requestList.forEach(certificate -> repository.create(certificate));

        List<GiftCertificate> response = repository.getByPartOfNameDescription("Sho");
        List<GiftCertificate> response2 = repository.getByPartOfNameDescription("desc");

        assertEquals(response.get(0), requestList.get(2));
        assertEquals(response2, requestList);
    }

    @Test
    void testUpdate() {
        List<GiftCertificate> requestList = new ArrayList<>();
        requestList.add(new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificate(2, "Rest", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificate(3, "Shopping", "Test Description", 1.0, 1, null, null));

        requestList.forEach(certificate -> repository.create(certificate));

        GiftCertificate response = repository.update(1, requestList.get(2));

        assertEquals(response, requestList.get(2));
        assertEquals(response, requestList.get(2));
    }

    @Test
    void testDelete() {
        List<GiftCertificate> requestList = new ArrayList<>();
        requestList.add(new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificate(2, "Rest", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificate(3, "Shopping", "Test Description", 1.0, 1, null, null));

        requestList.forEach(certificate -> repository.create(certificate));

        assertEquals(1, repository.delete(1));
        assertEquals(1, repository.delete(2));
        assertEquals(1, repository.delete(3));
        assertEquals(0, repository.delete(4));

        List<GiftCertificate> getAllResponse = repository.getAll();

        assertEquals(Collections.emptyList(), getAllResponse);
    }
}