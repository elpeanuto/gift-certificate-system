package com.epam.esm.controller;

import com.epam.esm.model.modelImpl.GiftCertificate;
import com.epam.esm.service.CRUDService;
import com.epam.esm.service.serviceImpl.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<GiftCertificate> index() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GiftCertificate show(@PathVariable("id") int id) {
        return service.getById(id);
    }

    @PostMapping()
    public GiftCertificate create(@RequestBody GiftCertificate giftCertificate) {
        service.create(giftCertificate);
        return giftCertificate;
    }

    @PutMapping("/{id}")
    public GiftCertificate update(@PathVariable("id") int id, @RequestBody GiftCertificate giftCertificate) {
        service.update(id, giftCertificate);
        return giftCertificate;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }
}
