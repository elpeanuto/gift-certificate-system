package com.epam.esm.controller;

import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.service.api.GiftCertificateService;
import com.epam.esm.util.CreateValidationGroup;
import com.epam.esm.util.UpdateValidationGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * A RestController class that handles API requests related to gift certificates.
 */
@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    private final Validator validator;
    private final GiftCertificateService<GiftCertificate> service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor for GiftCertificateController class.
     *
     * @param validator a Validator object used to validate gift certificate data
     * @param service   a GiftCertificateService object used to interact with gift certificate data in the database
     */
    @Autowired
    public GiftCertificateController(Validator validator, GiftCertificateService<GiftCertificate> service) {
        this.validator = validator;
        this.service = service;
    }

    /**
     * Retrieves all gift certificates or gift certificates matching the provided parameters.
     *
     * @param tagName a String object representing the name of a tag
     * @param part    a String object representing a part of the gift certificate name or description
     * @param sort    a String object representing the sorting order for the gift certificates
     * @return a List of GiftCertificate objects that match the given parameters or all gift certificates if no parameters provided
     */
    @GetMapping()
    public List<GiftCertificate> getAll(@RequestParam(required = false) String tagName,
                                        @RequestParam(required = false) String part,
                                        @RequestParam(required = false) String sort) {
        if (tagName != null || part != null || sort != null)
            return service.getByParams(tagName, part, sort);

        return service.getAll();
    }

    /**
     * Retrieves a gift certificate by its ID.
     *
     * @param id an integer representing the ID of the gift certificate
     * @return a GiftCertificate object that matches the given ID
     */
    @GetMapping("/{id}")
    public GiftCertificate getById(@PathVariable("id") int id) {
        return service.getById(id);
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
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificate create(@RequestBody @Validated(CreateValidationGroup.class) GiftCertificate certificate,
                                  BindingResult bindingResult) {
        validateGiftCertificate(certificate, bindingResult);
        return service.create(certificate);
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
    public GiftCertificate update(@PathVariable("id") int id,
                                  @RequestBody @Validated(UpdateValidationGroup.class) GiftCertificate certificate,
                                  BindingResult bindingResult) {
        validateGiftCertificate(certificate, bindingResult);
        return service.update(id, certificate);
    }

    /**
     * Deletes a gift certificate with the provided ID.
     *
     * @param id an integer representing the ID of the gift certificate to be deleted
     * @return a GiftCertificate object that represents the deleted gift certificate
     */
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
