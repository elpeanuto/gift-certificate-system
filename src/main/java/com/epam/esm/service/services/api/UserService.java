package com.epam.esm.service.services.api;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.UserOrderDTO;
import com.epam.esm.model.dto.filter.Pagination;

import java.util.List;

public interface UserService extends CRUDService<UserDTO, Pagination>{

    List<UserOrderDTO> getOrders(long id, Pagination pagination);

    List<UserOrderDTO> getOrderById(long userId, long orderId, Pagination pagination);
}

