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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.controller.util.Util.bindingResultCheck;
import static com.epam.esm.model.hateoas.UserLinker.bindLinks;

/**
 * A RestController class that handles API requests related to users.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    /**
     * Constructs an instance of UserController with the specified service.
     *
     * @param service the service used to perform CRUD operations on User objects
     */
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * Retrieves a list of all User objects in the system.
     *
     * @param pagination a Pagination object used for pagination of the results
     * @return a ResponseEntity containing a CollectionModel of UserDTO objects
     */
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public ResponseEntity<CollectionModel<UserDTO>> getAll(
            @ModelAttribute() Pagination pagination
    ) {
        List<UserDTO> users = service.getAll(pagination).getResponseList();

        return ResponseEntity.ok(bindLinks(users));
    }

    /**
     * Retrieves a User object by its ID.
     *
     * @param id an integer representing the ID of the User object
     * @return a ResponseEntity containing a UserDTO object that matches the given ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") long id) {
        UserDTO user = service.getById(id);

        bindLinks(user);

        return ResponseEntity.ok(user);
    }

    /**
     * Retrieves a list of orders objects for the specified User ID.
     *
     * @param userId     an integer representing the ID of the User
     * @param pagination a Pagination object used for pagination of the results
     * @return a ResponseEntity containing a CollectionModel of UserOrderDTO objects
     */
    @GetMapping("/{userId}/orders")
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public ResponseEntity<CollectionModel<OrderDTO>> getOrders(
            @PathVariable("userId") long userId,
            @ModelAttribute() Pagination pagination
    ) {
        List<OrderDTO> userOrders = service.getOrders(userId, pagination);

        return ResponseEntity.ok(OrderLinker.bindLinks(userOrders));
    }

    /**
     * Retrieves a UserOrder objects for the specified User ID.
     *
     * @param userId an integer representing the ID of the User
     * @return a ResponseEntity containing a CollectionModel of UserOrderDTO objects
     */
    @GetMapping("/{userId}/orders/{orderId}")
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public ResponseEntity<UserOrderDTO> getOrderInfo(
            @PathVariable("userId") long userId,
            @PathVariable("orderId") long orderId
    ) {
        UserOrderDTO userOrder = service.getOrderInfo(userId, orderId);

        OrderLinker.bindLinks(userOrder);

        return ResponseEntity.ok(userOrder);
    }

    /**
     * Creates a new User object.
     *
     * @param userDTO       a UserDTO object representing the new User to be created
     * @param bindingResult a BindingResult object that holds the result of the validation process
     * @return a ResponseEntity containing a UserDTO object that represents the newly created User
     * @throws InvalidRequestBodyException if the provided data is not valid
     */
    @PostMapping()
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO,
                                          BindingResult bindingResult) {
        bindingResultCheck(bindingResult);

        UserDTO body = service.create(userDTO);

        UserLinker.bindLinks(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
