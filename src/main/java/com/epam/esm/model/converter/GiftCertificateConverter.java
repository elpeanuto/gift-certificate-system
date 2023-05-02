package com.epam.esm.model.converter;

import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.entity.GiftCertificateEntity;

import java.util.stream.Collectors;

public class GiftCertificateConverter {

    private GiftCertificateConverter() {

    }

    public static GiftCertificateDTO toDto(GiftCertificateEntity entity) {
        return new GiftCertificateDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getDuration(),
                entity.getCreateDate(),
                entity.getLastUpdateDate(),
                entity.getTags().stream()
                        .map(TagConverter::toDto)
                        .collect(Collectors.toSet())
        );
    }

    public static GiftCertificateEntity toEntity(GiftCertificateDTO dto) {
        return new GiftCertificateEntity(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getDuration(),
                dto.getCreateDate(),
                dto.getLastUpdateDate(),
                dto.getTags().stream()
                        .map(TagConverter::toEntity)
                        .collect(Collectors.toSet())
        );
    }
}
