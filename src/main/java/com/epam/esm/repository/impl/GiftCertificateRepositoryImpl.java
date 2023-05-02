package com.epam.esm.repository.impl;

import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.model.filtering.Pagination;
import com.epam.esm.repository.api.GiftCertificateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of {@link GiftCertificateRepository} interface that uses JdbcTemplate and NamedParameterJdbcTemplate
 * for accessing the database. Provides CRUD operations to work with GiftCertificate entities.
 *
 * @see GiftCertificateRepository
 */
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<GiftCertificateEntity> getAll(Pagination pagination) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateEntity> query = cb.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> root = query.from(GiftCertificateEntity.class);

        query.select(root);

        TypedQuery<GiftCertificateEntity> typedQuery = manager.createQuery(query);

        typedQuery.setFirstResult(pagination.page() * pagination.limit());
        typedQuery.setMaxResults(pagination.limit());

        return typedQuery.getResultList();
    }

    @Override
    public GiftCertificateEntity getById(long id) {
        GiftCertificateEntity entity = manager.find(GiftCertificateEntity.class, id);

        if(entity == null)
            throw new ResourceNotFoundException(id);

        return entity;
    }

    @Override
    public GiftCertificateEntity create(GiftCertificateEntity certificate) {
        manager.persist(certificate);
        return certificate;
    }

    @Override
    public GiftCertificateEntity delete(long id) {
        GiftCertificateEntity entity = manager.find(GiftCertificateEntity.class, id);

        if (entity != null) {
            manager.remove(entity);
        }

        return entity;
    }

    @Override
    public GiftCertificateEntity update(GiftCertificateEntity certificate) {
        return manager.merge(certificate);
    }

    @Override
    public List<GiftCertificateEntity> getByIdList(List<Long> idList) {
        return null;
    }

    @Override
    public List<GiftCertificateEntity> getByPartOfNameDescription(String pattern) {
        return null;
    }
}
