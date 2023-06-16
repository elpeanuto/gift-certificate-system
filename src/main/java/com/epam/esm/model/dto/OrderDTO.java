package com.epam.esm.model.dto;

import com.epam.esm.controller.util.OrderValidationGroup;
import com.epam.esm.model.dto.api.DTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderDTO orderDTO = (OrderDTO) o;

        if (!Objects.equals(createDate, orderDTO.createDate)) return false;
        return Objects.equals(totalPrice, orderDTO.totalPrice);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        return result;
    }
}
