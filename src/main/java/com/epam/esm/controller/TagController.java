package com.epam.esm.controller;

import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.service.CRDService;
import com.epam.esm.service.impl.TagServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final CRDService<Tag> service;

    @Autowired
    public TagController(TagServiceImpl service) {
        this.service = service;
    }

    @GetMapping()
    public List<Tag> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Tag getById(@PathVariable("id") int id) {
        return service.getById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Tag create(@RequestBody @Valid Tag tag, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            throw new InvalidRequestBodyException(String.join(", ", errorMessages));
        }

        service.create(tag);
        return tag;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }
}
