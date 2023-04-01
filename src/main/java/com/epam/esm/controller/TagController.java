package com.epam.esm.controller;

import com.epam.esm.model.modelImpl.Tag;
import com.epam.esm.service.CRDService;
import com.epam.esm.service.serviceImpl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final CRDService<Tag> service;

    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Tag> index() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Tag show(@PathVariable("id") int id) {
        return service.getById(id);
    }

    @PostMapping()
    public Tag create(@RequestBody Tag tag) {
        service.create(tag);
        return tag;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }
}
