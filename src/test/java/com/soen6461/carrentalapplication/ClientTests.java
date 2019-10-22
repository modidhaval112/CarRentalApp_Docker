package com.soen6461.carrentalapplication;

import com.soen6461.carrentalapplication.controller.ClientController;

import static org.assertj.core.api.Assertions.*;

import com.soen6461.carrentalapplication.model.ClientRecord;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientTests {

	@Autowired
	private ClientController clientController;

	/**
	 * Check for the class loading
	 */
	@Test
	public void contextLoads() {
		assertThat(this.clientController).isNotNull();
	}

	/**
	 * Check for the Client size
	 */
	@Test
	public void checkClientSize() {
		Assert.assertTrue(clientController.getAllClientRecord().size() == 5);

	}

	/**
	 * Check for adding new client
	 */
	@Test
	public void addNewClient() {
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");
		clientController.addClientRecord(clientRecord);
		Assert.assertTrue(clientController.getAllClientRecord().size() == 6);
	}
	
	/**
	 * Check for the client record
	 */
	@Ignore("Test is ignored does not pass on server. Needs further investigation.")
	@Test
	public void clientRecordCheck() {
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
	 */
	@Test
	public void deleteClient() {
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");
		clientController.addClientRecord(clientRecord);
		Assert.assertTrue(clientController.getAllClientRecord().size() == 6);
		clientController.deleteClientRecord("T-1234-123456-12");
		Assert.assertTrue(clientController.getAllClientRecord().size() == 5);
	}
}

