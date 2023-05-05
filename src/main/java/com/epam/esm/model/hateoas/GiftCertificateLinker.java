package com.epam.esm.model.hateoas;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.model.dto.GiftCertificateDTO;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class GiftCertificateLinker {

    private GiftCertificateLinker() {
    }

    public static void bindLinks(GiftCertificateDTO certificate) {
        certificate.add(linkTo(methodOn(GiftCertificateController.class).getById(certificate.getId())).withSelfRel());
        certificate.add(linkTo(methodOn(GiftCertificateController.class).update(certificate.getId(), null, null)).withRel("update"));
        certificate.add(linkTo(methodOn(GiftCertificateController.class).delete(certificate.getId())).withRel("delete"));
        certificate.add(linkTo(methodOn(GiftCertificateController.class).getAll(null)).withRel("tags"));
    }

    public static CollectionModel<GiftCertificateDTO> bindLinks(List<GiftCertificateDTO> certificates) {
        certificates.forEach(certificate -> {
            certificate.getTags().forEach(TagLinker::bindLinks);
            bindLinks(certificate);
        });

        return CollectionModel.of(certificates,
                linkTo(methodOn(GiftCertificateController.class).getAll(null)).withSelfRel());
    }
}
