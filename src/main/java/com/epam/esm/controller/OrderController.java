package com.epam.esm.controller;

import com.epam.esm.controller.util.OrderValidationGroup;
import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.service.services.api.CRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.epam.esm.model.hateoas.OrderLinker.bindLinks;

/**
 * A RestController class that handles API requests related to orders.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CRUDService<OrderDTO, Pagination> service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public ResponseEntity<CollectionModel<OrderDTO>> getAll(
            @ModelAttribute() Pagination pagination
    ) {
        List<OrderDTO> orders = service.getAll(pagination);

        return ResponseEntity.ok(bindLinks(orders));
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id an integer representing the ID of the order
     * @return a ResponseEntity containing an OrderDTO object that matches the given ID
     */
    @GetMapping("/{id}")
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
    public ResponseEntity<OrderDTO> create(@RequestBody @Validated(OrderValidationGroup.class) OrderDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Set<String> errorMessages = new HashSet<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            String str = String.join(", ", errorMessages);

            logger.warn(str);
            throw new InvalidRequestBodyException(str);
        }

        BindingResult userBindingResult = new BeanPropertyBindingResult(orderDTO.getUser(), "user");

        if (userBindingResult.hasErrors()) {
            Set<String> errorMessages = new HashSet<>();
            for (ObjectError error : userBindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            String str = String.join(", ", errorMessages);

            logger.warn(str);
            throw new InvalidRequestBodyException(str);
        }

        int i = 1;
        for (GiftCertificateDTO certificate : orderDTO.getCertificates()) {
            BindingResult certificateBindingResult = new BeanPropertyBindingResult(certificate, "certificate" + i);

            if (certificateBindingResult.hasErrors()) {
                Set<String> errorMessages = new HashSet<>();
                for (ObjectError error : certificateBindingResult.getAllErrors()) {
                    errorMessages.add(error.getDefaultMessage());
                }
                String str = "Certificate #" + i + ": " + String.join(", ", errorMessages);

                logger.warn(str);
                throw new InvalidRequestBodyException(str);
            }

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
    public ResponseEntity<OrderDTO> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
