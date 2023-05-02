package com.epam.esm.controller;

import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.filtering.Pagination;
import com.epam.esm.service.api.GiftCertificateService;
import com.epam.esm.util.CreateValidationGroup;
import com.epam.esm.util.UpdateValidationGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A RestController class that handles API requests related to gift certificates.
 */
@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    private final Validator validator;
    private final GiftCertificateService<GiftCertificateDTO> service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor for GiftCertificateController class.
     *
     * @param validator a Validator object used to validate gift certificate data
     * @param service   a GiftCertificateService object used to interact with gift certificate data in the database
     */
    @Autowired
    public GiftCertificateController(Validator validator, GiftCertificateService<GiftCertificateDTO> service) {
        this.validator = validator;
        this.service = service;
    }

    //todo filtering from module 2
    @GetMapping()
    public ResponseEntity<List<GiftCertificateDTO>> getAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "limit", defaultValue = "5", required = false) Integer limit
    ) {
        Pagination pagination = new Pagination(page, limit);

        return ResponseEntity.ok(service.getAll(pagination));
    }

    /**
     * Retrieves a gift certificate by its ID.
     *
     * @param id an integer representing the ID of the gift certificate
     * @return a GiftCertificate object that matches the given ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDTO> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    /**
     * Creates a new gift certificate with the provided data.
     *
     * @param certificate   a GiftCertificate object representing the new gift certificate to be created
     * @param bindingResult a BindingResult object that holds the result of the validation process
     * @return a GiftCertificate object that represents the newly created gift certificate
     * @throws InvalidRequestBodyException if the provided data is not valid
     */
    @PostMapping()
    public ResponseEntity<GiftCertificateDTO> create(@RequestBody @Validated(CreateValidationGroup.class) GiftCertificateDTO certificate,
                                                     BindingResult bindingResult) {
        validateGiftCertificate(certificate, bindingResult);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(certificate));
    }

    /**
     * Updates an existing gift certificate with the provided data.
     *
     * @param id            an integer representing the ID of the gift certificate to be updated
     * @param certificate   a GiftCertificate object representing the updated gift certificate data
     * @param bindingResult a BindingResult object that holds the result of the validation process
     * @return a GiftCertificate object that represents the updated gift certificate
     * @throws InvalidRequestBodyException if the provided data is not valid
     */
    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateDTO> update(@PathVariable("id") int id,
                                                     @RequestBody @Validated(UpdateValidationGroup.class) GiftCertificateDTO certificate,
                                                     BindingResult bindingResult) {
        validateGiftCertificate(certificate, bindingResult);

        return ResponseEntity.ok(service.update(id, certificate));
    }

    /**
     * Deletes a gift certificate with the provided ID.
     *
     * @param id an integer representing the ID of the gift certificate to be deleted
     * @return a GiftCertificate object that represents the deleted gift certificate
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<GiftCertificateDTO> delete(@PathVariable("id") int id) {
        return ResponseEntity.ok(service.delete(id));
    }

    private void validateGiftCertificate(GiftCertificateDTO certificate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Set<String> errorMessages = new HashSet<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            String str = String.join(", ", errorMessages);

            logger.warn(str);
            throw new InvalidRequestBodyException(str);
        }

        int i = 1;
        for (TagDTO tag : certificate.getTags()) {
            BindingResult tagBindingResult = new BeanPropertyBindingResult(tag, "tag" + i);
            validator.validate(tag, tagBindingResult);

            if (tagBindingResult.hasErrors()) {
                Set<String> errorMessages = new HashSet<>();
                for (ObjectError error : tagBindingResult.getAllErrors()) {
                    errorMessages.add(error.getDefaultMessage());
                }
                String str = "Tag #" + i + ": " + String.join(", ", errorMessages);

                logger.warn(str);
                throw new InvalidRequestBodyException(str);
            }

            i++;
        }
    }
}
