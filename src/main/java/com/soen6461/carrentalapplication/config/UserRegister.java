package com.soen6461.carrentalapplication.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.soen6461.carrentalapplication.database.DatabaseConnection;

/**
 * This class is responsible to control the REST services related to users.
 * creation, search, modify, delete.
 */
@RestController
public class UserRegister {
	
	@Autowired
	DataSource dataSource;
	DatabaseConnection dc = DatabaseConnection.getInstance();

	private List<User> userList = new ArrayList<User>();

	
	public ResultSet getObject() {
        String sql = "SELECT * FROM carrentaldb.users";
        try {
        	Statement stmt = this.dataSource.getConnection().createStatement();     	
            ResultSet rs = stmt.executeQuery(sql);
            
            return rs;

        } catch (Exception e) {
            System.out.println("Get object exception" + e.getMessage());
        }

        return null;
    }
	
	public void setUserRegisterObject() {
        ResultSet rs= getObject();
        try{
        	while( rs.next()) {
        		String role = rs.getString("role");
        		
        		if(role.equalsIgnoreCase("USER")) {
        			addUser(new Clerk(rs.getString("username"), rs.getString("password")));
        		}
        		
        		if(role.equalsIgnoreCase("ADMINISTRATOR")) {
        			addUser(new Administrator(rs.getString("username"), rs.getString("password")));
        		}
        	}
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
	
	/**
	 * method to add new users
	 * @param user
	 */
	public void addUser(User user) {
		userList.add(user);
	}

	/**
	 * method to fecth all clerks
	 * @return
	 */
	public List<Clerk> getAllClerks() {
		List<Clerk> clerkList = new ArrayList<Clerk>();
		for (User user: this.userList) {

			if(user.getRole() == User.RoleType.Clerk) {
				clerkList.add((Clerk)user);
			}
		}

		return clerkList;
	}

	/**
	 * method to fetch all the admins
	 * @return
	 */
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
