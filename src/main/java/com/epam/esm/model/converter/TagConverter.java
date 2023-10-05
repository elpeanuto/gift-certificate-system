package com.epam.esm.model.converter;

import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.entity.TagEntity;

/**
 * The TagConverter class provides static methods for converting between
 * TagEntity and TagDTO objects.
 */
public class TagConverter {

    private TagConverter() {

    }

    public static TagDTO toDto(TagEntity entity) {
        return new TagDTO(entity.getId(), entity.getName());
    }

    public static TagEntity toEntity(TagDTO dto) {
        return new TagEntity(dto.getId(), dto.getName());
    }
}
