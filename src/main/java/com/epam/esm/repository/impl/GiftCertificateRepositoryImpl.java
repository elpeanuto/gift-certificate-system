package com.epam.esm.repository.impl;

import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.repository.api.GiftCertificateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
    public List<GiftCertificateEntity> getAll() {
        manager.getTransaction().begin();

       // manager.

        return null;
    }

    @Override
    public GiftCertificateEntity getById(long id) {
        return null;
    }

    @Override
    public GiftCertificateEntity create(GiftCertificateEntity giftCertificateEntity) {
        return null;
    }

    @Override
    public GiftCertificateEntity delete(long id) {
        return null;
    }

    @Override
    public GiftCertificateEntity update(long id, GiftCertificateEntity giftCertificateEntity) {
        return null;
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
