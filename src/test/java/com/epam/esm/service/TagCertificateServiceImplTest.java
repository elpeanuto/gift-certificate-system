package com.epam.esm.service;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.service.api.CRDService;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagCertificateServiceImplTest {

    @Mock
    private TagRepository<TagDTO> repository;

    private CRDService<TagDTO> service;

    @BeforeEach
    void setUp() {
        service = new TagServiceImpl(repository);
    }

    @Test
    void testGetAllReturnListOfTagsCase() {
        List<TagDTO> expectedTagDTOS = new ArrayList<>();
        expectedTagDTOS.add(new TagDTO(1, "tag1"));
        expectedTagDTOS.add(new TagDTO(2, "tag2"));

        when(repository.getAll()).thenReturn(expectedTagDTOS);

        List<TagDTO> actualTagDTOS = service.getAll();

        assertEquals(expectedTagDTOS, actualTagDTOS);
        verify(repository, times(1)).getAll();
    }

    @Test
    void testGetAllRepositoryExceptionCase() {
        when(repository.getAll()).thenThrow(new DataAccessException("") {});

        assertThrows(RepositoryException.class, () -> service.getAll());
        verify(repository, times(1)).getAll();
    }

    @Test
    void testGetByIdExistingTagIdCase() {
        TagDTO expectedTagDTO = new TagDTO(1, "tag1");

        when(repository.getById(expectedTagDTO.getId())).thenReturn(expectedTagDTO);

        TagDTO actualTagDTO = service.getById(expectedTagDTO.getId());

        assertEquals(expectedTagDTO, actualTagDTO);
        verify(repository, times(1)).getById(expectedTagDTO.getId());
    }

    @Test
    void testGetByIdNonExistingTagIdCase() {
        int nonExistingTagId = 1;

        when(repository.getById(nonExistingTagId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.getById(nonExistingTagId));
        verify(repository, times(1)).getById(nonExistingTagId);
    }

    @Test
    void testCreateValidTagCase() {
        TagDTO expectedTagDTO = new TagDTO(1, "tag1");

        when(repository.create(any(TagDTO.class))).thenReturn(expectedTagDTO);

        TagDTO actualTagDTO = service.create(expectedTagDTO);

        assertEquals(expectedTagDTO, actualTagDTO);
        verify(repository, times(1)).create(expectedTagDTO);
    }

    @Test
    void testCreateDuplicateTagNameCase() {
        TagDTO existingTagDTO = new TagDTO(1, "tag1");

        when(repository.create(existingTagDTO)).thenThrow(DuplicateKeyException.class);

        assertThrows(RepositoryException.class, () -> service.create(existingTagDTO));
        verify(repository, times(1)).create(existingTagDTO);
    }

    @Test
    void testDeleteExistingTagIdCase() {
        int existingTagId = 1;
        TagDTO tagDTOToDelete = new TagDTO(existingTagId, "Test Tag");

        when(repository.getById(existingTagId)).thenReturn(tagDTOToDelete);
        when(repository.delete(existingTagId)).thenReturn(1);

        TagDTO deletedTagDTO = service.delete(existingTagId);

        assertEquals(tagDTOToDelete, deletedTagDTO);
        verify(repository, times(1)).getById(existingTagId);
        verify(repository, times(1)).delete(existingTagId);
    }

    @Test
    void testDeleteNonExistingTagIdCase() {
        int nonExistingTagId = 1;

        when(repository.getById(nonExistingTagId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingTagId));
        verify(repository, times(1)).getById(nonExistingTagId);
    }

    @Test
    void testDeleteExistingTagIdRepositoryThrowsDataAccessExceptionCase() {
        int existingTagId = 1;

        when(repository.getById(existingTagId)).thenReturn(new TagDTO(existingTagId, "Test Tag"));
        when(repository.delete(existingTagId)).thenThrow(new DataAccessException("") {
        });

        assertThrows(RepositoryException.class, () -> service.delete(existingTagId));
        verify(repository, times(1)).getById(existingTagId);
        verify(repository, times(1)).delete(existingTagId);
    }

    @Test
    void testDeleteExistingTagIdRepositoryReturnsZeroDeletedItemsCase() {
        int existingTagId = 1;

        when(repository.getById(existingTagId)).thenReturn(new TagDTO(existingTagId, "Test Tag"));
        when(repository.delete(existingTagId)).thenReturn(0);

        assertThrows(RepositoryException.class, () -> service.delete(existingTagId));
        verify(repository, times(1)).getById(existingTagId);
        verify(repository, times(1)).delete(existingTagId);
    }
}
