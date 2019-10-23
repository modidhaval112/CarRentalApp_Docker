package com.soen6461.carrentalapplication.config;

public class Clerk extends User {

    /**
     * User class parameterised constructor.
     *
     * @param username username.
     * @param password password.
     */
    public Clerk(String username, String password) {
        super(username, password, RoleType.Clerk);
    }
}
