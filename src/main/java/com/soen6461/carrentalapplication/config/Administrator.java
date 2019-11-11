package com.soen6461.carrentalapplication.config;

public class Administrator extends User {

    /**
     * User class parameterised constructor.
     *
     * @param username Administrator password.
     * @param password Administrator username.
     */
    public Administrator(String username, String password) {
        super(username, password, RoleType.Administrator);
    }
}
