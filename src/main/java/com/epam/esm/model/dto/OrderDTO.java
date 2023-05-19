package com.epam.esm.model.dto;

import com.epam.esm.controller.util.OrderValidationGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO extends RepresentationModel<OrderDTO> implements DTO {

    private Long id;

    @NotNull(message = "User is missing", groups = OrderValidationGroup.class)
    @Valid
    private UserDTO user;

    @NotNull(message = "Certificates are missing", groups = OrderValidationGroup.class)
    @NotEmpty(message = "Certificates cant be empty", groups = OrderValidationGroup.class)
    @Valid
    private List<GiftCertificateDTO> certificates;

    private LocalDateTime createDate;
    private Double totalPrice;

    public OrderDTO(Long id, UserDTO user, List<GiftCertificateDTO> certificates, LocalDateTime createDate, Double totalPrice) {
        this.id = id;
        this.user = user;
        this.certificates = certificates;
        this.createDate = createDate;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<GiftCertificateDTO> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<GiftCertificateDTO> certificates) {
        this.certificates = certificates;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", user=" + user +
                ", certificate=" + certificates +
                ", createDate=" + createDate +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
