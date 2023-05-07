package com.epam.esm.controller;

import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.dto.filter.TagFilter;
import com.epam.esm.service.services.api.CRDService;
import com.epam.esm.service.services.impl.TagServiceImpl;
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

import static com.epam.esm.model.hateoas.TagLinker.bindLinks;

/**
 * A RestController class that handles API requests related to tags.
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    private final CRDService<TagDTO, Pagination> service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructs an instance of TagController with the specified service.
     *
     * @param service the service used to perform CRUD operations on Tag objects
     */
    @Autowired
    public TagController(TagServiceImpl service) {
        this.service = service;
    }

    /**
     * Returns a list of all Tag objects in the system.
     *
     * @return a list of all Tag objects
     */
    @GetMapping()
    public ResponseEntity<CollectionModel<TagDTO>> getAll(
            @ModelAttribute() TagFilter tagFilter
    ) {
        List<TagDTO> tags = service.getAll(tagFilter);

        return ResponseEntity.ok(bindLinks(tags));
    }

    /**
     * Returns the Tag object with the specified id.
     *
     * @param id the id of the Tag object to retrieve
     * @return the Tag object with the specified id
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getById(@PathVariable("id") long id) {
        TagDTO tag = service.getById(id);

        bindLinks(tag);

        return ResponseEntity.ok(tag);
    }

    /**
     * Creates a new Tag object.
     *
     * @param tagDTO        the Tag object to create
     * @param bindingResult the BindingResult object used to check for errors in the request body
     * @return the created Tag object
     * @throws InvalidRequestBodyException if there are errors in the request body
     */
    @PostMapping()
    public ResponseEntity<TagDTO> create(@RequestBody @Valid TagDTO tagDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            String str = String.join(", ", errorMessages);

            logger.warn(str);
            throw new InvalidRequestBodyException(str);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(tagDTO));
    }

    /**
     * Deletes the Tag object with the specified id.
     *
     * @param id the id of the Tag object to delete
     * @return the deleted Tag object
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<TagDTO> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
