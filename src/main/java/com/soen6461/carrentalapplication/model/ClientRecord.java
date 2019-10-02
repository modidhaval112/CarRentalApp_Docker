package com.soen6461.carrentalapplication.model;

import java.util.Date;

/**
 * Client Record model class
 */
public class ClientRecord {
    private String driversLicenseNumber;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date expirationDate;
    private String assignedVehicle;

    /**
     * ClientRecord class parameterised constructor
     *
     * @param driversLicenseNumber the clients drivers license number.
     * @param firstName the clients first name.
     * @param lastName the clients last name.
     * @param phoneNumber the clients phone number.
     * @param expiration the clients drivers license expiration date.
     */

    public ClientRecord(String driversLicenseNumber, String firstName, String lastName, String phoneNumber, Date expiration) {
        this.driversLicenseNumber = driversLicenseNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.expirationDate = expiration;
    }

    /**
     * Gets the drivers license.
     * @return
     */
    public String getDriversLicense() {
        return this.driversLicenseNumber;
    }

    /**
     * Sets the drivers license.
     * @param driversLicenseNumber
     */
    public void setDriversLicense(String driversLicenseNumber) {
        // TODO: Validate the driverse license number formatt.
        this.driversLicenseNumber = driversLicenseNumber;
    }

    /**
     * Gets the clients fist name.
     * @return the first name.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Sets the clients first name.
     * @param firstName first name of the client.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the clients last name.
     * @return the clients last name.
     */
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the clients phone number.
     * @return the phone number.
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Sets the clients phone number.
     * @param phoneNumber the phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the drivers license expiration date.
     * @return the expiration date of the drivers license.
     */
    public Date getExpirationDate() {
        return this.expirationDate;
    }

    /**
     * Sets the drivers license expiration date.
     * @param expirationDate the expiration date of the drivers license.
     */
    public void setExpiration(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getAssignedVehicle() {
        return assignedVehicle;
    }

    public void setAssignedVehicle(String assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }
}



