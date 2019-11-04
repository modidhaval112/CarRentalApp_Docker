package com.soen6461.carrentalapplication;


import com.soen6461.carrentalapplication.model.VehicleRecord;
import com.soen6461.carrentalapplication.model.VehicleRecordDataMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleRecordDataMapperTests {

    @Test
    public void CRUDTest() throws SQLException {
        VehicleRecordDataMapper vrdm = new VehicleRecordDataMapper();

        // Set db connection
        // TODO: This will be removed.
        //// Connection connection = vrdm.getConnection();
        //// Assert.notNull(connection);

        // Clear the test data.
        Assert.isTrue(vrdm.clearTestingData(), "Clearing testing data.");

        // Set the vehicle record data mapper in test mode.
        vrdm.setIsDataMapperTest(true);

        // Testing insertion in database.
        VehicleRecord originalVR = new VehicleRecord("TES_123", "SUV", "Jeep", "Hummer", 2019, "Yellow");
        Assert.isTrue(vrdm.insert( originalVR), "Testing insertion in database");

        // Testing read from database.
        VehicleRecord updatedVehicleRecord = vrdm.getObject(0);

        Assert.isTrue(originalVR.getLpr().equals("TES_123"), "Testing read from database.");
        Assert.isTrue(originalVR.getCarType().equals("SUV"), "Testing read from database.");
        Assert.isTrue(originalVR.getMake().equals("Jeep"), "Testing read from database.");
        Assert.isTrue(originalVR.getModel().equals("Hummer"), "Testing read from database.");
        Assert.isTrue(originalVR.getYear() == 2019, "Testing read from database.");
        Assert.isTrue(originalVR.getColor().equals("Yellow"), "Testing read from database.");

        // Testing update record in database.
        VehicleRecord updatedVR = new VehicleRecord("TES_123", "SUV2", "Jeep2", "Hummer2", 2020, "Yellow2");
        Assert.isTrue(vrdm.update(0, updatedVR), "Testing insertion in database");

        // Testing deleting record from database.
        Assert.isTrue(vrdm.delete(0), "Testing insertion in database");


        // Clear the test data.
        Assert.isTrue(vrdm.clearTestingData());
    }
}
