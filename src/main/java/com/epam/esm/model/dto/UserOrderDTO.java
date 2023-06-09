package com.epam.esm.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserOrderDTO extends RepresentationModel<UserOrderDTO> {

    @JsonIgnore
    private Long orderId;

    @JsonIgnore
    private Long userId;
    private LocalDateTime createDate;
    private Double totalPrice;

    public UserOrderDTO(Long orderId, Long userId, LocalDateTime createDate, Double totalPrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.createDate = createDate;
        this.totalPrice = totalPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        return "UserOrderDTO{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", createDate=" + createDate +
                ", totalPrice=" + totalPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserOrderDTO that = (UserOrderDTO) o;

        if (!Objects.equals(createDate, that.createDate)) return false;
        return Objects.equals(totalPrice, that.totalPrice);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        return result;
    }
}
