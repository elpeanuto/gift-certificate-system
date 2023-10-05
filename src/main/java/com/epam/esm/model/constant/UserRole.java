package com.epam.esm.model.constant;

/**
 * The UserRole enum represents different roles that users can have in the application.
 * Each role has a unique name associated with it.
 */
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