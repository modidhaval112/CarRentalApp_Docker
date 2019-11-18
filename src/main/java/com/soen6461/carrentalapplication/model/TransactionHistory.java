package com.soen6461.carrentalapplication.model;

import org.springframework.stereotype.Component;

import com.soen6461.carrentalapplication.Helpers.DataValidationHelper;

import java.util.Date;

@Component
public class TransactionHistory {

    private String transactionId;
    private String vehicleType;
    private String model;
    private String lpr;
    private String clientName;
    private Date startDate;
    private Date endDate;
    private String status;
    private String color;
    private String make;
    private int year;

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
    public TransactionHistory(String transactionId, String vehicleType, String model, String lpr, String clientName, String startDate, String endDate, String status, String timeStamp, String color, String make, int year) {
        this.transactionId = transactionId;
        this.vehicleType = vehicleType;
        this.model = model;
        this.lpr = lpr;
        this.clientName = clientName;
        try {
            this.setStartDate(DataValidationHelper.dateFormat.parse(startDate));
            this.setEndDate(DataValidationHelper.dateFormat.parse(endDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.status = status;
        this.timeStamp = timeStamp;
        this.color = color;
        this.make = make;
        this.year = year;
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

    public String getStatus() {
        return status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getColor() {
        return color;
    }

    public String getMake() {
        return make;
    }

    public Date getStartDate() {
        return startDate;
    }

    private void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    private void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getYear() {
        return year;
    }

}