package com.epam.esm.controller;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.UserOrderDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.hateoas.OrderLinker;
import com.epam.esm.service.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CollectionModel<UserDTO>> getAll(
            @ModelAttribute() Pagination pagination
    ) {
        List<UserDTO> users = service.getAll(pagination);

        return ResponseEntity.ok(bindLinks(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") long id) {
        UserDTO user = service.getById(id);

        bindLinks(user);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<CollectionModel<UserOrderDTO>> getOrders(
            @PathVariable("userId") long userId,
            @ModelAttribute() Pagination pagination
    ) {
        List<UserOrderDTO> userOrders = service.getOrders(userId, pagination);

        return ResponseEntity.ok(OrderLinker.bindLinksForUserOrder(userOrders));
    }
}
