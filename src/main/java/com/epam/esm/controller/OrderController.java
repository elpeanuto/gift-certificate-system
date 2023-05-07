package com.epam.esm.controller;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.service.services.api.CRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.model.hateoas.OrderLinker.bindLinks;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CRUDService<OrderDTO, Pagination> service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OrderController(CRUDService<OrderDTO, Pagination> service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<OrderDTO>> getAll(
            @ModelAttribute() Pagination pagination
    ) {
        List<OrderDTO> orders = service.getAll(pagination);

        return ResponseEntity.ok(bindLinks(orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable("id") long id) {
        OrderDTO order = service.getById(id);

        bindLinks(order);

        return ResponseEntity.ok(order);
    }

    @PostMapping()
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
        System.out.println(orderDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(orderDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDTO> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
