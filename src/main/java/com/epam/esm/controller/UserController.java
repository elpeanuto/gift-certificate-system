package com.epam.esm.controller;

import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.UserOrderDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.hateoas.OrderLinker;
import com.epam.esm.model.hateoas.UserLinker;
import com.epam.esm.service.services.api.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.model.hateoas.UserLinker.bindLinks;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public ResponseEntity<CollectionModel<OrderDTO>> getOrders(
            @PathVariable("userId") long userId,
            @ModelAttribute() Pagination pagination
    ) {
        List<OrderDTO> userOrders = service.getOrders(userId, pagination);

        return ResponseEntity.ok(OrderLinker.bindLinks(userOrders));
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<UserOrderDTO> getOrderInfo(
            @PathVariable("userId") long userId,
            @PathVariable("orderId") long orderId,
            @ModelAttribute() Pagination pagination
    ) {
        UserOrderDTO userOrder = service.getOrderInfo(userId, orderId, pagination);

        OrderLinker.bindLinks(userOrder);

        return ResponseEntity.ok(userOrder);
    }

    @PostMapping()
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            String str = String.join(", ", errorMessages);

            logger.warn(str);
            throw new InvalidRequestBodyException(str);
        }

        UserDTO body = service.create(userDTO);

        UserLinker.bindLinks(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
