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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleRecordTdg {
    @Autowired
    DataSource dataSource;
    //	DatabaseConnection dc= DatabaseConnection.getInstance();
    Connection con;
    Statement stmt = null;


    public List<Map<String, Object>> findAll() throws SQLException {
        String sql = "SELECT * FROM carrentaldb.vehicleRecord";
        try {
            Statement stmt = this.dataSource.getConnection().createStatement();
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
        }
    }
    
    /**
     * Method gets particular vehicle based on lpr
     * @param lpr license plate
     * @return it returns the vehicle record
     */
    public Map<String, Object> findVehicle(String lpr)  {
        String sql = "SELECT * FROM carrentaldb.vehicleRecord where licensePlateNumber= " + lpr +";";
        try {
            Statement stmt = this.dataSource.getConnection().createStatement();
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
        }
    }
    

    public boolean delete(String licensePlateNumber) {
        String statement = "DELETE FROM `carrentaldb`.`vehicleRecord` where `licensePlateNumber `=?";
		try {
			PreparedStatement dbStatement = this.dataSource.getConnection().prepareStatement(statement);
			dbStatement.setString(1 ,licensePlateNumber);
			dbStatement.executeUpdate();
			return true;
		}catch(SQLException e){
            System.out.println("Get object exception" + e.getMessage());
            return false;
		}

    }

	public boolean insert(int id, int recordVersion, String lpr, String carType, String make, String model, int year,
			String color) {
		String sql = "CREATE TABLE IF NOT EXISTS `carrentaldb`.`" + "`vehicleRecord`" + "` (" +
                "    `licensePlateNumber` VARCHAR(50) PRIMARY KEY," +
                "    `id` INT," +
                "    `version` INT," +
                "    `carType` VARCHAR(60)," +
                "    `make` VARCHAR(50)," +
                "    `model` VARCHAR(50)," +
                "    `year` INT" +
                "    `color` VARCHAR(50)," +
                ");";

        String sqlRecord = "INSERT INTO `carrentaldb`.`" + "`vehicleRecord`" + "`" +
                "(`licensePlateNumber`, `id`, `version`, `carType`, `make`, `model`, `year`, `color`) " +
                "VALUES (" +
                lpr + ", " +
                id + ", " +
                recordVersion + ", " +
                "\"" + carType + "\", " +
                "\"" + make + "\", " +
                "\"" + model + "\", " +
                "\"" + year + "\", " +
                "\"" + color + "\", " +
                ");";

        try {
			Statement stmt = this.dataSource.getConnection().createStatement();
            stmt.executeUpdate(sql);
            stmt.execute(sqlRecord);
            return true;

        } catch (Exception e) {
            System.out.println("Get object exception" + e.getMessage());
            return false;
        }
	}

	public boolean update(int id, int recordVersion, String lpr, String carType, String make, String model, int year,
			String color) {
		String sql = " UPDATE  `carrentaldb`.`" + "`vehicleRecord`" + "` SET " +
				"`id`=" + id + ", " +
                "`version`=" + recordVersion + ", " +
                "`licensePlateNumber`= \"" + lpr + "\", " +
                "`carType`=\"" + carType + "\", " +
                "`make`=\"" + make + "\", " +
                "`model`=\"" + model + "\", " +
                "`year`=\"" + year + "\", " +
                "`color`=\"" + color + "\", " +
                " WHERE licensePlateNumber=" + lpr + ";";
        try {
            Statement stmt = this.dataSource.getConnection().createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("Get object exception" + e.getMessage());
            return false;

        }
	}

}


