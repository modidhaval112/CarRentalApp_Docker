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

import com.soen6461.carrentalapplication.Helpers.DatabaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionTdg {

    @Autowired
    DataSource dataSource;
    //	DatabaseConnection dc= DatabaseConnection.getInstance();
    Connection con;
    Statement stmt = null;

    /**
     * @param recordVersion        version
     * @param transactionId        transaction id of transaction
     * @param status               status of transaction
     * @param startDate            startDate of transaction
     * @param endDate              endDate of transaction
     * @param licensePlateNumber   licensePlateNumber of vehicle
     * @param driversLicenseNumber driversLicenseNumber of client
     * @return
     */
    public boolean insert(int recordVersion, String transactionId, String status, Date startDate, Date endDate, String licensePlateNumber,
                          String driversLicenseNumber) {
        String sql = "CREATE TABLE IF NOT EXISTS " + DatabaseHelper.databaseName + ".  transaction   ( \n" +
                "                    transactionId VARCHAR(50) PRIMARY KEY, \n" +
                "                    version INT, \n" +
                "                    status VARCHAR(50), \n" +
                "                    startDate Date ,\n" +
                "                    endDate Date,\n" +
                "                    licensePlateNumber VARCHAR(50),\n" +
                "                    driversLicenseNumber VARCHAR(50),\n" +
                "					CONSTRAINT fk_vehcilerecord FOREIGN KEY (licensePlateNumber) references vehiclerecord(licensePlateNumber),\n" +
                "					CONSTRAINT fk_clientrecord FOREIGN KEY (driversLicenseNumber) references clientrecord(driversLicenseNumber)\n" +
                "\n" +
                "\n" +
                "                );";

        String sqlRecord = "INSERT INTO `" + DatabaseHelper.databaseName + "`.`" + "transaction" + "`" +
                "(`transactionId`," +
                "`version`," +
                "`status`," +
                "`startDate`," +
                "`endDate`," +
                "`licensePlateNumber`," +
                "`driversLicenseNumber`) " +
                "VALUES " +
                "(\"" +  transactionId + "\"," +
                "\"" + recordVersion +"\","+
                "\"" + status + "\"," +
                "\"" + new java.sql.Date(startDate.getTime()) + "\"," +
                "\"" + new java.sql.Date(endDate.getTime()) + "\"," +
                "\"" + licensePlateNumber + "\"," +
                "\"" + driversLicenseNumber + "\");";


        try {
            con = this.dataSource.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            stmt.execute(sqlRecord);
            return true;

        } catch (Exception e) {
            System.out.println("Get object exception" + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public boolean update(int recordVersion, String transactionId, String status, Date startDate, Date endDate, String licensePlateNumber,
                          String driversLicenseNumber) {
    	
        java.sql.Date startDateDB = new java.sql.Date(startDate.getTime());
        java.sql.Date endDateDB = new java.sql.Date(startDate.getTime());

        String sql = " UPDATE  `" + DatabaseHelper.databaseName + "`.`" + "transaction" + "` SET " +
                "`version`=\"" + recordVersion + "\", " +
                "`transactionId`=\"" + transactionId + "\", " +
                "`status`= \"" + status + "\", " +
                "`startDate`=\"" + startDateDB + "\", " +
                "`endDate`=\"" + endDateDB + "\", " +
                "`licensePlateNumber`=\"" + licensePlateNumber + "\" " +
                " WHERE transactionId=\"" + transactionId + "\";";
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

    public boolean delete(String transactionId) {
        String statement = "DELETE FROM `" + DatabaseHelper.databaseName + "`.`vehicleRecord` where `licensePlateNumber`=?";
        try {
            con = this.dataSource.getConnection();
            PreparedStatement dbStatement = con.prepareStatement(statement);
            dbStatement.setString(1, transactionId);
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

    public List<Map<String, Object>> findAll() {
        String sql = "SELECT * FROM " + DatabaseHelper.databaseName + ".transaction";
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

    public List<Map<String, Object>> findAll(String lpr) {
        String sql = "SELECT * FROM " + DatabaseHelper.databaseName + ".transaction" + " WHERE licensePlateNumber='" + lpr + "'";
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
            e.printStackTrace();
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    public Map<String, Object> findTransaction(String transactionid) throws SQLException {
        String sql = "SELECT * FROM " + DatabaseHelper.databaseName + ".transaction where transactionId=" + transactionid + ";";
        try {
            con = this.dataSource.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            Map<String, Object> row = new HashMap<String, Object>(columns);

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

}
