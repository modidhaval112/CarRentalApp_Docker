package com.soen6461.carrentalapplication.tabledatagateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.soen6461.carrentalapplication.Helpers.DatabaseHelper;

@Repository
public class ClientRecordTdg {
    @Autowired
    DataSource dataSource;
    
    Statement stmt = null;

    /**
     * Find all client records.
     *
     * @return All client records.
     */
    public List<Map<String, Object>> findAll() {
        String sql = "SELECT * FROM " + DatabaseHelper.databaseName + ".clientRecord";
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
            while (rs.next()) {

                Map<String, Object> row = new HashMap<String, Object>(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                rows.add(row);
            }
            return rows;
        } catch (Exception e) {
            System.out.println("Get object exception" + e.getMessage());
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Get particular client based on license of client
     *
     * @param driversLicense driver license number of client
     * @return returns particular client row
     */
    public Map<String, Object> findclient(String driversLicense) {
        String sql = "SELECT * FROM " + DatabaseHelper.databaseName + ".clientRecord WHERE driversLicense=" + driversLicense + ";";
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            Map<String, Object> row = new HashMap<String, Object>();
            while (rs.next()) {

                row = new HashMap<String, Object>(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
            }
            return row;
        } catch (Exception e) {
            System.out.println("Get object exception" + e.getMessage());
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Updates the client table based on license number
     *
     * @param firstName      first name of client
     * @param lastName       last name of client
     * @param phoneNumber    phone number of client
     * @param expirationDate expiration date of client license
     * @param driversLicense license number
     * @param version        version
     * @return True if the client record update succeeded, false otherwise.
     */
    public boolean update(String firstName, String lastName, String phoneNumber, Date expirationDate, String driversLicense, int version) {

        java.sql.Date dateDB = new java.sql.Date(expirationDate.getTime());
    	
        String sql = " UPDATE  `" + DatabaseHelper.databaseName + "`.`" + "clientRecord" + "` SET " +
                "`driversLicenseNumber`=\"" + driversLicense + "\", " +
                "`version`=\"" + version + "\", " +
                "`firstName`= \"" + firstName + "\", " +
                "`lastName`=\"" + lastName + "\", " +
                "`phoneNumber`=\"" + phoneNumber + "\", " +
                "`expirationDate`=\"" + dateDB + "\" " +
                " WHERE driversLicenseNumber=\"" + driversLicense + "\"; ";
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("Get object exception" + e.getMessage());
            return false;

        } finally {
            try {
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * @param firstName
     * @param lastName
     * @param phoneNumber
     * @param expirationDate
     * @param driversLicense
     * @param version
     * @return True if the client record insert succeeded, false otherwise.
     */
    public boolean insert(String firstName, String lastName, String phoneNumber, Date expirationDate, String driversLicense, int version) {
    	
        Connection con = null;

        try {
        String sql = "CREATE TABLE IF NOT EXISTS `" + DatabaseHelper.databaseName + "`.`" + "clientRecord" + "` (" +
                "    `driversLicenseNumber` VARCHAR(50) PRIMARY KEY," +
                "    `version` INT," +
                "    `firstname` VARCHAR(60)," +
                "    `lastname` VARCHAR(50)," +
                "    `phoneNumber` VARCHAR(50)," +
                "    `expirationDate` Date" +
                ");";
        
        java.sql.Date dateDB = new java.sql.Date(expirationDate.getTime());

        String sqlRecord = "INSERT INTO `" + DatabaseHelper.databaseName + "`.`" + "clientRecord" + "`" +
                "(`driversLicenseNumber`, `version`, `firstname`, `lastname`, `phoneNumber`, `expirationDate`) " +
                "VALUES (" +
                "\"" + driversLicense + "\", " +
                version + ", " +
                "\"" + firstName + "\", " +
                "\"" + lastName + "\", " +
                "\"" + phoneNumber + "\", " +
                "\"" + dateDB + "\" " +
                ");";

            con = this.dataSource.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            stmt.execute(sqlRecord);
            return true;

        } catch (Exception e) {
            System.out.println("Get object exception" + e.getMessage());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * @param driversLicense
     * @return True if the client record deletion succeeded, false otherwise.
     */
    public boolean delete(String driversLicense) {
        String statement = "DELETE FROM `" + DatabaseHelper.databaseName + "`.`clientRecord` where `driversLicenseNumber`=?";
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            PreparedStatement dbStatement = con.prepareStatement(statement);
            dbStatement.setString(1, driversLicense);
            dbStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Get object exception" + e.getMessage());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}


