package com.epam.esm.model.constant;

public enum UserRole {
    GUEST_ROLE("GUEST_ROLE"),
    ADMIN_ROLE("ADMIN_ROLE"),
    MANAGER_ROLE("MANAGER_ROLE"),
    USER_ROLE("USER_ROLE");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}