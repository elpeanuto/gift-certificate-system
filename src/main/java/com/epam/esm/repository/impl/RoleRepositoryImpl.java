package com.epam.esm.repository.impl;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.RoleEntity;
import com.epam.esm.repository.api.RoleRepository;
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
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<RoleEntity> getAll(Pagination pagination) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<RoleEntity> query = cb.createQuery(RoleEntity.class);
        Root<RoleEntity> root = query.from(RoleEntity.class);

        query.select(root);

        TypedQuery<RoleEntity> typedQuery = manager.createQuery(query);

        typedQuery.setFirstResult(pagination.getPage() * pagination.getLimit());
        typedQuery.setMaxResults(pagination.getLimit());

        return typedQuery.getResultList();
    }

    @Override
    public Optional<RoleEntity> getById(long id) {
        RoleEntity entity = manager.find(RoleEntity.class, id);

        return Optional.ofNullable(entity);
    }

    @Override
    public RoleEntity create(RoleEntity roleEntity) {
        manager.persist(roleEntity);

        return roleEntity;
    }

    @Override
    public RoleEntity delete(long id) {
        RoleEntity entity = manager.find(RoleEntity.class, id);

        if (entity != null) {
            manager.remove(entity);
        }

        return entity;
    }

    @Override
    public Optional<RoleEntity> getByName(String name) {
        TypedQuery<RoleEntity> query = manager.createQuery("SELECT t FROM RoleEntity t WHERE t.name = :name", RoleEntity.class);
        query.setParameter("name", name);
        List<RoleEntity> roles = query.getResultList();
        return Optional.ofNullable(roles.isEmpty() ? null : roles.get(0));
    }
}
