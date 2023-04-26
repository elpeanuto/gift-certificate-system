package com.epam.esm.service;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.dto.Tag;
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
    private TagRepository<Tag> repository;

    private CRDService<Tag> service;

    @BeforeEach
    void setUp() {
        service = new TagServiceImpl(repository);
    }

    @Test
    void testGetAllReturnListOfTagsCase() {
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(new Tag(1, "tag1"));
        expectedTags.add(new Tag(2, "tag2"));

        when(repository.getAll()).thenReturn(expectedTags);

        List<Tag> actualTags = service.getAll();

        assertEquals(expectedTags, actualTags);
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
        Tag expectedTag = new Tag(1, "tag1");

        when(repository.getById(expectedTag.getId())).thenReturn(expectedTag);

        Tag actualTag = service.getById(expectedTag.getId());

        assertEquals(expectedTag, actualTag);
        verify(repository, times(1)).getById(expectedTag.getId());
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
        Tag expectedTag = new Tag(1, "tag1");

        when(repository.create(any(Tag.class))).thenReturn(expectedTag);

        Tag actualTag = service.create(expectedTag);

        assertEquals(expectedTag, actualTag);
        verify(repository, times(1)).create(expectedTag);
    }

    @Test
    void testCreateDuplicateTagNameCase() {
        Tag existingTag = new Tag(1, "tag1");

        when(repository.create(existingTag)).thenThrow(DuplicateKeyException.class);

        assertThrows(RepositoryException.class, () -> service.create(existingTag));
        verify(repository, times(1)).create(existingTag);
    }

    @Test
    void testDeleteExistingTagIdCase() {
        int existingTagId = 1;
        Tag tagToDelete = new Tag(existingTagId, "Test Tag");

        when(repository.getById(existingTagId)).thenReturn(tagToDelete);
        when(repository.delete(existingTagId)).thenReturn(1);

        Tag deletedTag = service.delete(existingTagId);

        assertEquals(tagToDelete, deletedTag);
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

        when(repository.getById(existingTagId)).thenReturn(new Tag(existingTagId, "Test Tag"));
        when(repository.delete(existingTagId)).thenThrow(new DataAccessException("") {
        });

        assertThrows(RepositoryException.class, () -> service.delete(existingTagId));
        verify(repository, times(1)).getById(existingTagId);
        verify(repository, times(1)).delete(existingTagId);
    }

    @Test
    void testDeleteExistingTagIdRepositoryReturnsZeroDeletedItemsCase() {
        int existingTagId = 1;

        when(repository.getById(existingTagId)).thenReturn(new Tag(existingTagId, "Test Tag"));
        when(repository.delete(existingTagId)).thenReturn(0);

        assertThrows(RepositoryException.class, () -> service.delete(existingTagId));
        verify(repository, times(1)).getById(existingTagId);
        verify(repository, times(1)).delete(existingTagId);
    }
}
