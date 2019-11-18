package com.soen6461.carrentalapplication.config;

import com.soen6461.carrentalapplication.Helpers.DatabaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class is responsible to control the REST services related to users.
 * creation, search, modify, delete.
 */
@RestController
public class UserRegister {

    @Autowired
    DataSource dataSource;

    @Autowired
    private SessionRegistry sessionRegistry;

    private List<User> userList = new ArrayList<User>();

    public static Queue<String> adminQueue = new LinkedList<>();
    public boolean exitOtherAdmin;

    public void setUserRegisterObject() {

        String sql = "SELECT * FROM " + DatabaseHelper.databaseName + ".users";
        Connection con = null;

        try {
            con = this.dataSource.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String role = rs.getString("role");

                if (role.equalsIgnoreCase("USER")) {
                    addUser(new Clerk(rs.getString("username"), rs.getString("password")));
                }

                if (role.equalsIgnoreCase("ADMINISTRATOR")) {
                    addUser(new Administrator(rs.getString("username"), rs.getString("password")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * method to add new users
     *
     * @param user
     */
    public void addUser(User user) {
        userList.add(user);
    }

    /**
     * method to fetch all clerks.
     *
     * @return All the clerks
     */
    public List<Clerk> getAllClerks() {
        List<Clerk> clerkList = new ArrayList<Clerk>();
        for (User user : this.userList) {

            if (user.getRole() == User.RoleType.Clerk) {
                clerkList.add((Clerk) user);
            }
        }

        return clerkList;
    }

    /**
     * method to fetch all the admins
     *
     * @return All the administrators
     */
    public List<Administrator> getAllAdministrators() {
        List<Administrator> administratorList = new ArrayList<Administrator>();
        for (User user : this.userList) {

            if (user.getRole() == User.RoleType.Administrator) {
                administratorList.add((Administrator) user);
            }
        }

        return administratorList;
    }

    /**
     * Management of the login of admin once the session is destroyed.
     */
    @Component
    public class SessionListenerDestroy implements ApplicationListener<HttpSessionDestroyedEvent> {

        /**
         * Event called on session destroy.
         *
         * @param event Http Session Destroyed Event
         */
        @Override
        public void onApplicationEvent(HttpSessionDestroyedEvent event) {
            System.out.println("Destroyed session");

            if (isAdministratorRole()) {
                String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
                adminQueue.remove(username);
            }
        }
    }

    /**
     * Management of the login of admin post login.
     */
    @Component
    public class SessionListenerStart implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

        /**
         * Event called once the user is authenticated.
         *
         * @param event Interactive Authentication Success Event
         */
        @Override
        public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
            UserDetails user = (UserDetails) event.getAuthentication().getPrincipal();
            System.out.println("LOGIN name: " + user.getUsername());

            if (isAdministratorRole()) {
                String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

                if (adminQueue.size() == 0) {
                    adminQueue.add(username);
                } else {
                    exitOtherAdmin = true;
                    SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
                }
            }
        }
    }

    /**
     * Validates if logged in user is Admin or not
     *
     * @return true or false
     */
    public boolean isAdministratorRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.toString().equals("ROLE_" + User.RoleType.Administrator)) {
                return true;
            }
        }

        return false;
    }
}
