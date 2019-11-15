package com.soen6461.carrentalapplication.model;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.soen6461.carrentalapplication.Helpers.DataValidationHelper;
@Component
public class Transaction {
	private static int Id = 0;
	private ClientRecord clientRecord;
	private VehicleRecord vehicleRecord;
	private Date startDate;
	private Date endDate;
	private String transactionId;
	private Status status;
	private int version;

	/**
	 * State of a vehicle with respect to the transaction.
	 */
	public enum Status {
		Available,
		Rented,
		Reserved,
		Returned,
		Cancelled
	}
	public  Transaction(){}
	/**
	 * Transaction done by the clerk.
	 *
	 * @param clientRecord  Client who is part of the transaction.
	 * @param vehicleRecord The vehicle of interest of the client.
	 * @param startDate     Start date of the transaction.
	 * @param endDate       End date of the transaction.
	 * @param vehicleStatus Status of the transaction.
	 */

	public Transaction(int version,ClientRecord clientRecord, VehicleRecord vehicleRecord, String startDate, String endDate, Status vehicleStatus) {
		this.setVersion(version);

		this.setClientRecord(clientRecord);
		this.setVehicleRecord(vehicleRecord);
		try {
			this.setStartDate(DataValidationHelper.dateFormat.parse(startDate));
			this.setEndDate(DataValidationHelper.dateFormat.parse(endDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setStatus(vehicleStatus);
		this.setTransactionId(Id++ + "_" + clientRecord.getDriversLicenseNumber() + "_" + vehicleRecord.getLpr() + "_" + Calendar.getInstance().getTime().toString().replace(" ", "_"));
	}
	
	public Transaction(String transactionId, int version,ClientRecord clientRecord, VehicleRecord vehicleRecord, String startDate, String endDate, Status vehicleStatus) {
		this.setVersion(version);

		this.setClientRecord(clientRecord);
		this.setVehicleRecord(vehicleRecord);
		try {
			this.setStartDate(DataValidationHelper.dateFormat.parse(startDate));
			this.setEndDate(DataValidationHelper.dateFormat.parse(endDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setStatus(vehicleStatus);
		this.setTransactionId(transactionId);
	}

	public int getVersion() {
		return this.version;		
	}

	/*
	 * Properties section.
	 */

	/**
	 * Gets the transaction id.
	 *
	 * @return The transaction id.
	 */
	public String getTransactionId() {
		return this.transactionId;
	}

	/**
	 * Gets the vehicle status.
	 *
	 * @return The vehicle status.
	 */
	public Status getStatus() {
		return this.status;
	}

	/**
	 * Gets the client record.
	 *
	 * @return The client record.
	 */
	public ClientRecord getClientRecord() {
		return this.clientRecord;
	}

	/**
	 * Gets the vehicle status.
	 *
	 * @return The vehicle record.
	 */
	public VehicleRecord getVehicleRecord() {
		return this.vehicleRecord;
	}

	/**
	 * Gets the vehicle status. (yyyy-mm-dd)
	 *
	 * @return The start date.
	 */
	public String getStartDate() {
		return DataValidationHelper.dateFormat.format(this.startDate);
	}

	/**
	 * Gets the vehicle status.
	 *
	 * @return The start date.
	 */
	public Date getStartDateObject() {
		return this.startDate;
	}

	/**
	 * Gets the transaction end date. (yyyy-mm-dd)
	 *
	 * @return The end date.
	 */
	public String getEndDate() {
		return DataValidationHelper.dateFormat.format(this.endDate);
	}

	/**
	 * Gets the transaction end date.
	 *
	 * @return The end date.
	 */
	public Date getEndDateObject() {
		return this.endDate;
	}

	public static Status getStatus(String status) {
		
		if(status.equalsIgnoreCase("available"))
			return Status.Available;
		else if(status.equalsIgnoreCase("cancelled"))
			return Status.Cancelled;
		else if(status.equalsIgnoreCase("returned"))
			return Status.Returned;
		else if(status.equalsIgnoreCase("rented"))
			return Status.Rented;
		else if(status.equalsIgnoreCase("reserved"))
			return Status.Reserved;
		return null;

	}
	/**
	 * Sets the vehicle status.
	 *
	 * @param status The vehicle status.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Sets the client record.
	 *
	 * @param clientRecord The client record.
	 */
	private void setClientRecord(ClientRecord clientRecord) {
		this.clientRecord = clientRecord;
	}

	/**
	 * Sets the vehicle record.
	 *
	 * @param vehicleRecord The vehicle record.
	 */
	private void setVehicleRecord(VehicleRecord vehicleRecord) {
		this.vehicleRecord = vehicleRecord;
	}

	/**
	 * Sets the start date of the transaction.
	 *
	 * @param startDate The start date.
	 */
	private void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Sets the end date of the transaction.
	 *
	 * @param endDate The end date.
	 */
	private void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	

	/**
	 * Sets the transaction id.
	 *
	 * @param transactionId The transaction id.
	 */
	private void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/*
	 * Methods section.
	 */

	/**
	 * Removes a given transaction.
	 */
	public void removeTransaction() {
		vehicleRecord.removeTransaction(this.transactionId);
	}

	/**
	 * Override the toString method with transaction relevant information.
	 *
	 * @return Transaction information.
	 */

	private void setVersion(int version) {
		this.version=version;		
	}

	
	
	public String toString() {
		return "transaction:{ VehicleKey:" + this.vehicleRecord.getLpr() +
				" clientKey:" + this.clientRecord.getDriversLicenseNumber() +
				" startDate:" + this.getStartDate() +
				" endDate:" + this.getEndDate() +
				" transactionId:" + this.getTransactionId() +
				"}";
	}
}
