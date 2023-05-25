package com.epam.esm.service.services.api;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.UserOrderDTO;
import com.epam.esm.model.dto.filter.Pagination;

import java.util.List;

public interface UserService extends CRUDService<UserDTO, Pagination> {

    List<OrderDTO> getOrders(long id, Pagination pagination);

    UserOrderDTO getOrderInfo(long id, long orderId, Pagination pagination);
}

