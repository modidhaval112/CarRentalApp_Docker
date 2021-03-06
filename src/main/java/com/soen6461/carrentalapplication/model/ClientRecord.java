package com.soen6461.carrentalapplication.model;

import com.soen6461.carrentalapplication.Helpers.DataValidationHelper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

/**
 * Client Record model class
 */
@Component
public class ClientRecord {
    private int version;
    private String driversLicenseNumber;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date expirationDate;

    /**
     * ClientRecord class default constructor.
     */
    public ClientRecord() {
    }

    /**
     * ClientRecord class parameterised constructor.
     *
     * @param driversLicenseNumber the clients drivers license number.
     * @param firstName            the clients first name.
     * @param lastName             the clients last name.
     * @param phoneNumber          the clients phone number.
     * @param expirationDate       the clients drivers license expiration date.
     */
    public ClientRecord(String driversLicenseNumber, int version, String firstName, String lastName, String phoneNumber, String expirationDate) {

        this.version = version;
        this.setVersion(version);
        this.setDriversLicenseNumber(driversLicenseNumber);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPhoneNumber(phoneNumber);
        try {
            this.setExpirationDate(DataValidationHelper.dateFormat.parse(expirationDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the drivers license.
     *
     * @return The drivers license.
     */
    public String getDriversLicenseNumber() {
        return this.driversLicenseNumber;
    }

    /**
     * Gets the clients fist name.
     *
     * @return The first name.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Gets the clients last name.
     *
     * @return The clients last name.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Gets the clients phone number.
     *
     * @return The phone number.
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Gets the drivers license expiration date. (with format yyyy-mm-dd).
     *
     * @return The expiration date of the drivers license.
     */
    public String getExpirationDate() {

        return DataValidationHelper.dateFormat.format(this.expirationDate);
    }

    /**
     * Gets the drivers license expiration date.
     *
     * @return The expiration date of the drivers license.
     */
    public Date getExpirationDateObject() {
        return this.expirationDate;
    }

    /**
     * Sets the clients phone number.
     *
     * @param phoneNumber The phone number.
     */
    private void setPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.trim();

        if (DataValidationHelper.isPhoneNumberFormatValid(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        }
    }

    /**
     * Sets the drivers license expiration date.
     *
     * @param expirationDate The expiration date of the drivers license.
     */
    private void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Sets the clients last name.
     *
     * @param lastName The clients last name.
     */
    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the clients first name.
     *
     * @param firstName The first name of the client.
     */
    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the drivers license.
     *
     * @param driversLicenseNumber The drivers license number.
     */
    private void setDriversLicenseNumber(String driversLicenseNumber) {
        driversLicenseNumber = driversLicenseNumber.trim();

        if (DataValidationHelper.isDriversLicenseNumber(driversLicenseNumber)) {
            this.driversLicenseNumber = driversLicenseNumber;
        }
    }

    /**
     * Get the version of he record of client
     *
     * @return
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets the version number.
     *
     * @param version
     */
    private void setVersion(int version) {
        this.version = version;
    }

    /**
     * Override the toString method with client relevant information.
     *
     * @return Client record information.
     */
    public String toString() {
        return "clientRecord:{ firstName:" + this.getFirstName() +
                " lastName:" + this.getLastName() +
                " phoneNumber:" + this.getPhoneNumber() +
                " driversLicenseNumber:" + this.getDriversLicenseNumber() +
                " expirationDate:" + this.getExpirationDate() +
                "}";
    }
}
