package com.epam.esm.repository;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.repository.configuration.Config;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = Config.class)
@Transactional
class TagRepositoryImplTest {

    private final TagRepository tagRepository;
    private static List<TagEntity> list;

    @Autowired
    public TagRepositoryImplTest(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @BeforeAll
    static void setUp() {
        list = new ArrayList<>();

        for (long i = 1; i < 6; i++) {
            list.add(new TagEntity(i, "name" + i));
        }
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_tags.sql"})
    void testGetAll() {
        List<TagEntity> all = tagRepository.getAll(new Pagination(0, 10));
        Assertions.assertEquals(list, all);
    }

    @Test
    @Sql({"/sql/clear_tables.sql"})
    void testGetAllEmpty() {
        List<TagEntity> all = tagRepository.getAll(new Pagination(0, 10));
        Assertions.assertTrue(all.isEmpty());
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_tags.sql"})
    void testGetById() {
        Optional<TagEntity> tag = tagRepository.getById(1L);
        Assertions.assertTrue(tag.isPresent());
        Assertions.assertEquals(new TagEntity(1L, "name1"), tag.get());
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_tags.sql"})
    void testGetByIdNonExistent() {
        Optional<TagEntity> tag = tagRepository.getById(10L);
        Assertions.assertTrue(tag.isEmpty());
    }

    @Test
    @Sql({"/sql/clear_tables.sql"})
    void testGetByIdEmpty() {
        Optional<TagEntity> tag = tagRepository.getById(1L);
        Assertions.assertTrue(tag.isEmpty());
    }

    @Test
    @Sql({"/sql/clear_tables.sql"})
    void testCreate() {
        TagEntity tag = new TagEntity(null, "name1");
        TagEntity createdTag = tagRepository.create(tag);
        Assertions.assertNotNull(createdTag.getId());
        Assertions.assertEquals(tag, createdTag);
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_tags.sql"})
    void testDelete() {
        TagEntity deletedTag = tagRepository.delete(1L);
        Assertions.assertNotNull(deletedTag);
        Assertions.assertEquals(new TagEntity(1L, "name1"), deletedTag);

        Optional<TagEntity> tag = tagRepository.getById(1L);
        Assertions.assertTrue(tag.isEmpty());
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_tags.sql"})
    void testGetByIdList() {
        List<Long> idList = Arrays.asList(1L, 2L, 3L);
        List<TagEntity> tags = tagRepository.getByIdList(idList);
        Assertions.assertEquals(3, tags.size());
        Assertions.assertEquals(Arrays.asList(new TagEntity(1L, "name1"), new TagEntity(2L, "name2"), new TagEntity(3L, "name3")), tags);
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_tags.sql"})
    void testGetByName() {
        TagEntity tag = tagRepository.getByName("name1");
        Assertions.assertNotNull(tag);
        Assertions.assertEquals(new TagEntity(1L, "name1"), tag);
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_tags.sql"})
    void testGetByNameNonExistent() {
        TagEntity tag = tagRepository.getByName("nonexistent");
        Assertions.assertNull(tag);
    }

    @Test
    @Sql({"/sql/clear_tables.sql", "/sql/create_tags.sql"})
    void testGetWidelyUsedTag() {
        Optional<TagEntity> widelyUsedTag = tagRepository.getWidelyUsedTag();
        Assertions.assertTrue(widelyUsedTag.isEmpty());
    }
}
