package com.epam.esm.service;

import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.converter.GiftCertificateConverter;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.repository.api.GiftCertificateRepository;
import com.epam.esm.service.services.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    private GiftCertificateServiceImpl service;

    @Mock
    private GiftCertificateRepository repository;

    private List<GiftCertificateDTO> dtoList;
    private List<GiftCertificateEntity> entityList;

    @BeforeEach
    void setUp() {
        service = new GiftCertificateServiceImpl(repository, null);

        dtoList = List.of(
                new GiftCertificateDTO(
                        1L,
                        "name",
                        "description",
                        100D,
                        10,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ),
                new GiftCertificateDTO(
                        2L,
                        "name",
                        "description",
                        200D,
                        20,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ),
                new GiftCertificateDTO(
                        3L,
                        "name",
                        "description",
                        300D,
                        30,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                )
        );

        entityList = dtoList.stream()
                .map(GiftCertificateConverter::toEntity)
                .toList();
    }

    @Test
    void getAllTest() {
        Pagination pagination = new Pagination(0, 5);
        when(repository.getAll(pagination)).thenReturn(entityList);

        List<GiftCertificateDTO> result = service.getAll(pagination).getResponseList();

        assertEquals(dtoList, result);
    }

    @Test
    void doSearchTest() {
        when(repository.doSearch(null)).thenReturn(entityList);

        List<GiftCertificateDTO> result = service.doSearch(null).getResponseList();

        assertEquals(dtoList, result);
    }

    @Test
    void getByIdTest() {
        long id = 1L;
        GiftCertificateEntity entity = entityList.get(0);
        when(repository.getById(id)).thenReturn(Optional.of(entity));

        GiftCertificateDTO result = service.getById(id);

        assertEquals(dtoList.get(0), result);
    }

    @Test
    void getByIdFailTest() {
        long id = -1L;
        when(repository.getById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
    }

    @Test
    void createTest() {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(
                null,
                "New Certificate",
                "New Description",
                500D,
                50,
                null,
                null
        );

        GiftCertificateEntity createdEntity = new GiftCertificateEntity(
                1L,
                "New Certificate",
                "New Description",
                500D,
                50,
                LocalDateTime.now(),
                LocalDateTime.now(),
                new HashSet<>()
        );

        when(repository.create(any(GiftCertificateEntity.class))).thenReturn(createdEntity);

        GiftCertificateDTO result = service.create(giftCertificateDTO);

        assertNotNull(result.getId());
        assertEquals(giftCertificateDTO.getName(), result.getName());
        assertEquals(giftCertificateDTO.getDescription(), result.getDescription());
        assertEquals(giftCertificateDTO.getPrice(), result.getPrice());
        assertEquals(giftCertificateDTO.getDuration(), result.getDuration());
        assertNotNull(result.getCreateDate());
        assertNotNull(result.getLastUpdateDate());
    }

    @Test
    void deleteTest() {
        long id = 1L;
        GiftCertificateEntity deletedEntity = entityList.get(0);

        when(repository.isCertificateOrdered(id)).thenReturn(false);
        when(repository.delete(id)).thenReturn(deletedEntity);

        GiftCertificateDTO result = service.delete(id);

        assertEquals(dtoList.get(0), result);
    }

    @Test
    void deleteFailTest() {
        long id = 1L;

        when(repository.isCertificateOrdered(id)).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> service.delete(id));
    }

    @Test
    void updateTest() {
        long id = 1L;
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(
                id,
                "Updated Certificate",
                "Updated Description",
                500D,
                50,
                null,
                null
        );
        GiftCertificateEntity existingEntity = entityList.get(0);
        GiftCertificateEntity updatedEntity = new GiftCertificateEntity(
                id,
                "Updated Certificate",
                "Updated Description",
                500D,
                50,
                existingEntity.getCreateDate(),
                LocalDateTime.now(),
                existingEntity.getTags()
        );

        when(repository.getById(id)).thenReturn(Optional.of(existingEntity));
        when(repository.update(any(GiftCertificateEntity.class))).thenReturn(updatedEntity);

        GiftCertificateDTO result = service.update(id, giftCertificateDTO);

        assertEquals(giftCertificateDTO, result);
        assertNotNull(result.getLastUpdateDate());
    }
}
