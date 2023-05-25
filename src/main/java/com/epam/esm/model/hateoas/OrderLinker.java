package com.epam.esm.model.hateoas;

import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.UserOrderDTO;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class OrderLinker {

    private OrderLinker() {
    }

    public static void bindLinks(OrderDTO order) {
        UserLinker.bindLinks(order.getUser());
        GiftCertificateLinker.bindLinks(order.getCertificates());

        order.add(linkTo(methodOn(OrderController.class).getById(order.getId())).withSelfRel());
        order.add(linkTo(methodOn(OrderController.class).getAll(null)).withRel("tags"));
    }

    public static CollectionModel<OrderDTO> bindLinks(List<OrderDTO> orders) {
        orders.forEach(OrderLinker::bindLinks);

        return CollectionModel.of(orders,
                linkTo(methodOn(TagController.class).getAll(null)).withSelfRel());
    }

    public static void bindLinks(UserOrderDTO order) {
        order.add(linkTo(methodOn(OrderController.class).getById(order.getOrderId())).withSelfRel());
        order.add(linkTo(methodOn(UserController.class).getById(order.getUserId())).withRel("user"));
    }
}
