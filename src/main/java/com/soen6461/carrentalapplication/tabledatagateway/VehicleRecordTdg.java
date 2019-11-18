package com.soen6461.carrentalapplication.tabledatagateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.soen6461.carrentalapplication.Helpers.DatabaseHelper;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.metadata.HikariDataSourcePoolMetadata;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

@Repository
public class VehicleRecordTdg {
    @Autowired
    DataSource dataSource;

    Statement stmt = null;

    public void checkHikari() {
        HikariPool hikariPool = (HikariPool)
                new DirectFieldAccessor(dataSource).getPropertyValue("pool");

        //Getting pool connection info
        System.out.println(" hikariPool getActiveConnections : " + hikariPool.getActiveConnections());
        System.out.println(" hikariPool getTotalConnections : " + hikariPool.getTotalConnections());
        System.out.println(" hikariPool getIdleConnections : " + hikariPool.getIdleConnections());
        System.out.println(" hikariPool getThreadsAwaitingConnection : " + hikariPool.getThreadsAwaitingConnection());

        //Getting maximum pool size - set from properties
        Integer t = new HikariDataSourcePoolMetadata((HikariDataSource) dataSource).getMax();
        System.out.println(" hikariPool Maximum Pool Size : " + t.toString());
    }

    public List<Map<String, Object>> findAll() {
        String sql = "SELECT * FROM " + DatabaseHelper.databaseName + ".vehicleRecord";
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
     * Method gets particular vehicle based on lpr
     *
     * @param lpr license plate
     * @return it returns the vehicle record
     */
    public Map<String, Object> findVehicle(String lpr) {
        String sql = "SELECT * FROM " + DatabaseHelper.databaseName + ".vehicleRecord where licensePlateNumber=\"" + lpr + "\";";
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            Map<String, Object> row = new HashMap<String, Object>();
            while (rs.next()) {

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
     * Method to delete a record from the database
     *
     * @param licensePlateNumber Id of the record to delete from the database.
     * @return True if the operation was a success, false otherwise.
     */
    public boolean delete(String licensePlateNumber) {
        String statement = "DELETE FROM `" + DatabaseHelper.databaseName + "`.`vehicleRecord` where `licensePlateNumber`=?";
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            PreparedStatement dbStatement = con.prepareStatement(statement);
            dbStatement.setString(1, licensePlateNumber);
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

    /**
     * Insert vehicle record in database.
     *
     * @param id            The vehicle id.
     * @param recordVersion The record version.
     * @param lpr           The license plate number.
     * @param carType       The vehicle type.
     * @param make          The vehicle make.
     * @param model         The vehicle model.
     * @param year          The vehicle year.
     * @param color         The vehicle color.
     * @return True if the operation was a success, false otherwise.
     */
    public boolean insert(int id, int recordVersion, String lpr, String carType, String make, String model, int year, String color) {
        String sql = "CREATE TABLE IF NOT EXISTS `" + DatabaseHelper.databaseName + "`.`" + "vehicleRecord" + "` (" +
                "    `licensePlateNumber` VARCHAR(50) PRIMARY KEY," +
                "    `id` INT," +
                "    `version` INT," +
                "    `carType` VARCHAR(60)," +
                "    `make` VARCHAR(50)," +
                "    `model` VARCHAR(50)," +
                "    `year` INT," +
                "    `color` VARCHAR(50)" +
                ");";

        String sqlRecord = "INSERT INTO `" + DatabaseHelper.databaseName + "`.`" + "vehicleRecord" + "`" +
                "(`licensePlateNumber`, `id`, `version`, `carType`, `make`, `model`, `year`, `color`) " +
                "VALUES (" +
                "\"" + lpr + "\", " +
                id + ", " +
                recordVersion + ", " +
                "\"" + carType + "\", " +
                "\"" + make + "\", " +
                "\"" + model + "\", " +
                year + ", " +
                "\"" + color + "\" " +
                ");";
        Connection con = null;
        try {

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
     * Update vehicle record in database.
     *
     * @param recordVersion The record version.
     * @param lpr           The license plate number.
     * @param carType       The vehicle type.
     * @param make          The vehicle make.
     * @param model         The vehicle model.
     * @param year          The vehicle year.
     * @param color         The vehicle color.
     * @return True if the operation was a success, false otherwise.
     */
    public boolean update(int id, int recordVersion, String lpr, String carType, String make, String model, int year, String color) {
        String sql = " UPDATE  `" + DatabaseHelper.databaseName + "`.`" + "vehicleRecord" + "` SET " +
                "`id`=" + id + ", " +
                "`version`=" + "version + 1" + ", " +
                "`licensePlateNumber`= \"" + lpr + "\", " +
                "`carType`=\"" + carType + "\", " +
                "`make`=\"" + make + "\", " +
                "`model`=\"" + model + "\", " +
                "`year`=\"" + year + "\", " +
                "`color`=\"" + color + "\" " +
                " WHERE licensePlateNumber=\"" + lpr + "\";";
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
}


