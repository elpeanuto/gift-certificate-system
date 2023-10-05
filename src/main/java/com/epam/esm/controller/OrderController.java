package com.epam.esm.controller;

import com.epam.esm.controller.util.OrderValidationGroup;
import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.service.services.api.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.controller.util.Util.bindingResultCheck;
import static com.epam.esm.model.hateoas.OrderLinker.bindLinks;

/**
 * A RestController class that handles API requests related to orders.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CRUDService<OrderDTO, Pagination> service;

    /**
     * Constructor for OrderController class.
     *
     * @param service a CRUDService object used to interact with order data in the database
     */
    @Autowired
    public OrderController(CRUDService<OrderDTO, Pagination> service) {
        this.service = service;
    }

    /**
     * Retrieves all orders.
     *
     * @param pagination a Pagination object used for pagination of the results
     * @return a ResponseEntity containing a CollectionModel of OrderDTO objects
     */
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public ResponseEntity<CollectionModel<OrderDTO>> getAll(
            @ModelAttribute() Pagination pagination
    ) {
        List<OrderDTO> orders = service.getAll(pagination).getResponseList();

        return ResponseEntity.ok(bindLinks(orders));
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id an integer representing the ID of the order
     * @return a ResponseEntity containing an OrderDTO object that matches the given ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public ResponseEntity<OrderDTO> getById(@PathVariable("id") long id) {
        OrderDTO order = service.getById(id);

        bindLinks(order);

        return ResponseEntity.ok(order);
    }

    /**
     * Creates a new order with the provided data.
     *
     * @param orderDTO      an OrderDTO object representing the new order to be created
     * @param bindingResult a BindingResult object that holds the result of the validation process
     * @return a ResponseEntity containing an OrderDTO object that represents the newly created order
     * @throws InvalidRequestBodyException if the provided data is not valid
     */
    @PostMapping()
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public ResponseEntity<OrderDTO> create(@RequestBody @Validated(OrderValidationGroup.class) OrderDTO orderDTO, BindingResult bindingResult) {
        bindingResultCheck(bindingResult);

        BindingResult userBindingResult = new BeanPropertyBindingResult(orderDTO.getUser(), "user");

        bindingResultCheck(userBindingResult);

        int i = 1;
        for (GiftCertificateDTO certificate : orderDTO.getCertificates()) {
            BindingResult certificateBindingResult = new BeanPropertyBindingResult(certificate, "certificate" + i);
            bindingResultCheck(certificateBindingResult);

            i++;
        }

        OrderDTO order = service.create(orderDTO);

        bindLinks(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    /**
     * Deletes an order with the provided ID.
     *
     * @param id an integer representing the ID of the order to be deleted
     * @return a ResponseEntity containing an OrderDTO object that represents the deleted order
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public ResponseEntity<OrderDTO> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
