package com.epam.esm.controller;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.dto.filter.TagFilter;
import com.epam.esm.service.services.api.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.model.hateoas.UserLinker.bindLinks;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CRUDService<UserDTO, Pagination> service;

    @Autowired
    public UserController(CRUDService<UserDTO, Pagination> service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<UserDTO>> getAll(
            @ModelAttribute() TagFilter tagFilter
    ) {
        List<UserDTO> users = service.getAll(tagFilter);

        return ResponseEntity.ok(bindLinks(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") long id) {
        UserDTO user = service.getById(id);

        bindLinks(user);

        return ResponseEntity.ok(user);
    }
}
