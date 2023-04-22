package com.epam.esm.controller;

import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.service.api.CRDService;
import com.epam.esm.service.impl.TagServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A RestController class that handles API requests related to tags.
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    private final CRDService<Tag> service;
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
    public List<Tag> getAll() {
        return service.getAll();
    }

    /**
     * Returns the Tag object with the specified id.
     *
     * @param id the id of the Tag object to retrieve
     * @return the Tag object with the specified id
     */
    @GetMapping("/{id}")
    public Tag getById(@PathVariable("id") int id) {
        return service.getById(id);
    }

    /**
     * Creates a new Tag object.
     *
     * @param tag           the Tag object to create
     * @param bindingResult the BindingResult object used to check for errors in the request body
     * @return the created Tag object
     * @throws InvalidRequestBodyException if there are errors in the request body
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Tag create(@RequestBody @Valid Tag tag, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            String str = String.join(", ", errorMessages);

            logger.warn(str);
            throw new InvalidRequestBodyException();
        }

        service.create(tag);
        return tag;
    }

    /**
     * Deletes the Tag object with the specified id.
     *
     * @param id the id of the Tag object to delete
     * @return the deleted Tag object
     */
    @DeleteMapping("/{id}")
    public Tag delete(@PathVariable("id") int id) {
        return service.delete(id);
    }
}
