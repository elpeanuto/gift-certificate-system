package com.epam.esm.model.dto;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO extends RepresentationModel<OrderDTO> implements DTO {

    private Long id;
    private UserDTO user;
    private List<GiftCertificateDTO> certificate;
    private LocalDateTime createDate;
    private Double totalPrice;

    public OrderDTO(Long id, UserDTO user, List<GiftCertificateDTO> certificate, LocalDateTime createDate, Double totalPrice) {
        this.id = id;
        this.user = user;
        this.certificate = certificate;
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

    public List<GiftCertificateDTO> getCertificate() {
        return certificate;
    }

    public void setCertificate(List<GiftCertificateDTO> certificate) {
        this.certificate = certificate;
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
                ", certificate=" + certificate +
                ", createDate=" + createDate +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
