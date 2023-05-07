package com.epam.esm.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany
    @JoinColumn(name = "certificate_id", nullable = false)
    private List<GiftCertificateEntity> certificates = new ArrayList<>();

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

    public OrderEntity(Long id, UserEntity user, List<GiftCertificateEntity> certificates,
                       LocalDateTime createDate, Double price) {
        this.id = id;
        this.user = user;
        this.certificates = certificates;
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

    public List<GiftCertificateEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<GiftCertificateEntity> certificates) {
        this.certificates = certificates;
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
