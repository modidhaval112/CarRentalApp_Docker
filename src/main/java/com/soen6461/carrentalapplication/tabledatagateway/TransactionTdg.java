package com.soen6461.carrentalapplication.tabledatagateway;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

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
	public boolean insert(int id, int recordVersion, String transactionId, String status, Date startDate, Date endDate, String licensePlateNumber,
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

	 public boolean update(int id, int recordVersion, String transactionId, String status, Date startDate, Date endDate, String licensePlateNumber,
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
}
