package com.epam.esm.controller;

import com.epam.esm.model.modelImpl.GiftCertificate;
import com.epam.esm.service.CRUDService;
import com.epam.esm.service.serviceImpl.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    private final CRUDService<GiftCertificate> service;

    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping()
    public List<GiftCertificate> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GiftCertificate getById(@PathVariable("id") int id) {
        return service.getById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificate create(@RequestBody GiftCertificate giftCertificate) {
        return service.create(giftCertificate);
    }

    @PatchMapping("/{id}")
    public GiftCertificate update(@PathVariable("id") int id, @RequestBody GiftCertificate giftCertificate) {
        return service.update(id, giftCertificate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }
}
