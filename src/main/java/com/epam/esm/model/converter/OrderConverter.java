package com.epam.esm.model.converter;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.entity.OrderEntity;

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
                dto.getCertificate().stream()
                        .map(GiftCertificateConverter::toEntity)
                        .toList(),
                dto.getCreateDate(),
                dto.getTotalPrice()
        );
    }
}
