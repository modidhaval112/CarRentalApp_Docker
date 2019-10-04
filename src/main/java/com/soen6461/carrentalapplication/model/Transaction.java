package com.soen6461.carrentalapplication.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

    ClientRecord clientRecord;
    VehicleRecord vehicleRecord;
    Date startDate;
    Date endDate;
    String transactionId;

    private Status status;


    /**
     * State of a vehicle with respect to the transaction.
     */
    public enum Status {
        Available,
        Rented,
        Reserved
    }

    /**
     * Transaction done by the clerk.
     *
     * @param clientRecord  Client who is part of the transaction.
     * @param vehicleRecord The vehicle of interest of the client.
     * @param startDate     Start date of the transaction.
     * @param endDate       End date of the transaction.
     * @param vehicleStatus Status of the transaction.
     * @throws ParseException 
     */
    public Transaction(ClientRecord clientRecord, VehicleRecord vehicleRecord, String startDate, String endDate, Status vehicleStatus) throws ParseException {

        this.setClientRecord(clientRecord);
        this.setVehicleRecord(vehicleRecord);
        try {
            this.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
            this.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
        } catch(Exception e){
            e.printStackTrace();
        }
        this.setStatus(vehicleStatus);
        this.setTransactionId(clientRecord.getDriversLicenseNumber()+"_"+vehicleRecord.getLpr()+"_"+ (new SimpleDateFormat("yyyy-MM-dd").parse(startDate)));
    }

    /*
     * Properties section.
     */
 
    /**
     * Gets the transaction id.
     * @return The transaction id.
     */
    public String getTransactionId() {
        return this.transactionId;
    }

    /**
     * Gets the vehicle status.
     * @return The vehicle status.
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Gets the client record.
     * @return The client record.
     */
    public ClientRecord getClientRecord() {
      return   this.clientRecord;
    }

    /**
     * Gets the vehicle status.
     * @return The vehicle record.
     */
    public VehicleRecord getVehicleRecord() {
        return this.vehicleRecord ;
    }

    /**
     * Gets the vehicle status.
     * @return The start date.
     */
    public Date getStartDate() {
        return this.startDate ;
    }

    /**
     * Gets the transaction end date.
     * @return The end date.
     */
    public Date getEndDate() {
        return  this.endDate ;
    }

    /**
     * Sets the vehicle status.
     * @param status The vehicle status.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Sets the client record.
     * @param clientRecord The client record.
     */
    private void setClientRecord(ClientRecord clientRecord) {
        this.clientRecord = clientRecord;
    }

    /**
     * Sets the vehicle record.
     * @param vehicleRecord The vehicle record.
     */
    private void setVehicleRecord(VehicleRecord vehicleRecord) {
        this.vehicleRecord = vehicleRecord;
    }

    /**
     * Sets the start date of the transaction.
     * @param startDate The start date.
     */
    private void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Sets the end date of the transaction.
     * @param endDate The end date.
     */
    private void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Sets the transaction id.
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
}