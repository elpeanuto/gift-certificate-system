package com.epam.esm.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "certificate_id", nullable = false)
    private GiftCertificateEntity certificate;

    @Column(
            name = "create_date",
            nullable = false
    )
    private LocalDateTime createDate;

    @Column(
            name = "price",
            columnDefinition = "NUMERIC(10, 2)",
            nullable = false
    )
    private Double price;

    public OrderEntity(){

    }

    public OrderEntity(Long id, UserEntity user, LocalDateTime createDate, Double price) {
        this.id = id;
        this.user = user;
        this.createDate = createDate;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
