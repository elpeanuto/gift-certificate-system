package com.epam.esm.controller;

import com.epam.esm.controller.util.CreateValidationGroup;
import com.epam.esm.controller.util.UpdateValidationGroup;
import com.epam.esm.exception.exceptions.InvalidRequestBodyException;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.PaginatedResponse;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.filter.GiftCertificateFilter;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.service.services.api.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.controller.util.Util.bindingResultCheck;
import static com.epam.esm.model.hateoas.GiftCertificateLinker.bindLinks;

/**
 * A RestController class that handles API requests related to gift certificates.
 */
@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    private final Validator validator;
    private final GiftCertificateService service;

    /**
     * Constructor for GiftCertificateController class.
     *
     * @param validator a Validator object used to validate gift certificate data
     * @param service   a GiftCertificateService object used to interact with gift certificate data in the database
     */
    @Autowired
    public GiftCertificateController(Validator validator, GiftCertificateService service) {
        this.validator = validator;
        this.service = service;
    }

    /**
     * Retrieves all gift certificates.
     *
     * @param pagination a Pagination object used for pagination of the results
     * @return a ResponseEntity containing a CollectionModel of GiftCertificateDTO objects
     */
    @GetMapping()
    public ResponseEntity<Map<String, Object>> getAll(
            @ModelAttribute Pagination pagination
    ) {
        PaginatedResponse<GiftCertificateDTO> all = service.getAll(pagination);
        List<GiftCertificateDTO> list = all.getResponseList();

        Map<String, Object> response = new HashMap<>();
        response.put("total", all.getTotalCount());
        response.put("giftCertificates", bindLinks(list));

        return ResponseEntity.ok(response);
    }

    /**
     * Searches for gift certificates based on the provided filter.
     *
     * @param giftCertificateFilter a GiftCertificateFilter object used to filter the search results
     * @return a ResponseEntity containing a CollectionModel of GiftCertificateDTO objects
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            @ModelAttribute GiftCertificateFilter giftCertificateFilter
    ) {
        PaginatedResponse<GiftCertificateDTO> all = service.doSearch(giftCertificateFilter);

        List<GiftCertificateDTO> list = all.getResponseList();

        Map<String, Object> response = new HashMap<>();
        response.put("total", all.getTotalCount());
        response.put("giftCertificates", bindLinks(list));

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a gift certificate by its ID.
     *
     * @param id an integer representing the ID of the gift certificate
     * @return a GiftCertificate object that matches the given ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDTO> getById(@PathVariable("id") long id) {
        GiftCertificateDTO certificate = service.getById(id);

        bindLinks(certificate);

        return ResponseEntity.ok(certificate);
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
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public ResponseEntity<GiftCertificateDTO> create(@RequestBody @Validated(CreateValidationGroup.class) GiftCertificateDTO certificate,
                                                     BindingResult bindingResult) {
        validateGiftCertificate(certificate, bindingResult);

        GiftCertificateDTO body = service.create(certificate);

        bindLinks(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
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
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public ResponseEntity<GiftCertificateDTO> update(@PathVariable("id") long id,
                                                     @RequestBody @Validated(UpdateValidationGroup.class) GiftCertificateDTO certificate,
                                                     BindingResult bindingResult) {
        validateGiftCertificate(certificate, bindingResult);

        GiftCertificateDTO update = service.update(id, certificate);

        bindLinks(update);

        return ResponseEntity.ok(update);
    }

    /**
     * Deletes a gift certificate with the provided ID.
     *
     * @param id an integer representing the ID of the gift certificate to be deleted
     * @return a GiftCertificate object that represents the deleted gift certificate
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public ResponseEntity<GiftCertificateDTO> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    private void validateGiftCertificate(GiftCertificateDTO certificate, BindingResult bindingResult) {
        bindingResultCheck(bindingResult);

        int i = 1;
        for (TagDTO tag : certificate.getTags()) {
            BindingResult tagBindingResult = new BeanPropertyBindingResult(tag, "tag" + i);
            validator.validate(tag, tagBindingResult);

            bindingResultCheck(tagBindingResult);

            i++;
        }
    }
}
