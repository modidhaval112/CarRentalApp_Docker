package com.soen6461.carrentalapplication.model;

import java.util.ArrayList;
import java.util.List;

public class VehicleRecord extends Record{

    private String carType;
    private String make;
    private String model;
    private int year;
    private String color;
    private String lpr;

    private List<Transaction> transactionList = new ArrayList<Transaction>();

    /**
     * The vehicle record constructor
     * @param lpr The license plate registration number.
     * @param carType The car type.
     * @param make The vehicle make.
     * @param model The vehicle model
     * @param year The vehicle year.
     * @param color The vehicle color.
     */
    public VehicleRecord(String lpr, String carType, String make, String model, int year, String color) {

        this.setCarType(carType);
        this.setMake(make);
        this.setYear(year);
        this.setColor(color);
        this.setModel(model);
        this.setLpr(lpr);
    }

    /*
     * Properties section.
     */

    /**
     * Gets a copy of the vehicle transaction list.
     * @return A copy of the vehicle transaction list.
     */
    public List<Transaction> getVehicleTransactionList()
    {
        return super.getTransactionList();
    }

    /**
     * Gets the car type.
     * @return The car type.
     */
    public String getCarType() {
        return this.carType;
    }

    /**
     * Gets the vehicle make.
     * @return The vehicle make.
     */
    public String getMake() {
        return this.make;
    }

    /**
     * Gets the vehicle model.
     * @return The vehicle model.
     */
    public String getModel() {
        return this.model;
    }

    /**
     * Gets the vehicle year.
     * @return The year.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Gets the vehicle color.
     * @return The vehicle color.
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Gets the license plate registration number.
     * @return The license plate registration number
     */
    public String getLpr() {
        return this.lpr;
    }

    /**
     * Sets the car type.
     * @param carType The car type.
     */
    private void setCarType(String carType) {
        this.carType = carType;
    }

    /**
     * Sets the make.
     * @param make The make.
     */
    private void setMake(String make) {
        this.make = make;
    }

    /**
     * Sets the model.
     * @param model The model.
     */
    private void setModel(String model) {
        this.model = model;
    }

    /**
     * Sets the year.
     * @param year the year.
     */
    private void setYear(int year) {
        this.year = year;
    }

    /**
     * Sets the color.
     * @param color The color.
     */
    private void setColor(String color) {
        this.color = color;
    }

    /**
     * Sets the license plate registration number.
     * @param lpr The license plate registration number.
     */
    private void setLpr(String lpr) {
        this.lpr = lpr;
    }

    /*
     * Methods section.
     */
}
