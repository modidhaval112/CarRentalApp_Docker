package com.soen6461.carrentalapplication;

import com.soen6461.carrentalapplication.controller.ClientController;

import static org.assertj.core.api.Assertions.*;

import com.soen6461.carrentalapplication.controller.VehicleCatalog;
import com.soen6461.carrentalapplication.model.ClientRecord;
import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.model.VehicleRecord;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
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
	public void checkClientSize()  throws Exception{
		Assert.assertTrue(clientController.getAllClientRecord().size() == 5);

	}
	@Test
	public void addNewClient()  throws Exception{
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");
		clientController.addClientRecord(clientRecord);
		Assert.assertTrue(clientController.getAllClientRecord().size() == 6);
	}

	@Test
	public void clientRecordCheck()  throws Exception{
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");
		clientController.addClientRecord(clientRecord);
		clientRecord = clientController.searchClient("T-1234-123456-12");

		Assert.assertEquals("T-1234-123456-12", clientRecord.getDriversLicenseNumber());
		Assert.assertEquals("Johny", clientRecord.getFirstName());
		Assert.assertEquals("Tester", clientRecord.getLastName());
		Assert.assertEquals("(438) 566-9999", clientRecord.getPhoneNumber());
		System.out.println(clientRecord.getExpirationDate());
		Assert.assertEquals("Fri Oct 31 00:00:00 EDT 2059", clientRecord.getExpirationDate().toString());
	}

	@Test
	public void deleteClient()  throws Exception{
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");
		clientController.addClientRecord(clientRecord);
		Assert.assertTrue(clientController.getAllClientRecord().size() == 6);
		clientRecord = clientController.searchClient("T-1234-123456-12");
		clientController.deleteClientRecord("T-1234-123456-12");
		Assert.assertTrue(clientController.getAllClientRecord().size() == 5);
	}


	@Test
	public void vehicleCheck()  throws Exception{
		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("A12_636");
		VehicleRecord v2 =  vehicleCatalog.getVehicleRecord("X12_646");
		Assert.assertNotNull(v1);
		Assert.assertNotNull(v2);
	}
	@Test

	public void vehicleRentSizeCheck() throws Exception{
		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("A12_636");
		VehicleRecord v2 =  vehicleCatalog.getVehicleRecord("X12_646");
		Assert.assertTrue(v1.getVehicleTransactionList().size() != 0);
		Assert.assertTrue(v2.getVehicleTransactionList().size() != 0);
	}

	@Test

	public void vehicleNewRent() throws Exception{
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");

		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("A12_636");
		Transaction t1= new Transaction(clientRecord, v1, "2021-09-10", "2021-10-15", Transaction.Status.Rented);
		v1.addTransaction(t1);
		v1.getTransactionList().forEach((n) -> System.out.println(n));
		Assert.assertTrue(v1.getTransactionList().contains(t1));
	}

	@Test
	public void vehicleCancel() throws Exception{
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");

		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("A12_636");
		Transaction t1= new Transaction(clientRecord, v1, "2021-09-10", "2021-10-15", Transaction.Status.Rented);
		v1.addTransaction(t1);
		int beforecancel= v1.getTransactionList().size();
		v1.removeTransaction("T-1234-123456-12_A12_636_Fri Sep 10 00:00:00 EDT 2021");
		int afterecancel= v1.getTransactionList().size();

		Assert.assertNotEquals(beforecancel, afterecancel);
	
	}
	
}

