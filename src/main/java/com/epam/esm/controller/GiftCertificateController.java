package com.epam.esm.controller;

import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.model.modelImpl.GiftCertificate;
import com.epam.esm.service.CRUDService;
import com.epam.esm.service.serviceImpl.GiftCertificateServiceImpl;
import com.epam.esm.util.CreateValidationGroup;
import com.epam.esm.util.UpdateValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    private final Validator validator;
    private final CRUDService<GiftCertificate> service;

    @Autowired
    public GiftCertificateController(Validator validator, GiftCertificateServiceImpl service) {
        this.validator = validator;
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
    public GiftCertificate create(@RequestBody @Validated(CreateValidationGroup.class) GiftCertificate giftCertificate,
                                  BindingResult bindingResult) {
        validateGiftCertificate(giftCertificate, bindingResult);
        return service.create(giftCertificate);
    }

    @PatchMapping("/{id}")
    public GiftCertificate update(@PathVariable("id") int id,
                                  @RequestBody @Validated(UpdateValidationGroup.class) GiftCertificate giftCertificate,
                                  BindingResult bindingResult) {
        validateGiftCertificate(giftCertificate, bindingResult);
        return service.update(id, giftCertificate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }

    private void validateGiftCertificate(GiftCertificate giftCertificate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            throw new InvalidRequestBodyException(String.join(", ", errorMessages));
        }

        for (int i = 0; i < giftCertificate.getTags().size(); i++) {
            BindingResult tagBindingResult = new BeanPropertyBindingResult(giftCertificate.getTags().get(i), "tag" + i);
            validator.validate(giftCertificate.getTags().get(i), tagBindingResult);
            if (tagBindingResult.hasErrors()) {
                List<String> errorMessages = new ArrayList<>();
                for (ObjectError error : tagBindingResult.getAllErrors()) {
                    errorMessages.add(error.getDefaultMessage());
                }
                throw new InvalidRequestBodyException("Tag #" + (i + 1) + ": " + String.join(", ", errorMessages));
            }
        }
    }
}
