package com.epam.esm.controller;

import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.model.dto.RoleDTO;
import com.epam.esm.model.dto.RoleDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.service.services.api.CRDService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.model.hateoas.RoleLinker.bindLinks;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final CRDService<RoleDTO, Pagination> service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RoleController(CRDService<RoleDTO, Pagination> service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<RoleDTO>> getAll(
            @ModelAttribute() Pagination pagination
    ) {
        List<RoleDTO> tags = service.getAll(pagination);

        return ResponseEntity.ok(bindLinks(tags));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getById(@PathVariable("id") long id) {
        RoleDTO tag = service.getById(id);

        bindLinks(tag);

        return ResponseEntity.ok(tag);
    }


    @PostMapping()
    public ResponseEntity<RoleDTO> create(@RequestBody @Valid RoleDTO tagDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            String str = String.join(", ", errorMessages);

            logger.warn(str);
            throw new InvalidRequestBodyException(str);
        }

        RoleDTO tag = service.create(tagDTO);

        bindLinks(tag);

        return ResponseEntity.status(HttpStatus.CREATED).body(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoleDTO> delete(@PathVariable("id") long id) {
        RoleDTO tag = service.delete(id);

        return ResponseEntity.ok(tag);
    }
}
