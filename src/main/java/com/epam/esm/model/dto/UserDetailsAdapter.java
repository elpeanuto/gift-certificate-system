package com.epam.esm.model.dto;

import com.epam.esm.model.constant.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsAdapter extends UserDTO implements UserDetails {

    public UserDetailsAdapter() {
    }

    public UserDetailsAdapter(Long id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName, email, password);
    }

    public UserDetailsAdapter(Long id, String firstName, String lastName, String email, String password, UserRole role) {
        super(id, firstName, lastName, email, password, role);
    }

    public UserDetailsAdapter(UserDTO userDTO) {
        super.id = userDTO.id;
        super.firstName = userDTO.firstName;
        super.lastName = userDTO.lastName;
        super.email = userDTO.email;
        super.password = userDTO.password;
        super.role = userDTO.role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role.getName()));

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAccountNonLocked() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "UserDetailsAdapter{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
