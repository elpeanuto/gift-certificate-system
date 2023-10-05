package com.epam.esm.repository.impl;

import com.epam.esm.model.dto.filter.GiftCertificateFilter;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.repository.api.GiftCertificateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public List<GiftCertificateEntity> getAll(Pagination filter) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateEntity> query = cb.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> root = query.from(GiftCertificateEntity.class);

        query.select(root);

        TypedQuery<GiftCertificateEntity> typedQuery = manager.createQuery(query);

        typedQuery.setFirstResult(filter.getPage() * filter.getLimit());
        typedQuery.setMaxResults(filter.getLimit());

        return typedQuery.getResultList();
    }

    @Override
    public long getTotalCount() {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<GiftCertificateEntity> root = countQuery.from(GiftCertificateEntity.class);
        countQuery.select(cb.count(root));

        TypedQuery<Long> typedQuery = manager.createQuery(countQuery);

        return typedQuery.getSingleResult();
    }

    @Override
    public long getFilterCount(GiftCertificateFilter filter) {
        return configureTypedQuery(filter).getResultList().size();
    }


    @Override
    public List<GiftCertificateEntity> doSearch(GiftCertificateFilter filter) {
        TypedQuery<GiftCertificateEntity> typedQuery = configureTypedQuery(filter);

        typedQuery.setFirstResult(filter.getPage() * filter.getLimit());
        typedQuery.setMaxResults(filter.getLimit());

        return typedQuery.getResultList();
    }

    @Override
    public Optional<GiftCertificateEntity> getById(long id) {
        GiftCertificateEntity entity = manager.find(GiftCertificateEntity.class, id);

        return Optional.ofNullable(entity);
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
    public boolean isCertificateOrdered(long id) {
        Query query = manager.createQuery("SELECT COUNT(o) > 0 FROM OrderEntity o JOIN o.certificates c WHERE c.id = :certificateId");
        query.setParameter("certificateId", id);

        return (boolean) query.getSingleResult();
    }

    private TypedQuery<GiftCertificateEntity> configureTypedQuery(GiftCertificateFilter filter) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateEntity> query = cb.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> root = query.from(GiftCertificateEntity.class);
        query.select(root);

        List<Predicate> predicate = new ArrayList<>();

        if (filter.getTags() != null && !filter.getTags().isEmpty()) {
            Join<GiftCertificateEntity, TagEntity> tagsJoin = root.join("tags");
            Set<String> tagNames = filter.getTags();
            int numTags = tagNames.size();

            Expression<String> upperCaseTagName = cb.upper(tagsJoin.get("name"));

            Predicate[] predicates = tagNames.stream()
                    .map(tagName -> cb.equal(upperCaseTagName, tagName.toUpperCase()))
                    .toArray(Predicate[]::new);

            Predicate temp = cb.or(predicates);

            predicate.add(temp);

            query.groupBy(root.get("id"))
                    .having(cb.equal(cb.countDistinct(tagsJoin.get("id")), numTags));
        }

        if (filter.getPartOfNameDescription() != null && !filter.getPartOfNameDescription().isEmpty()) {
            String searchString = "%" + filter.getPartOfNameDescription().toUpperCase() + "%";

            Predicate nameOrDescriptionLike = cb.or(
                    cb.like(cb.upper(root.get("name")), searchString),
                    cb.like(cb.upper(root.get("description")), searchString)
            );

            predicate.add(nameOrDescriptionLike);
        }

        query.where(predicate.toArray(new Predicate[0]));

        if (filter.getSortOrder() != null) {
            if (filter.getSortOrder().equalsIgnoreCase("desc")) {
                query.orderBy(cb.desc(root.get("createDate")));
            } else if (filter.getSortOrder().equalsIgnoreCase("asc")) {
                query.orderBy(cb.asc(root.get("createDate")));
            }
        }

        return manager.createQuery(query);
    }
}
