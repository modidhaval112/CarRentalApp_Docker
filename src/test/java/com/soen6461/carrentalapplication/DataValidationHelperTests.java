package com.soen6461.carrentalapplication;

import com.soen6461.carrentalapplication.Helpers.DataValidationHelper;
import org.junit.Assert;
import org.junit.Test;

public class DataValidationHelperTests {

    /**
     * Test the format of phone numbers.
     */
    @Test
    public void phoneNumberFormatValidation() {
        Assert.assertFalse(DataValidationHelper.isPhoneNumberFormatValid("Fake number"));
        Assert.assertTrue(DataValidationHelper.isPhoneNumberFormatValid("1234567890"));
        Assert.assertTrue(DataValidationHelper.isPhoneNumberFormatValid("123-456-7890 "));
        Assert.assertTrue(DataValidationHelper.isPhoneNumberFormatValid("123-456-7890 x1234"));
        Assert.assertTrue(DataValidationHelper.isPhoneNumberFormatValid("123-456-7890 ext1234"));
        Assert.assertTrue(DataValidationHelper.isPhoneNumberFormatValid("(123)-456-7890"));
        Assert.assertTrue(DataValidationHelper.isPhoneNumberFormatValid("123.456.7890"));
        Assert.assertTrue(DataValidationHelper.isPhoneNumberFormatValid("123 456 7890"));
    }

    /**
     * Test the format of the date expected for this application.
     */
    @Test
    public void DateFormatValidation() {
        Assert.assertFalse(DataValidationHelper.isDateFormatValid("Fake number"));
        Assert.assertTrue(DataValidationHelper.isDateFormatValid("2013-10-05"));
    }

    /**
     * Test the format of the date expected for this application.
     */
    @Test
    public void DriversLicenseNumberFormatValidation() {
        Assert.assertFalse(DataValidationHelper.isDriversLicenseNumber("Fake number"));
        Assert.assertTrue(DataValidationHelper.isDriversLicenseNumber("A-1234-123456-12"));
    }
}
