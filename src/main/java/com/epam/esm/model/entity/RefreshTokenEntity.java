package com.epam.esm.model.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import java.time.Instant;

@Audited
@Entity
@Table(name = "refresh_tokens")
public class RefreshTokenEntity {

    @Id
    @SequenceGenerator(
            name = "token_sequence",
            sequenceName = "token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "token_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private int id;

    @Column(
            name = "token"
    )
    private String token;

    @Column(
            name = "expirationDate"
    )
    private Instant expirationDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    public RefreshTokenEntity() {

    }

    public RefreshTokenEntity(int id, String token, Instant expirationDate, UserEntity user) {
        this.id = id;
        this.token = token;
        this.expirationDate = expirationDate;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "RefreshTokenEntity{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expirationDate=" + expirationDate +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefreshTokenEntity that = (RefreshTokenEntity) o;

        if (!token.equals(that.token)) return false;
        return expirationDate.equals(that.expirationDate);
    }

    @Override
    public int hashCode() {
        int result = token.hashCode();
        result = 31 * result + expirationDate.hashCode();
        return result;
    }
}
