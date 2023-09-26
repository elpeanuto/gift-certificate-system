package com.epam.esm.model.hateoas;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.model.dto.GiftCertificateDTO;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The GiftCertificateLinker class provides static methods for adding HATEOAS links to GiftCertificateDTO objects
 * and generating a collection of linked gift certificates. It is used to enrich gift certificate DTOs with
 * self-links, update links, delete links, and links for associated tags.
 */
public class GiftCertificateLinker {

    private GiftCertificateLinker() {
    }

    public static void bindLinks(GiftCertificateDTO certificate) {
        certificate.add(linkTo(methodOn(GiftCertificateController.class).getById(certificate.getId())).withSelfRel());
        certificate.add(linkTo(methodOn(GiftCertificateController.class).update(certificate.getId(), null, null)).withRel("update"));
        certificate.add(linkTo(methodOn(GiftCertificateController.class).delete(certificate.getId())).withRel("delete"));
        certificate.getTags().forEach(TagLinker::bindLinks);
    }

    public static CollectionModel<GiftCertificateDTO> bindLinks(List<GiftCertificateDTO> certificates) {
        certificates.forEach(GiftCertificateLinker::bindLinks);

        return CollectionModel.of(certificates,
                linkTo(methodOn(GiftCertificateController.class).getAll(null)).withSelfRel());
    }
}
