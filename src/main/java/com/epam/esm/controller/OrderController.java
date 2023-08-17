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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.epam.esm.model.hateoas.OrderLinker.bindLinks;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CRUDService<OrderDTO, Pagination> service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrderController(CRUDService<OrderDTO, Pagination> service) {
        this.service = service;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public ResponseEntity<CollectionModel<OrderDTO>> getAll(
            @ModelAttribute() Pagination pagination
    ) {
        List<OrderDTO> orders = service.getAll(pagination).getResponseList();

        return ResponseEntity.ok(bindLinks(orders));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public ResponseEntity<OrderDTO> getById(@PathVariable("id") long id) {
        OrderDTO order = service.getById(id);

        bindLinks(order);

        return ResponseEntity.ok(order);
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public ResponseEntity<OrderDTO> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
