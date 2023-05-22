package com.epam.esm.model.hateoas;

import com.epam.esm.controller.TagController;
import com.epam.esm.model.dto.TagDTO;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
