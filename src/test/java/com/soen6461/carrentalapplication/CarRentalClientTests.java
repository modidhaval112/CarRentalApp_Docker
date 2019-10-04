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
public class CarRentalClientTests {

	@Autowired
	private ClientController clientController;

	@Autowired
	private  VehicleCatalog vehicleCatalog;

	/**
	 * Check for the class loading
	 * @throws Exception Throw the junit fail error
	 */
	@Test
	public void contextLoads() throws Exception {
		assertThat(this.clientController).isNotNull();
		assertThat(this.vehicleCatalog).isNotNull();
	}
	/**
	 * Check for the Client size
	 * @throws Exception Throw the junit fail error
	 */
	@Test
	public void checkClientSize()  throws Exception{
		Assert.assertTrue(clientController.getAllClientRecord().size() == 5);

	}
	/**
	 * Check for adding new client
	 * @throws Exception Throw the junit fail error
	 */
	@Test
	public void addNewClient()  throws Exception{
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");
		clientController.addClientRecord(clientRecord);
		Assert.assertTrue(clientController.getAllClientRecord().size() == 6);
	}
	
	/**
	 * Check for the client record 
	 * @throws Exception Throw the junit fail error
	 */

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
	/**
	 * Check for the deletion of the client record 
	 * @throws Exception Throw the junit fail error
	 */
	@Test
	public void deleteClient()  throws Exception{
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");
		clientController.addClientRecord(clientRecord);
		Assert.assertTrue(clientController.getAllClientRecord().size() == 6);
		clientRecord = clientController.searchClient("T-1234-123456-12");
		clientController.deleteClientRecord("T-1234-123456-12");
		Assert.assertTrue(clientController.getAllClientRecord().size() == 5);
	}


	
	
}

