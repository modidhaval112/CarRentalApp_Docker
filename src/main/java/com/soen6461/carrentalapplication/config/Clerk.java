package com.soen6461.carrentalapplication.config;

public class Clerk extends User {

    /**
     * User class parameterised constructor.
     *
     * @param username Clerk username.
     * @param password Clerk password.
     */
    public Clerk(String username, String password) {
        super(username, password, RoleType.Clerk);
    }
}
