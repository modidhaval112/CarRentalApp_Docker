package com.soen6461.carrentalapplication.config;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible to control the REST services related to users.
 * creation, search, modify, delete.
 */
@RestController
public class UserRegister {

    private List<User> userList = new ArrayList<User>();

    public void addUser(User user) {
        userList.add(user);
    }

    public List<Clerk> getAllClerks() {
        List<Clerk> clerkList = new ArrayList<Clerk>();
        for (User user: this.userList) {

            if(user.getRole() == User.RoleType.Clerk) {
                clerkList.add((Clerk)user);
            }
        }

        return clerkList;
    }

    public List<Administrator> getAllAdministrators() {
        List<Administrator> administratorList = new ArrayList<Administrator>();
        for (User user: this.userList) {

            if(user.getRole() == User.RoleType.Administrator) {
                administratorList.add((Administrator)user);
            }
        }

        return administratorList;
    }
}
