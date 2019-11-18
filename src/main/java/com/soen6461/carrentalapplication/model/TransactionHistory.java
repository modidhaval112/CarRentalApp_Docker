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

    /**
     * TransactionHistory class default constructor.
     */
    public TransactionHistory() {

    }

    /**
     * Constructor for an instance of a recorded transaction in history.
     *
     * @param transactionId The transaction id.
     * @param vehicleType   The vehicle type.
     * @param model         The vehicle model.
     * @param lpr           The license plate registration number.
     * @param clientName    The client name.
     * @param startDate     The start date.
     * @param endDate       The end date.
     * @param status        The status.
     * @param timeStamp     The time stamp.
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

    /**
     * Gets the transaction id.
     *
     * @return The transaction id.
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Gets the vehicle type.
     *
     * @return The vehicle type.
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * Gets the model.
     *
     * @return The model.
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the license plate number.
     *
     * @return The license plate number.
     */
    public String getLpr() {
        return lpr;
    }

    /**
     * Gets the client name.
     *
     * @return The client name.
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Gets the status.
     *
     * @return The status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets the timestamp.
     *
     * @return The timestamp.
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Gets the color.
     *
     * @return The color.
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets the make.
     *
     * @return the make.
     */
    public String getMake() {
        return make;
    }

    /**
     * Gets the start date.
     *
     * @return The start date.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate The start date.
     */
    private void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return The end date.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate The end date.
     */
    private void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the year.
     *
     * @return The year.
     */
    public int getYear() {
        return year;
    }
}