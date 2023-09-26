package com.epam.esm.model.hateoas;

import com.epam.esm.controller.TagController;
import com.epam.esm.model.dto.TagDTO;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The TagLinker class provides static methods for adding HATEOAS links to TagDTO objects and generating a
 * collection of linked tag DTOs. It is used to enrich tag DTOs with self-links and delete links.
 */
public class TagLinker {

    private TagLinker() {
    }

    public static void bindLinks(TagDTO tag) {
        tag.add(linkTo(methodOn(TagController.class).getById(tag.getId())).withSelfRel());
        tag.add(linkTo(methodOn(TagController.class).delete(tag.getId())).withRel("delete"));
    }

    public static CollectionModel<TagDTO> bindLinks(List<TagDTO> tags) {
        tags.forEach(TagLinker::bindLinks);

        return CollectionModel.of(tags,
                linkTo(methodOn(TagController.class).getAll(null)).withSelfRel());
    }
}
