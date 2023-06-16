package com.epam.esm.controller;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.UserOrderDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.hateoas.OrderLinker;
import com.epam.esm.service.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.model.hateoas.UserLinker.bindLinks;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('USER_ROLE, ADMIN_ROLE')")
    public ResponseEntity<CollectionModel<UserDTO>> getAll(
            @ModelAttribute() Pagination pagination
    ) {
        List<UserDTO> users = service.getAll(pagination);

        return ResponseEntity.ok(bindLinks(users));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_ROLE, ADMIN_ROLE')")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") long id) {
        UserDTO user = service.getById(id);

        bindLinks(user);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/orders")
    @PreAuthorize("hasAnyAuthority('USER_ROLE, ADMIN_ROLE')")
    public ResponseEntity<CollectionModel<OrderDTO>> getOrders(
            @PathVariable("userId") long userId,
            @ModelAttribute() Pagination pagination
    ) {
        List<OrderDTO> userOrders = service.getOrders(userId, pagination);

        return ResponseEntity.ok(OrderLinker.bindLinks(userOrders));
    }

    @GetMapping("/{userId}/orders/{orderId}")
    @PreAuthorize("hasAnyAuthority('USER_ROLE, ADMIN_ROLE')")
    public ResponseEntity<UserOrderDTO> getOrderInfo(
            @PathVariable("userId") long userId,
            @PathVariable("orderId") long orderId
    ) {
        UserOrderDTO userOrder = service.getOrderInfo(userId, orderId);

        OrderLinker.bindLinks(userOrder);

        return ResponseEntity.ok(userOrder);
    }
}
