package com.epam.esm.repository.impl;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.repository.api.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

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
    public Optional<UserEntity> getById(long id) {
        UserEntity entity = manager.find(UserEntity.class, id);

        return Optional.ofNullable(entity);
    }

    @Override
    public UserEntity create(UserEntity userEntity) {
        manager.persist(userEntity);
        return userEntity;
    }

    @Override
    public UserEntity delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<UserEntity> getByEmail(String email) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = cb.createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);

        query.select(root).where(cb.equal(root.get("email"), email));

        TypedQuery<UserEntity> typedQuery = manager.createQuery(query);

        return typedQuery.getResultList().stream().findFirst();
    }
}
