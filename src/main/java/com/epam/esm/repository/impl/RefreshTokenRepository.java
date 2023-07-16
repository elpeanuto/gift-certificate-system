package com.epam.esm.repository.impl;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.RefreshTokenEntity;
import com.epam.esm.repository.api.CRUDRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RefreshTokenRepository implements CRUDRepository<RefreshTokenEntity, Pagination> {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<RefreshTokenEntity> getAll(Pagination filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<RefreshTokenEntity> getById(long id) {
        throw new UnsupportedOperationException();    }

    @Override
    public RefreshTokenEntity create(RefreshTokenEntity refreshTokenEntity) {
        manager.persist(refreshTokenEntity);

        return refreshTokenEntity;
    }

    @Override
    public RefreshTokenEntity delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RefreshTokenEntity update(RefreshTokenEntity refreshTokenEntity) {
        return manager.merge(refreshTokenEntity);
    }
}
