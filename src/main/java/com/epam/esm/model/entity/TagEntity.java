package com.epam.esm.model.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Audited
@Entity
@Table(
        name = "tag",
        uniqueConstraints = {
                @UniqueConstraint(name = "tag_name", columnNames = "name")
        }
)
public class TagEntity {

    @Id
    @SequenceGenerator(
            name = "tag_sequence",
            sequenceName = "tag_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tag_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<GiftCertificateEntity> certificates = new HashSet<>();

    public TagEntity() {

    }

    public TagEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TagEntity(Long id, String name, Set<GiftCertificateEntity> certificates) {
        this.id = id;
        this.name = name;
        this.certificates = certificates;
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

    public Set<GiftCertificateEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(Set<GiftCertificateEntity> certificates) {
        this.certificates = certificates;
    }

    @Override
    public String toString() {
        return "TagEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", certificates=" + certificates +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagEntity entity = (TagEntity) o;

        return Objects.equals(name, entity.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
