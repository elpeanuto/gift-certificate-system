package com.epam.esm.controller;

import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.service.api.GiftCertificateService;
import com.epam.esm.util.CreateValidationGroup;
import com.epam.esm.util.UpdateValidationGroup;
import org.apache.log4j.Logger;
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
    private final GiftCertificateService<GiftCertificate> service;
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    public GiftCertificateController(Validator validator, GiftCertificateService<GiftCertificate> service) {
        this.validator = validator;
        this.service = service;
    }

    @GetMapping()
    public List<GiftCertificate> getAll(@RequestParam(required = false) String tagName,
                                        @RequestParam(required = false) String part,
                                        @RequestParam(required = false) String sort) {
        if (tagName != null || part != null || sort != null)
            return service.getByParams(tagName, part, sort);

        return service.getAll();
    }

    @GetMapping("/{id}")
    public GiftCertificate getById(@PathVariable("id") int id) {
        return service.getById(id);
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificate create(@RequestBody @Validated(CreateValidationGroup.class) GiftCertificate certificate,
                                  BindingResult bindingResult) {
        validateGiftCertificate(certificate, bindingResult);
        return service.create(certificate);
    }

    @PatchMapping("/{id}")
    public GiftCertificate update(@PathVariable("id") int id,
                                  @RequestBody @Validated(UpdateValidationGroup.class) GiftCertificate certificate,
                                  BindingResult bindingResult) {
        validateGiftCertificate(certificate, bindingResult);
        return service.update(id, certificate);
    }

    @DeleteMapping("/{id}")
    public GiftCertificate delete(@PathVariable("id") int id) {
        return service.delete(id);
    }

    private void validateGiftCertificate(GiftCertificate certificate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            String str = String.join(", ", errorMessages);

            logger.warn(str);
            throw new InvalidRequestBodyException(str);
        }

        for (int i = 0; i < certificate.getTags().size(); i++) {
            BindingResult tagBindingResult = new BeanPropertyBindingResult(certificate.getTags().get(i), "tag" + i);
            validator.validate(certificate.getTags().get(i), tagBindingResult);
            if (tagBindingResult.hasErrors()) {
                List<String> errorMessages = new ArrayList<>();
                for (ObjectError error : tagBindingResult.getAllErrors()) {
                    errorMessages.add(error.getDefaultMessage());
                }
                String str = "Tag #" + (i + 1) + ": " + String.join(", ", errorMessages);

                logger.warn(str);
                throw new InvalidRequestBodyException(str);
            }
        }
    }
}
