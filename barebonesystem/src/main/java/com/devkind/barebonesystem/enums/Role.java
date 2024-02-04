package com.devkind.barebonesystem.enums;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private String roleCode;

    Role(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleCode() {
        return roleCode;
    }
}
