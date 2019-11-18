
package com.soen6461.carrentalapplication;

import com.soen6461.carrentalapplication.controller.ClientController;

import static org.assertj.core.api.Assertions.*;

import com.soen6461.carrentalapplication.model.ClientRecord; import
org.junit.Assert; import org.junit.Ignore; import org.junit.Test; import
org.junit.runner.RunWith; import
org.springframework.beans.factory.annotation.Autowired; import
org.springframework.boot.test.context.SpringBootTest; import
org.springframework.test.context.junit4.SpringRunner;

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
		assertThat(this.clientController).isNotNull(); }

	/**
	 * Check for the Client size
	 */


	//	@Test public void checkClientSize() {
	//		Assert.assertTrue(clientController.getAllClientRecord().size() == 5); }
	//
	//	/**
	//	 * Check for adding new client
	//	 */


	@Test 
	public void addNewClient() throws Exception { 
		clientController.deleteClientRecord("T-1234-123456-34");
		clientController.persistData();


		ClientRecord clientRecord
		= new ClientRecord("T-1234-123456-34", 0, "Johny", "Tester", "(438) 566-9999",	  
				"2059-10-31"); 
	int count =clientController.getAllClientRecord().size() ;
	System.out.println(count);
	clientController.addClientRecord(clientRecord);
	clientController.persistData();
	System.out.println(clientController.getAllClientRecord().size());
	Assert.assertTrue(clientController.getAllClientRecord().size() == count+1); 
	}

	

	@Ignore @Test 
	public void clientRecordCheck() throws Exception { 
		clientController.deleteClientRecord("T-1234-123456-34");
		clientController.persistData();
		ClientRecord
		clientRecord = new ClientRecord("T-1234-123456-12", 0, "Johny", "Tester",
				"(438) 566-9999", "2059-10-31");

	clientController.addClientRecord(clientRecord);
	clientController.persistData();

	clientRecord =
			clientController.searchClient("T-1234-123456-12");

	Assert.assertEquals("T-1234-123456-12",
			clientRecord.getDriversLicenseNumber()); 
	Assert.assertEquals("Johny",
					clientRecord.getFirstName()); 
	Assert.assertEquals("Tester",
							clientRecord.getLastName()); 
	Assert.assertEquals("(438) 566-9999",
									clientRecord.getPhoneNumber()); }

	/**
	 * Check for the deletion of the client record
	 */
	@Test
	public void deleteClient() throws Exception {
		ClientRecord clientRecord
		= new ClientRecord("T-1234-123456-12", 0, "Johny", "Tester", "(438) 566-9999",
				"2059-10-31"); clientController.addClientRecord(clientRecord);
				clientController.persistData();

				
				int count =clientController.getAllClientRecord().size() ;

				clientController.deleteClientRecord("T-1234-123456-12");
				clientController.persistData();

				Assert.assertTrue(clientController.getAllClientRecord().size() ==count-1); } }
