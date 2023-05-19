package com.epam.esm.model.dto;

import com.epam.esm.controller.util.OrderValidationGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class UserDTO extends RepresentationModel<UserDTO> implements DTO {

    @NotNull(message = "Id is missing", groups = OrderValidationGroup.class)
    private Long id;

    @Size(min = 2, max = 30, message = "firstName should be between 2 and 30 characters")
    @Pattern(regexp = "\\p{L}+", message = "First name should contain only characters")
    private String firstName;

    @Size(min = 2, max = 30, message = "lastName should be between 2 and 30 characters")
    @Pattern(regexp = "\\p{L}+", message = "Last name should contain only characters")
    private String lastName;

    @Email
    @NotNull(message = "Email is missing")
    @Size(min = 2, max = 30, message = "Email should be between 2 and 30 characters")
    private String email;

    @JsonIgnore
    @NotNull(message = "Password is missing")
    @Size(min = 2, max = 30, message = "Password should be between 2 and 30 characters")
    private String password;

    public UserDTO(Long id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserDTO userDTO = (UserDTO) o;

        return Objects.equals(email, userDTO.email);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
