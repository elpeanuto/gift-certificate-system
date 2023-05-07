package com.epam.esm.model.hateoas;

import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.model.dto.UserDTO;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class UserLinker {

    private UserLinker() {
    }

    public static void bindLinks(UserDTO user) {
        user.add(linkTo(methodOn(UserController.class).getById(user.getId())).withSelfRel());
        user.add(linkTo(methodOn(UserController.class).getAll(null)).withRel("tags"));
    }

    public static CollectionModel<UserDTO> bindLinks(List<UserDTO> users) {
        users.forEach(user -> {
            user.add(linkTo(methodOn(UserController.class).getById(user.getId())).withSelfRel());
            user.add(linkTo(methodOn(UserController.class).getAll(null)).withRel("tags"));
        });

        return CollectionModel.of(users,
                linkTo(methodOn(TagController.class).getAll(null)).withSelfRel());
    }
}
