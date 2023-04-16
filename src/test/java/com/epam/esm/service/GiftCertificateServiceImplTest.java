package com.epam.esm.service;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.api.GiftCertificateRepository;
import com.epam.esm.repository.api.TagGiftCertificateRepository;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateRepository<GiftCertificate> certificateRepo;
    @Mock
    private TagRepository<Tag> tagRepo;
    @Mock
    private TagGiftCertificateRepository tagCertificateRepo;
    @InjectMocks
    private GiftCertificateServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllExistsCase() {
        List<GiftCertificate> giftCertificateList = new ArrayList<>();
        giftCertificateList.add(new GiftCertificate(1, "name", "description",
                100d, 10, LocalDateTime.now(), LocalDateTime.now()));
        giftCertificateList.add(new GiftCertificate(2, "name", "description",
                100d, 10, LocalDateTime.now(), LocalDateTime.now()));
        giftCertificateList.add(new GiftCertificate(3, "name", "description",
                100d, 10, LocalDateTime.now(), LocalDateTime.now()));

        when(certificateRepo.getAll()).thenReturn(giftCertificateList);
        when(tagCertificateRepo.getAllTagsIdByGiftCertificate(anyInt())).thenReturn(null);
        when(tagRepo.getByIdList(null)).thenReturn(null);

        List<GiftCertificate> response = service.getAll();

        assertEquals(giftCertificateList, response);

        verify(certificateRepo, times(1)).getAll();

        for (GiftCertificate certificate : giftCertificateList) {
            verify(tagCertificateRepo, times(1)).getAllTagsIdByGiftCertificate(certificate.getId());
        }

        verify(tagRepo, times(3)).getByIdList(null);
    }


    @Test
    void testGetAllDataAccessExceptionCase() {
        when(certificateRepo.getAll()).thenThrow(new DataAccessException("") {
        });

        assertThrows(RepositoryException.class, () -> service.getAll());
        verify(certificateRepo).getAll();
    }

    @Test
    void testGetByIdCertificateExistsCase() {
        GiftCertificate certificate = new GiftCertificate(1, "Test", "Test Description",
                1.0, 1, null, null);
        when(certificateRepo.getById(1)).thenReturn(certificate);
        when(tagCertificateRepo.getAllTagsIdByGiftCertificate(1)).thenReturn(Collections.emptyList());

        GiftCertificate result = service.getById(1);

        assertNotNull(result);
        assertEquals(certificate, result);
        verify(certificateRepo, times(1)).getById(anyInt());
        verify(tagCertificateRepo, times(1)).getAllTagsIdByGiftCertificate(anyInt());
    }

    @Test
    void testGetByIdCertificateDoesNotExistCase() {
        when(certificateRepo.getById(1)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.getById(1));
        verify(certificateRepo, times(1)).getById(anyInt());
        verify(tagCertificateRepo, times(0)).getAllTagsIdByGiftCertificate(anyInt());
    }

    @Test
    void testGetByIdDataAccessExceptionCase() {
        when(certificateRepo.getById(1)).thenThrow(new DataAccessException("") {
        });

        assertThrows(RepositoryException.class, () -> service.getById(1));
        verify(certificateRepo, times(1)).getById(anyInt());
        verify(tagCertificateRepo, times(0)).getAllTagsIdByGiftCertificate(anyInt());
    }

    @Test
    void testGetByParamsAllNullCase() {
        List<GiftCertificate> list = service.getByParams(null, null, null);

        assertEquals(Collections.emptyList(), list);
    }

    @Test
    void testGetByParamsAllTagNameNotNullCase() {
        GiftCertificate certificate = new GiftCertificate(1, "Test", "Test Description",
                1.0, 1, null, null);
        Tag tag = new Tag(1, "tagName");

        when(tagRepo.getByName(anyString())).thenReturn(tag);
        when(tagCertificateRepo.getAllCertificateIdByTag(tag.getId())).thenReturn(List.of(1));
        when(certificateRepo.getByIdList(anyList())).thenReturn(List.of(certificate));
        when(tagCertificateRepo.getAllTagsIdByGiftCertificate(anyInt())).thenReturn(List.of(1));
        when(tagRepo.getByIdList(anyList())).thenReturn(List.of(tag));

        List<GiftCertificate> list = service.getByParams("tagName", null, null);

        certificate.setTags(List.of(tag));

        verify(tagRepo, times(1)).getByName("tagName");
        verify(tagCertificateRepo, times(1)).getAllCertificateIdByTag(1);
        verify(certificateRepo, times(1)).getByIdList(anyList());
        verify(tagCertificateRepo, times(1)).getAllTagsIdByGiftCertificate(anyInt());
        verify(tagRepo, times(1)).getByIdList(anyList());

        assertEquals(List.of(certificate), list);
    }

    @Test
    void testGetByParamsAllPartNotNullCase() {
        GiftCertificate certificate = new GiftCertificate(1, "Test", "Test Description",
                1.0, 1, null, null);

        when(certificateRepo.getByPartOfNameDescription("part")).thenReturn(List.of(certificate));
        when(tagCertificateRepo.getAllTagsIdByGiftCertificate(anyInt())).thenReturn(Collections.emptyList());
        when(tagRepo.getByIdList(anyList())).thenReturn(Collections.emptyList());

        List<GiftCertificate> list = service.getByParams(null, "part", null);

        verify(certificateRepo, times(1)).getByPartOfNameDescription("part");
        verify(tagCertificateRepo, times(1)).getAllTagsIdByGiftCertificate(anyInt());
        verify(tagRepo, times(1)).getByIdList(anyList());

        assertEquals(List.of(certificate), list);
    }

    @Test
    void testGetByParamsAllASCCase() {
        List<GiftCertificate> list = new ArrayList<>();
        list.add(new GiftCertificate(1, "Test", "Test Description",
                1.0, 1, LocalDateTime.MIN, LocalDateTime.MIN));
        list.add(new GiftCertificate(1, "Test", "Test Description",
                1.0, 1, LocalDateTime.MAX, LocalDateTime.MAX));

        when(service.getAll()).thenReturn(new ArrayList<>(list));

        List<GiftCertificate> result = service.getByParams(null, null, "ASC");
        assertEquals(List.of(list.get(0), list.get(1)), result);
    }

    @Test
    void testGetByParamsAllDESCSCase() {
        List<GiftCertificate> list = new ArrayList<>();
        list.add(new GiftCertificate(1, "Test", "Test Description",
                1.0, 1, LocalDateTime.MIN, LocalDateTime.MIN));
        list.add(new GiftCertificate(1, "Test", "Test Description",
                1.0, 1, LocalDateTime.MAX, LocalDateTime.MAX));

        when(service.getAll()).thenReturn(new ArrayList<>(list));

        List<GiftCertificate> result = service.getByParams(null, null, "DESC");
        assertEquals(List.of(list.get(1), list.get(0)), result);
    }

    @Test
    void testCreateWithoutTagsCase() {
        GiftCertificate certificate = new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null);

        when(certificateRepo.create(any())).thenReturn(certificate);

        GiftCertificate result = service.create(certificate);

        assertEquals(certificate, result);
    }

    @Test
    void testCreateWithoutTagsDataAccessExceptionCase() {
        GiftCertificate certificate = new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null);

        when(certificateRepo.create(any())).thenThrow(new DataAccessException("") {});

        assertThrows(RepositoryException.class, () -> service.create(certificate));
    }

    @Test
    void testCreateWithTags() {
        GiftCertificate certificate = new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null);
        GiftCertificate certificate2 = new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null);
        Tag tag = new Tag(null, "tag");
        Tag tag2 = new Tag(1, "tag");
        certificate.setTags(List.of(tag));
        certificate2.setTags(List.of(tag2));

        when(tagRepo.getByName("tag")).thenReturn(null);
        when(tagRepo.getByName("tag")).thenReturn(new Tag(1, "tag"));
        when(tagCertificateRepo.createTagGiftCertificate(1, 1)).thenReturn(1);
        when(certificateRepo.create(any())).thenReturn(certificate);

        assertEquals(certificate2, service.create(certificate));

        verify(tagRepo, times(3)).getByName(anyString());
        verify(tagCertificateRepo, times(1)).createTagGiftCertificate(1, 1);
        verify(certificateRepo, times(1)).create(any());

    }

    @Test
    void testUpdateWithoutTag() {
        GiftCertificateServiceImpl spiedService = spy(service);
        GiftCertificate certificate = new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null);

        doReturn(certificate).when(spiedService).getById(anyInt());
        when(certificateRepo.update(1, certificate)).thenReturn(certificate);

        GiftCertificate result = spiedService.update(1, certificate);

        verify(spiedService, times(1)).getById(anyInt());
        verify(certificateRepo, times(1)).update(anyInt(), any());

        assertEquals(certificate, result);
    }

    @Test
    void testUpdateWithoutTagDataAccessExceptionCase() {
        GiftCertificateServiceImpl spiedService = spy(service);
        GiftCertificate certificate = new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null);

        doReturn(certificate).when(spiedService).getById(anyInt());
        when(certificateRepo.update(1, certificate)).thenThrow(new DataAccessException("") {});

        assertThrows(RepositoryException.class, () -> spiedService.update(1, certificate));

        verify(spiedService, times(1)).getById(anyInt());
        verify(certificateRepo, times(1)).update(anyInt(), any());
    }

    @Test
    void testUpdateWithTag() {
        GiftCertificateServiceImpl spiedService = spy(service);

        GiftCertificate certificate = new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null);
        GiftCertificate certificate2 = new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null);
        Tag tag = new Tag(null, "tag");
        Tag tag2 = new Tag(1, "tag");
        certificate2.setTags(List.of(tag2));

        doReturn(certificate).when(spiedService).getById(anyInt());
        when(tagRepo.getByName("tag")).thenReturn(null);

        when(tagRepo.create(any())).thenReturn(tag2);
        when(certificateRepo.update(1, certificate)).thenReturn(certificate);
        when(tagCertificateRepo.createTagGiftCertificate(1, 1)).thenReturn(1);
        when(tagCertificateRepo.getAllTagsIdByGiftCertificate(1)).thenReturn(null);
        when(tagRepo.getByIdList(any())).thenReturn(List.of(tag2));

        assertEquals(certificate2, spiedService.update(1, certificate2));

        verify(spiedService, times(1)).getById(anyInt());
        verify(tagRepo, times(2)).getByName(anyString());
        verify(tagRepo, times(1)).create(any());
        verify(tagCertificateRepo, times(1)).createTagGiftCertificate(1, 1);
        verify(tagCertificateRepo, times(1)).getAllTagsIdByGiftCertificate(anyInt());
        verify(tagRepo, times(1)).getByIdList(null);
    }

    @Test
    void testDeleteExistsCase() {
        GiftCertificate certificate = new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null);

        GiftCertificateServiceImpl spiedService = spy(service);

        doReturn(certificate).when(spiedService).getById(anyInt());
        when(certificateRepo.delete(anyInt())).thenReturn(1);

        GiftCertificate result = spiedService.delete(1);

        verify(spiedService, times(1)).getById(anyInt());
        verify(spiedService, times(1)).delete(anyInt());

        assertEquals(certificate, result);
    }

    @Test
    void testDeleteDataAccessExceptionCase() {
        GiftCertificate certificate = new GiftCertificate(1, "Test", "Test Description", 1.0, 1, null, null);

        GiftCertificateServiceImpl spiedService = spy(service);

        doReturn(certificate).when(spiedService).getById(anyInt());
        when(certificateRepo.delete(anyInt())).thenThrow(new DataAccessException("") {
        });

        assertThrows(RepositoryException.class, () -> spiedService.delete(1));
        verify(spiedService, times(1)).getById(anyInt());
    }
}

