package com.epam.esm.service;

import com.epam.esm.exception.exceptions.EntityAlreadyExistsException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.converter.TagConverter;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.service.services.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private TagServiceImpl service;

    @Mock
    private TagRepository repository;

    private List<TagDTO> dtoList;
    private List<TagEntity> entityList;

    @BeforeEach
    void setUp() {
        service = new TagServiceImpl(repository);

        dtoList = List.of(
                new TagDTO(1L, "tag1"),
                new TagDTO(2L, "tag2"),
                new TagDTO(3L, "tag3")
        );

        entityList = dtoList.stream()
                .map(TagConverter::toEntity)
                .toList();
    }

    @Test
    void getAllTest() {
        Pagination pagination = new Pagination(0, 5);
        when(repository.getAll(pagination)).thenReturn(entityList);

        List<TagDTO> result = service.getAll(pagination).getResponseList();

        Assertions.assertEquals(dtoList, result);
    }

    @Test
    void getByIdTest() {
        long id = 1L;
        TagEntity entity = entityList.get(0);
        when(repository.getById(id)).thenReturn(Optional.of(entity));

        TagDTO result = service.getById(id);

        Assertions.assertEquals(dtoList.get(0), result);
    }

    @Test
    void getByIdFailTest() {
        long id = -1L;
        when(repository.getById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
    }

    @Test
    void createTest() {
        TagDTO tagDTO = new TagDTO(null, "New Tag");
        TagEntity createdEntity = new TagEntity(1L, "New Tag");

        when(repository.getByName(tagDTO.getName())).thenReturn(null);
        when(repository.create(any(TagEntity.class))).thenReturn(createdEntity);

        TagDTO result = service.create(tagDTO);

        assertNotNull(result.getId());
        assertEquals(tagDTO.getName(), result.getName());
    }

    @Test
    void createDuplicateTest() {
        TagDTO tagDTO = new TagDTO(null, "Duplicate Tag");
        TagEntity existingEntity = new TagEntity(1L, "Duplicate Tag");

        when(repository.getByName(tagDTO.getName())).thenReturn(existingEntity);


        assertThrows(EntityAlreadyExistsException.class, () -> service.create(tagDTO));
    }

    @Test
    void deleteTest() {
        long id = 1L;
        TagEntity deletedEntity = entityList.get(0);

        when(repository.delete(id)).thenReturn(deletedEntity);

        TagDTO result = service.delete(id);

        assertEquals(dtoList.get(0), result);
    }

    @Test
    void deleteFailTest() {
        long id = 1L;

        when(repository.delete(id)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));
    }

    @Test
    void getWidelyUsedTagTest() {
        TagEntity widelyUsedTag = entityList.get(0);

        when(repository.getWidelyUsedTag()).thenReturn(Optional.of(widelyUsedTag));

        TagDTO result = service.getWidelyUsedTag();

        assertEquals(dtoList.get(0), result);
    }

    @Test
    void getWidelyUsedTagFailTest() {
        when(repository.getWidelyUsedTag()).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getWidelyUsedTag());
    }
}
