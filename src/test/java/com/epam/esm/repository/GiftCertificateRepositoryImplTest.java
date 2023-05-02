package com.epam.esm.repository;

import com.epam.esm.Application;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.repository.api.GiftCertificateRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Sql(scripts = "/setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GiftCertificateRepositoryImplTest {

    @Autowired
    private GiftCertificateRepository<GiftCertificateDTO> repository;

    @Test
    void testGetAll() {
        List<GiftCertificateDTO> requestList = new ArrayList<>();
        requestList.add(new GiftCertificateDTO(1, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificateDTO(2, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificateDTO(3, "Test", "Test Description", 1.0, 1, null, null));

        requestList.forEach(certificate -> repository.create(certificate));

        List<GiftCertificateDTO> response = repository.getAll();

        assertEquals(response, requestList);
    }

    @Test
    void testGetById() {
        List<GiftCertificateDTO> requestList = new ArrayList<>();
        requestList.add(new GiftCertificateDTO(1, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificateDTO(2, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificateDTO(3, "Test", "Test Description", 1.0, 1, null, null));

        requestList.forEach(certificate -> repository.create(certificate));

        assertEquals(repository.getById(1), requestList.get(0));
        assertEquals(repository.getById(2), requestList.get(1));
        assertEquals(repository.getById(3), requestList.get(2));
    }

    @Test
    void testGetByIdList() {
        List<GiftCertificateDTO> requestList = new ArrayList<>();
        requestList.add(new GiftCertificateDTO(1, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificateDTO(2, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificateDTO(3, "Test", "Test Description", 1.0, 1, null, null));

        requestList.forEach(certificate -> repository.create(certificate));

        List<GiftCertificateDTO> response = repository.getByIdList(List.of(1, 3));

        assertEquals(response.get(0), requestList.get(0));
        assertEquals(response.get(1), requestList.get(2));
    }

    @Test
    void testGetByPartOfNameDescription() {
        List<GiftCertificateDTO> requestList = new ArrayList<>();
        requestList.add(new GiftCertificateDTO(1, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificateDTO(2, "Rest", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificateDTO(3, "Shopping", "Test Description", 1.0, 1, null, null));

        requestList.forEach(certificate -> repository.create(certificate));

        List<GiftCertificateDTO> response = repository.getByPartOfNameDescription("Sho");
        List<GiftCertificateDTO> response2 = repository.getByPartOfNameDescription("desc");

        assertEquals(response.get(0), requestList.get(2));
        assertEquals(response2, requestList);
    }

    @Test
    void testUpdate() {
        List<GiftCertificateDTO> requestList = new ArrayList<>();
        requestList.add(new GiftCertificateDTO(1, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificateDTO(2, "Rest", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificateDTO(3, "Shopping", "Test Description", 1.0, 1, null, null));

        requestList.forEach(certificate -> repository.create(certificate));

        GiftCertificateDTO response = repository.update(1, requestList.get(2));

        assertEquals(response, requestList.get(2));
        assertEquals(response, requestList.get(2));
    }

    @Test
    void testDelete() {
        List<GiftCertificateDTO> requestList = new ArrayList<>();
        requestList.add(new GiftCertificateDTO(1, "Test", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificateDTO(2, "Rest", "Test Description", 1.0, 1, null, null));
        requestList.add(new GiftCertificateDTO(3, "Shopping", "Test Description", 1.0, 1, null, null));

        requestList.forEach(certificate -> repository.create(certificate));

        assertEquals(1, repository.delete(1));
        assertEquals(1, repository.delete(2));
        assertEquals(1, repository.delete(3));
        assertEquals(0, repository.delete(4));

        List<GiftCertificateDTO> getAllResponse = repository.getAll();

        assertEquals(Collections.emptyList(), getAllResponse);
    }
}