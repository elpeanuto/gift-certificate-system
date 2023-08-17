package com.epam.esm.model.dto;

import java.util.Objects;

public class JwtResponseDTO {

    private String accessesToken;
    private String refreshToken;

    public JwtResponseDTO() {

    }

    public JwtResponseDTO(String accessesToken, String refreshToken) {
        this.accessesToken = accessesToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessesToken() {
        return accessesToken;
    }

    public void setAccessesToken(String accessesToken) {
        this.accessesToken = accessesToken;
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

        if (!Objects.equals(accessesToken, that.accessesToken))
            return false;
        return Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        int result = accessesToken != null ? accessesToken.hashCode() : 0;
        result = 31 * result + (refreshToken != null ? refreshToken.hashCode() : 0);
        return result;
    }
}
