package com.epam.esm.repository.impl;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.repository.api.TagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public List<TagEntity> getAll(Pagination pagination) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> query = cb.createQuery(TagEntity.class);
        Root<TagEntity> root = query.from(TagEntity.class);

        query.select(root);

        TypedQuery<TagEntity> typedQuery = manager.createQuery(query);

        typedQuery.setFirstResult(pagination.getPage() * pagination.getLimit());
        typedQuery.setMaxResults(pagination.getLimit());

        return typedQuery.getResultList();
    }

    @Override
    public Optional<TagEntity> getById(long id) {
        TagEntity entity = manager.find(TagEntity.class, id);

        return Optional.ofNullable(entity);
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

    @Override
    public Optional<TagEntity> getWidelyUsedTag() {
        String subQuery = "SELECT c.id " +
                "FROM OrderEntity o " +
                "JOIN o.certificates c " +
                "WHERE o.user.id = ( " +
                "    SELECT o.user.id " +
                "    FROM OrderEntity o " +
                "    GROUP BY o.user.id " +
                "    ORDER BY SUM(o.price) DESC " +
                "    LIMIT 1" +
                ")";

        String jpqlQuery = "SELECT t.id, t.name " +
                "FROM GiftCertificateEntity gc " +
                "JOIN gc.tags t " +
                "WHERE gc.id IN (" + subQuery + ") " +
                "GROUP BY t.id, t.name " +
                "ORDER BY COUNT(t.id) DESC";

        TypedQuery<Object[]> query = manager.createQuery(jpqlQuery, Object[].class);
        query.setMaxResults(1);

        List<Object[]> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            Object[] result = resultList.get(0);
            Long tagId = (Long) result[0];
            String tagName = (String) result[1];
            TagEntity tagEntity = new TagEntity(tagId, tagName);
            return Optional.of(tagEntity);
        } else {
            return Optional.empty();
        }
    }
}

