package com.epam.esm.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "GiftCertificate")
@Table(name = "gift_certificate")
public class GiftCertificateEntity {

    @Id
    @SequenceGenerator(
            name = "certificate_sequence",
            sequenceName = "certificate_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "certificate_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "name"
    )
    private String name;

    @Column(
            name = "description",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String description;

    @Column(
            name = "price",
            columnDefinition = "NUMERIC(10, 2)",
            nullable = false
    )
    private Double price;

    @Column(
            name = "duration",
            nullable = false
    )
    private Integer duration;

    @Column(
            name = "create_date",
            columnDefinition = "TIMESTAMP",
            nullable = false
    )
    private LocalDateTime createDate;

    @Column(
            name = "last_update_date",
            columnDefinition = "TIMESTAMP",
            nullable = false
    )
    private LocalDateTime lastUpdateDate;

    @ManyToMany(
            mappedBy = "certificates",
            cascade = CascadeType.ALL
    )
    private Set<TagEntity> tags = new HashSet<>();

    public GiftCertificateEntity() {

    }

    public GiftCertificateEntity(Long id, String name, String description, Double price, Integer duration,
                                 LocalDateTime createDate, Set<TagEntity> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = LocalDateTime.now();
        this.tags = tags;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "GiftCertificateEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tags=" + tags +
                '}';
    }
}
