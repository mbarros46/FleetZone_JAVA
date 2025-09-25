package com.fiap.fleetzone.model;

public enum Role {
    ROLE_ADMIN("Administrador"),
    ROLE_USER("Usuário");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAuthority() {
        return this.name();
    }
}