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

@Repository
public class ClientRecordTdg {
    @Autowired
    DataSource dataSource;
    //	DatabaseConnection dc= DatabaseConnection.getInstance();
    Connection con;
    Statement stmt = null;


//	ClientTDG() throws SQLException
//	{
//		this.con=dc.getConnection();
//	}


    public List<Map<String, Object>> findAll()  {
        String sql = "SELECT * FROM carrentaldb.clientRecord";
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

    public boolean update(String firstname, String lastname, String phoneNumber, Date expirationDate, String driversLicense, int version) {

        String sql = " UPDATE  `carrentaldb`.`" + "`clientRecord`" + "` SET " +
                "`driversLicenseNumber`=" + driversLicense + ", " +
                "`version`=" + version + ", " +
                "`firstname`= \"" + firstname + "\", " +
                "`lastname`=\"" + lastname + "\", " +
                "`phoneNumber`=\"" + phoneNumber + "\", " +
                "`expirationDate`=\"" + expirationDate + "\", " +
                " WHERE driversLicense=" + driversLicense + ";";
        try {
            Statement stmt = this.dataSource.getConnection().createStatement();
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println("Get object exception" + e.getMessage());
            return false;

        }
    }

    public boolean insert(String firstname, String lastname, String phoneNumber, Date expirationDate, String driversLicense, int version) {
        String sql = "CREATE TABLE IF NOT EXISTS `carrentaldb`.`" + "`clientRecord`" + "` (" +
                "    `driversLicenseNumber` VARCHAR(50) PRIMARY KEY," +
                "    `version` INT," +
                "    `firstname` VARCHAR(60)," +
                "    `lastname` VARCHAR(50)," +
                "    `phoneNumber` VARCHAR(50)," +
                "    `expirationDate` Date" +
                ");";

        String sqlRecord = "INSERT INTO `carrentaldb`.`" + "`clientRecord`" + "`" +
                "(`driversLicenseNumber`, `version`, `firstname`, `lastname`, `phoneNumber`, `expirationDate`) " +
                "VALUES (" +
                driversLicense + ", " +
                version + ", " +
                "\"" + firstname + "\", " +
                "\"" + lastname + "\", " +
                "\"" + phoneNumber + "\", " +
                "\"" + expirationDate + "\", " +
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


    public boolean delete(String driversLicense) {
        String statement = "DELETE FROM `carrentaldb`.`clientRecord` where `driversLicenseNumber`=?";
		try {
			PreparedStatement dbStatement = this.dataSource.getConnection().prepareStatement(statement);
			dbStatement.setString(1 ,driversLicense);
			dbStatement.executeUpdate();
			return true;
		}catch(SQLException e){
            System.out.println("Get object exception" + e.getMessage());
            return false;
		}

    }

}


