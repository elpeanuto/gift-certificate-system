package com.epam.esm.repository.api;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.OrderEntity;

import java.util.List;


/**
 * The OrderRepository interface provides methods for CRUD operations on order entities.
 * It extends the CRUDRepository interface for basic CRUD operations.
 *
 * @see CRUDRepository
 */
public interface OrderRepository extends CRUDRepository<OrderEntity, Pagination> {

    /**
     * Retrieves a list of orders associated with the specified user ID.
     *
     * @param userId     the ID of the user
     * @param pagination the pagination information for retrieving a subset of orders
     * @return a list of order entities
     */
    List<OrderEntity> getByUserId(Long userId, Pagination pagination);

    OrderEntity getByUserOrderId(Long userId, Long orderId);
}

