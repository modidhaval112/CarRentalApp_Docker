package com.soen6461.carrentalapplication.config;

public class Administrator extends User {

    /**
     * User class parameterised constructor.
     *
     * @param username
     * @param password
     */
    public Administrator(String username, String password) {
        super(username, password, RoleType.Administrator);
    }
}
