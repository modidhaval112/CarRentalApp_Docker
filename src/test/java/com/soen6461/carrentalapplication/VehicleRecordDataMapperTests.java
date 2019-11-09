package com.soen6461.carrentalapplication;


import com.soen6461.carrentalapplication.model.VehicleRecord;
import com.soen6461.carrentalapplication.tabledatagateway.VehicleRecordTdg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleRecordDataMapperTests {

    @Test
    public void CRUDTest() throws SQLException {
        VehicleRecordTdg vtdg = new VehicleRecordTdg();

        // Set db connection
        // TODO: This will be removed.
        //// Connection connection = vrdm.getConnection();
        //// Assert.notNull(connection);

        // Clear the test data.
        Assert.isTrue(vtdg.clearTestingData(), "Clearing testing data.");

        // Set the vehicle record data mapper in test mode.
        vtdg.setIsDataMapperTest(true);

        // Testing insertion in database.
        VehicleRecord originalVR = new VehicleRecord("TES_123", "SUV", "Jeep", "Hummer", 2019, "Yellow");
        Assert.isTrue(vtdg.insert(originalVR), "Testing insertion in database");

        // Testing read from database.
        VehicleRecord updatedVehicleRecord = vtdg.getObject(0);

        Assert.isTrue(originalVR.getLpr().equals("TES_123"), "Testing read from database.");
        Assert.isTrue(originalVR.getCarType().equals("SUV"), "Testing read from database.");
        Assert.isTrue(originalVR.getMake().equals("Jeep"), "Testing read from database.");
        Assert.isTrue(originalVR.getModel().equals("Hummer"), "Testing read from database.");
        Assert.isTrue(originalVR.getYear() == 2019, "Testing read from database.");
        Assert.isTrue(originalVR.getColor().equals("Yellow"), "Testing read from database.");

        // Testing update record in database.
        VehicleRecord updatedVR = new VehicleRecord("TES_123", "SUV2", "Jeep2", "Hummer2", 2020, "Yellow2");
        Assert.isTrue(vtdg.update(0, updatedVR), "Testing insertion in database");

        // Testing deleting record from database.
        Assert.isTrue(vtdg.delete(0), "Testing insertion in database");

        // Clear the test data.
        Assert.isTrue(vtdg.clearTestingData());
    }
}
