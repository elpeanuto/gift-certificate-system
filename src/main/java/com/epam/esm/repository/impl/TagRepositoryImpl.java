package com.epam.esm.repository.impl;

import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.repository.api.TagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of the TagRepository interface that uses JdbcTemplate and NamedParameterJdbcTemplate to interact with the database.
 *
 * @see TagRepository
 */
@Repository
public class TagRepositoryImpl implements TagRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<TagEntity> getAll() {
        return manager.createQuery("SELECT t FROM TagEntity t", TagEntity.class).getResultList();
    }

    @Override
    public TagEntity getById(long id) {
        return manager.find(TagEntity.class, id);
    }

    @Override
    public TagEntity create(TagEntity tag) {
        manager.persist(tag);
        return tag;
    }

    @Override
    public TagEntity delete(long id) {
        TagEntity entity = manager.find(TagEntity.class, id);
        if (entity != null) {
            manager.remove(entity);
        }
        return entity;
    }

    @Override
    public List<TagEntity> getByIdList(List<Long> idList) {
        TypedQuery<TagEntity> query = manager.createQuery("SELECT t FROM TagEntity t WHERE t.id IN :idList", TagEntity.class);
        query.setParameter("idList", idList);
        return query.getResultList();
    }

    @Override
    public TagEntity getByName(String name) {
        TypedQuery<TagEntity> query = manager.createQuery("SELECT t FROM TagEntity t WHERE t.name = :name", TagEntity.class);
        query.setParameter("name", name);
        List<TagEntity> tags = query.getResultList();
        return tags.isEmpty() ? null : tags.get(0);
    }
}
