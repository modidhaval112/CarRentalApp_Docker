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
public class TransactionTdg {
	
    @Autowired
    DataSource dataSource;
    //	DatabaseConnection dc= DatabaseConnection.getInstance();
    Connection con;
    Statement stmt = null;

	/**
	 * @param id table id 
	 * @param recordVersion version
	 * @param transactionId transaction id of transaction
	 * @param status status of transaction
	 * @param startDate startDate of transaction
	 * @param endDate endDate of transaction
	 * @param licensePlateNumber licensePlateNumber of vehicle
	 * @param driversLicenseNumber driversLicenseNumber of client
	 * @return
	 */
	public boolean insert( int recordVersion, String transactionId, String status, Date startDate, Date endDate, String licensePlateNumber,
			String driversLicenseNumber) {
		String sql = "CREATE TABLE IF NOT EXISTS carrentaldb.  transaction   ( \n" + 
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

		String sqlRecord = "INSERT INTO `carrentaldb`.`transaction`" + 
				"(`transactionId`," + 
				"`version`," + 
				"`status`," + 
				"`startDate`," + 
				"`endDate`," + 
				"`licensePlateNumber`," + 
				"`driversLicenseNumber`)" + 
				"VALUES" + 
				"("+transactionId+"," + 
				recordVersion + 
				status+"," + 
				startDate+"," + 
				endDate+"," + 
				licensePlateNumber+"," + 
				driversLicenseNumber+");" ;


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

	 public boolean update( int recordVersion, String transactionId, String status, Date startDate, Date endDate, String licensePlateNumber,
				String driversLicenseNumber) {

	        String sql = " UPDATE  `carrentaldb`.`" + "`transaction`" + "` SET " +
	                "`version`=" + recordVersion + ", " +
	                "`transactionId`=" + transactionId + ", " +
	                "`status`= \"" + status + "\", " +
	                "`startDate`=\"" + startDate + "\", " +
	                "`endDate`=\"" + endDate + "\", " +
	                "`licensePlateNumber`=\"" + licensePlateNumber + "\", " +
	                " WHERE transactionId=" + transactionId + ";";
	        try {
	            Statement stmt = this.dataSource.getConnection().createStatement();
	            stmt.executeUpdate(sql);
	            return true;
	        } catch (Exception e) {
	            System.out.println("Get object exception" + e.getMessage());
	            return false;

	        }
	    }
	
	 public boolean delete(String transactionId) {
	        String statement = "DELETE FROM `carrentaldb`.`transaction` where `transactionId `=?";
			try {
				PreparedStatement dbStatement = this.dataSource.getConnection().prepareStatement(statement);
				dbStatement.setString(1 ,transactionId);
				dbStatement.executeUpdate();
				return true;
			}catch(SQLException e){
	            System.out.println("Get object exception" + e.getMessage());
	            return false;
			}

	    }
	 
	 public List<Map<String, Object>> findAll() throws SQLException {
	        String sql = "SELECT * FROM carrentaldb.transaction";
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

	 public Map<String, Object> findTransaction(String transactionid) throws SQLException {
	        String sql = "SELECT * FROM carrentaldb.transaction where transactionId=" +transactionid+ ";";
	        try {
	            Statement stmt = this.dataSource.getConnection().createStatement();
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
	        }
	    }

}
