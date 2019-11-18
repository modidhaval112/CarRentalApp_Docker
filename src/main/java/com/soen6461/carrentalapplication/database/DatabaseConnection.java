package com.soen6461.carrentalapplication.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseConnection {
    @Autowired
    private DataSource dataSource;

    /**
     * Sets the datasource.
     *
     * @param dataSource The datasource.
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Get connection to datasource.
     *
     * @return The connection.
     * @throws SQLException Exception encountered while establishing the connection.
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}