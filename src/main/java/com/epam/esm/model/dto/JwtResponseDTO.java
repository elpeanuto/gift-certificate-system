package com.epam.esm.model.dto;

import java.util.Objects;

public class JwtResponseDTO {

    private String access;
    private String refreshToken;

    public JwtResponseDTO() {

    }

    public JwtResponseDTO(String access, String refreshToken) {
        this.access = access;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return access;
    }

    public void setAccessToken(String access) {
        this.access = access;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JwtResponseDTO that = (JwtResponseDTO) o;

        if (!Objects.equals(access, that.access))
            return false;
        return Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        int result = access != null ? access.hashCode() : 0;
        result = 31 * result + (refreshToken != null ? refreshToken.hashCode() : 0);
        return result;
    }
}
