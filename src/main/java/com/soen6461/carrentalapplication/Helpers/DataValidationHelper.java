package com.soen6461.carrentalapplication.Helpers;

import java.text.SimpleDateFormat;

public class DataValidationHelper {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    public static SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    /**
     * Validates the format of the phone number.
     *
     * @param phoneNumber Phone number.
     * @return True if the phone number is valid, false otherwise.
     */
    public static boolean isPhoneNumberFormatValid(String phoneNumber) {
        // Code was taken from the following web reference: https://www.journaldev.com/641/regular-expression-phone-number-validation-in-java

        try {
            phoneNumber = phoneNumber.trim();

            //validate phone numbers of format "1234567890"
            if (phoneNumber.matches("\\d{10}")) {
                return true;
            }
            //validating phone number with -, . or spaces
            else if (phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
                return true;
            }
            //validating phone number with extension length from 3 to 5
            else if (phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
                return true;
            }
            //validating phone number where area code is in braces ()
            else if (phoneNumber.matches("(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}")) {
                return true;
            }
        } catch (Exception e) {
            // There was an error and the matching failed.
        }

        return false;
    }

    /**
     * Validate the format of the date. (yyyy-mm-dd)
     *
     * @param date a date.
     * @return True if the date is valid, false otherwise.
     */
    public static boolean isDateFormatValid(String date) {
        date = date.trim();

        if (date.matches("\\d{4}[-]\\d{1,2}[-]\\d{1,2}")) {
            return true;
        }

        // No match found
        return false;
    }

    /**
     * Validate the drivers license format number
     *
     * @param driversLicenseNumber drivers license number
     * @return True if the drivers license number is valid, false otherwise.
     */
    public static boolean isDriversLicenseNumber(String driversLicenseNumber) {

        // Accepted format: A-1234-123456-12
        return driversLicenseNumber.trim().matches("\\p{Alpha}[-]\\d{4}[-]\\d{6}[-]\\d{2}");
    }

    /**
     * Validate the vehicle license registration plate.
     *
     * @param licenseRegistrationPlate Vehicle license registration plate.
     * @return True if the vehicle license registration plate is valid, false otherwise.
     */
    public static boolean isLicenseRegistrationPlateValid(String licenseRegistrationPlate) {

        // Accepted formats: ABC_012 or ABC 123
        return licenseRegistrationPlate.trim().matches("[A-Za-z]{3}[ _][0-9]{3}");
    }
}
