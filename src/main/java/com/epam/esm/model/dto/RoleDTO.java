package com.epam.esm.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

public class RoleDTO extends RepresentationModel<RoleDTO> {

    private Long id;

    @NotNull(message = "Name is missing")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    public RoleDTO() {

    }

    public RoleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
