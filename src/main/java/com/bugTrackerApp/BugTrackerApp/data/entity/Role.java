package com.bugTrackerApp.BugTrackerApp.data.entity;

public enum Role {
    USER("USER"), ADMIN("ADMIN");

    private String roleName;

    private Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
