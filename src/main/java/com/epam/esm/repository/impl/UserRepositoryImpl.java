package com.epam.esm.repository.impl;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.repository.api.CRUDRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements CRUDRepository<UserEntity, Pagination> {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<UserEntity> getAll(Pagination pagination) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = cb.createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);

        query.select(root);

        TypedQuery<UserEntity> typedQuery = manager.createQuery(query);

        typedQuery.setFirstResult(pagination.getPage() * pagination.getLimit());
        typedQuery.setMaxResults(pagination.getLimit());

        return typedQuery.getResultList();
    }

    @Override
    public UserEntity getById(long id) {
        return manager.find(UserEntity.class, id);
    }

    @Override
    public UserEntity create(UserEntity userEntity) {
        return null;
    }

    @Override
    public UserEntity delete(long id) {
        return null;
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        return null;
    }
}
