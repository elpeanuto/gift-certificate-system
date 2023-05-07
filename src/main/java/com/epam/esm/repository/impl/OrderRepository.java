package com.epam.esm.repository.impl;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.repository.api.CRUDRepository;
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
public class OrderRepository implements CRUDRepository<OrderEntity, Pagination> {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<OrderEntity> getAll(Pagination pagination) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> query = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> root = query.from(OrderEntity.class);

        query.select(root);

        TypedQuery<OrderEntity> typedQuery = manager.createQuery(query);

        typedQuery.setFirstResult(pagination.getPage() * pagination.getLimit());
        typedQuery.setMaxResults(pagination.getLimit());

        return typedQuery.getResultList();
    }

    @Override
    public Optional<OrderEntity> getById(long id) {
        OrderEntity entity = manager.find(OrderEntity.class, id);

        return Optional.ofNullable(entity);
    }

    @Override
    public OrderEntity create(OrderEntity order) {
        manager.persist(order);
        return order;
    }

    @Override
    public OrderEntity delete(long id) {
        return null;
    }

    @Override
    public OrderEntity update(OrderEntity order) {
        return null;
    }
}
