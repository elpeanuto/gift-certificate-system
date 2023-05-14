package com.epam.esm.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )
    private Long id;

    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToMany(
            cascade = CascadeType.MERGE
    )
    @JoinTable(
            name = "order_gift_certificate",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id")
    )
    private Set<GiftCertificateEntity> certificates = new HashSet<>();

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

    public OrderEntity() {

    }

    public OrderEntity(Long id, UserEntity user, Set<GiftCertificateEntity> certificates,
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

    public Set<GiftCertificateEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(Set<GiftCertificateEntity> certificates) {
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
