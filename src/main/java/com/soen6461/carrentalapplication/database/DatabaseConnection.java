package com.soen6461.carrentalapplication.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseConnection {
	@Autowired
	private DataSource dataSource;

//	private static DatabaseConnection instance = new DatabaseConnection();

//	private DatabaseConnection(){}
//	public static DatabaseConnection getInstance(){
//		return instance;
//	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Connection getConnection() throws SQLException
	{
		return dataSource.getConnection();
	}
}