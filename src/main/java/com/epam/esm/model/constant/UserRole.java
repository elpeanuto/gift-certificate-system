package com.epam.esm.model.constant;

public enum UserRole {
    ADMIN("Administrator"),
    MANAGER("Manager"),
    USER("User");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return name;
    }
}