package com.soen6461.carrentalapplication.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseConnection {
	@Autowired
	private DataSource dataSource;
	private Connection connection;

	private static DatabaseConnection instance = new DatabaseConnection();

	private DatabaseConnection(){
		try {
			this.getConnection();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	public static DatabaseConnection getInstance(){
		return instance;
	}


	public void setDataSource (DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private void getConnection() throws SQLException
	{
		this.connection = dataSource.getConnection();
	}

	public Connection accessConnection(){
		return connection;
	}


}