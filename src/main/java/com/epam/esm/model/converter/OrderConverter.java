package com.epam.esm.model.converter;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.UserOrderDTO;
import com.epam.esm.model.entity.OrderEntity;

import java.util.stream.Collectors;

public class OrderConverter {

    private OrderConverter() {

    }

    public static OrderDTO toDto(OrderEntity entity) {
        return new OrderDTO(
                entity.getId(),
                UserConverter.toDto(entity.getUser()),
                entity.getCertificates().stream()
                        .map(GiftCertificateConverter::toDto)
                        .toList(),
                entity.getCreateDate(),
                entity.getPrice()
        );
    }

    public static OrderEntity toEntity(OrderDTO dto) {
        return new OrderEntity(
                dto.getId(),
                UserConverter.toEntity(dto.getUser()),
                dto.getCertificates().stream()
                        .map(GiftCertificateConverter::toEntity)
                        .collect(Collectors.toSet()),
                dto.getCreateDate(),
                dto.getTotalPrice()
        );
    }

    public static UserOrderDTO orderToUserOrder(OrderEntity entity) {
        return new UserOrderDTO(
                entity.getId(),
                entity.getUser().getId(),
                entity.getCreateDate(),
                entity.getPrice()
        );
    }
}
