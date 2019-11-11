package com.soen6461.carrentalapplication.config;

public abstract class User {

    private String username;

    private String password;

    private RoleType role;

    public enum RoleType {
        Clerk,
        Administrator
    }

    /**
     * User class parameterised constructor.
     *
     * @param username username.
     * @param password password.
     */
    protected User(String username, String password, RoleType role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    String getUsername() {
        return this.username;
    }

    String getPassword() {
        return this.password;
    }

    RoleType getRole() {
        return this.role;
    }
}
