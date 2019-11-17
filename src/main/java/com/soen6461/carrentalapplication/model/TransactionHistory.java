package com.soen6461.carrentalapplication.model;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TransactionHistory {

    private Transaction transaction;
    private String transactionId;
    private String vehicleType;
    private String model;
    private String lpr;
    private String clientName;
    private String startDate;
    private String endDate;
    private String status;

    // The timestamp cannot be altered by any class.
    // A timestamp does not need a very comprehensible number. It simply needs to mark a moment in time very precisely.
    private String timeStamp;

    public TransactionHistory() {

    }

    /**
     * Constructor for an instance of a recorded transaction in history.
     *
     * @param transactionId
     * @param vehicleType
     * @param model
     * @param lpr
     * @param clientName
     * @param startDate
     * @param endDate
     * @param status
     * @param timeStamp
     */
    public TransactionHistory(String transactionId, String vehicleType, String model, String lpr, String clientName, String startDate, String endDate, String status, String timeStamp) {
        this.transactionId = transactionId;
        this.vehicleType = vehicleType;
        this.model = model;
        this.lpr = lpr;
        this.clientName = clientName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getModel() {
        return model;
    }

    public String getLpr() {
        return lpr;
    }

    public String getClientName() {
        return clientName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}