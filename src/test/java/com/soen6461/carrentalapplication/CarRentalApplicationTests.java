package com.soen6461.carrentalapplication;

import com.soen6461.carrentalapplication.controller.ClientController;

import static org.assertj.core.api.Assertions.*;

import com.soen6461.carrentalapplication.controller.VehicleCatalog;
import com.soen6461.carrentalapplication.model.ClientRecord;
import com.soen6461.carrentalapplication.model.VehicleRecord;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarRentalApplicationTests {

    @Autowired
    private ClientController clientController;

    @Autowired
    private  VehicleCatalog vehicleCatalog;

    @Test
    public void contextLoads() throws Exception {
        assertThat(this.clientController).isNotNull();
        assertThat(this.vehicleCatalog).isNotNull();
    }

    @Test
    public void testClerkClientFunctionality() throws Exception {

        // In iteration 1 the client register is preset with hard coded values.
        Assert.assertTrue(clientController.getAllClientRecord().size() == 5);

        ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");
        clientController.addClientRecord(clientRecord);

		Assert.assertTrue(clientController.getAllClientRecord().size() == 6);
		clientRecord = clientController.searchClient("T-1234-123456-12");

		Assert.assertEquals("T-1234-123456-12", clientRecord.getDriversLicenseNumber());
        Assert.assertEquals("Johny", clientRecord.getFirstName());
        Assert.assertEquals("Tester", clientRecord.getLastName());
        Assert.assertEquals("(438) 566-9999", clientRecord.getPhoneNumber());
        Assert.assertEquals(new Date(2019, 10, 31), clientRecord.getExpirationDate());

        clientController.deleteClientRecord("T-1234-123456-12");
        Assert.assertTrue(clientController.getAllClientRecord().size() == 5);

        // Validate the presence of the hard coded transactions.

        VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("A12_636");
        VehicleRecord v2 =  vehicleCatalog.getVehicleRecord("X12_646");
        Assert.assertNotNull(v1);
        Assert.assertNotNull(v2);

        Assert.assertTrue(v1.getVehicleTransactionList().size() != 0);
        Assert.assertTrue(v2.getVehicleTransactionList().size() != 0);
    }
}
