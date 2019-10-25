package com.soen6461.carrentalapplication;

import com.soen6461.carrentalapplication.Helpers.DataValidationHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataValidationHelperTests {

    /**
     * Test the format of phone numbers.
     */
    @Test
    public void phoneNumberFormatValidationTest() {
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
    public void DateFormatValidationTest() {
        Assert.assertFalse(DataValidationHelper.isDateFormatValid("Fake number"));
        Assert.assertTrue(DataValidationHelper.isDateFormatValid("2013-10-05"));
    }

    /**
     * Test the format of the date expected for this application.
     */
    @Test
    public void DriversLicenseNumberFormatValidationTest() {
        Assert.assertFalse(DataValidationHelper.isDriversLicenseNumber("Fake number"));
        Assert.assertTrue(DataValidationHelper.isDriversLicenseNumber("A-1234-123456-12"));
    }

    /**
     * Test the format of the date expected for this application.
     */
    @Test
    public void LicenseRegistrationPlateFormatValidationTest() {
        Assert.assertFalse(DataValidationHelper.isLicenseRegistrationPlateValid("Fake number"));
        Assert.assertTrue(DataValidationHelper.isLicenseRegistrationPlateValid("ABC_123"));
        Assert.assertTrue(DataValidationHelper.isLicenseRegistrationPlateValid("ABC 123"));
    }
}
