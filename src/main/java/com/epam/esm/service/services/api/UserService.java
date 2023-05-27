package com.epam.esm.service.services.api;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.UserOrderDTO;
import com.epam.esm.model.dto.filter.Pagination;

import java.util.List;

/**
 * The UserService interface provides methods for CRUD operations on user DTOs.
 * It extends the CRUDService interface for basic CRUD operations.
 */
public interface UserService extends CRUDService<UserDTO, Pagination> {

    /**
     * Retrieves a list of user order DTOs associated with the specified user ID.
     *
     * @param id         The ID of the user.
     * @param pagination The pagination information for retrieving a subset of user orders.
     * @return A list of user order DTOs.
     */
    List<UserOrderDTO> getOrders(long id, Pagination pagination);

    UserOrderDTO getOrderInfo(long id, long orderId);
}

