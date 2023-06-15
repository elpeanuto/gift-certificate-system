package com.epam.esm.model.hateoas;

import com.epam.esm.controller.RoleController;
import com.epam.esm.model.dto.RoleDTO;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class RoleLinker {

    private RoleLinker() {

    }

    public static void bindLinks(RoleDTO tag) {
        tag.add(linkTo(methodOn(RoleController.class).getById(tag.getId())).withSelfRel());
        tag.add(linkTo(methodOn(RoleController.class).delete(tag.getId())).withRel("delete"));
    }

    public static CollectionModel<RoleDTO> bindLinks(List<RoleDTO> tags) {
        tags.forEach(RoleLinker::bindLinks);

        return CollectionModel.of(tags,
                linkTo(methodOn(RoleController.class).getAll(null)).withSelfRel());
    }
}
