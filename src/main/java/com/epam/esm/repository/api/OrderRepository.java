package com.epam.esm.repository.api;

import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.OrderEntity;

import java.util.List;

public interface OrderRepository extends CRUDRepository<OrderEntity, Pagination> {

    List<OrderEntity> getByUserId(Long userId, Pagination pagination);

    OrderEntity getByUserOrderId(Long userId, Long orderId, Pagination pagination);
}

