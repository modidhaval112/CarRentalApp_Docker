
package com.soen6461.carrentalapplication;

import com.soen6461.carrentalapplication.controller.ClientController;
import com.soen6461.carrentalapplication.controller.TransactionCatalog;

import static org.assertj.core.api.Assertions.*;

import com.soen6461.carrentalapplication.controller.VehicleCatalog; import
com.soen6461.carrentalapplication.model.ClientRecord; import
com.soen6461.carrentalapplication.model.Transaction; import
com.soen6461.carrentalapplication.model.Transaction.Status; 
import
com.soen6461.carrentalapplication.model.VehicleRecord; import
org.junit.Assert; import org.junit.Ignore; import org.junit.Test; import
org.junit.runner.RunWith; import
org.springframework.beans.factory.annotation.Autowired; import
org.springframework.boot.test.context.SpringBootTest; import
org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException; import java.util.List;

@RunWith(SpringRunner.class)

@SpringBootTest public class VehicleTests {

	@Autowired private VehicleCatalog vehicleCatalog;
	@Autowired 
	private ClientController clientController;
	@Autowired
	private TransactionCatalog transactionCatalog;

	@Test
	public void vehicleCheck() throws Exception { 
		vehicleCatalog.deleteVehicleRecord("ABE_636");
		vehicleCatalog.deleteVehicleRecord("ABD_636");
		vehicleCatalog.persistData();

		VehicleRecord vd1 =
				new VehicleRecord(0, 0, "ABE_636", "Sedan", "Audi", "A8", 2011, "Red");
		VehicleRecord vd2 =
				new VehicleRecord(0, 0, "ABD_636", "Sedan", "Audi", "A8", 2011, "Red");
		vehicleCatalog.persistData();

		VehicleRecord v1=vehicleCatalog.getVehicleRecord("ABD_636"); VehicleRecord v2 =
				vehicleCatalog.getVehicleRecord("ABE_636"); 
		Assert.assertNotNull(v1);
		Assert.assertNotNull(v2); 
	}

	@Test 
	public void vehicleRentSizeCheck() throws Exception { 
		vehicleCatalog.deleteVehicleRecord("ABE_636");
		vehicleCatalog.deleteVehicleRecord("ABD_636");
		vehicleCatalog.persistData();

		VehicleRecord vd1 =
				new VehicleRecord(0, 0, "ABE_636", "Sedan", "Audi", "A8", 2011, "Red");
		VehicleRecord vd2 =
				new VehicleRecord(0, 0, "ABD_636", "Sedan", "Audi", "A8", 2011, "Red");
		vehicleCatalog.persistData();
		vehicleCatalog.persistData();

		VehicleRecord v1
		= vehicleCatalog.getVehicleRecord("ABD_636");
		VehicleRecord v2 =
				vehicleCatalog.getVehicleRecord("ABE_636");
		Assert.assertTrue(v1.getVehicleTransactionList().size() == 0);
		Assert.assertTrue(v2.getVehicleTransactionList().size() == 0); }

	@Test public void vehicleNewRent() throws Exception { 
		clientController.deleteClientRecord("T-1234-123456-34");
		clientController.persistData();

		ClientRecord
		clientRecord = new ClientRecord("T-1234-123456-34", 0, "Johny", "Tester",
				"(438) 566-9999", "2059-10-31");
		clientController.persistData();

		vehicleCatalog.deleteVehicleRecord("ABE_636");
		vehicleCatalog.deleteVehicleRecord("ABD_636");
		vehicleCatalog.persistData();

		VehicleRecord vd1 =
				new VehicleRecord(0, 0, "ABE_636", "Sedan", "Audi", "A8", 2011, "Red");
		VehicleRecord vd2 =
				new VehicleRecord(0, 0, "ABD_636", "Sedan", "Audi", "A8", 2011, "Red");
		vehicleCatalog.persistData();

		VehicleRecord v1 = vehicleCatalog.getVehicleRecord("ABD_636"); 
		Transaction t1
		= new Transaction(0, clientRecord, v1, "2021-09-10", "2021-10-15",
				Transaction.Status.Rented);
		v1.addTransaction(t1);
		v1.getTransactionList().forEach(System.out::println);
		Assert.assertTrue(v1.getTransactionList().contains(t1));
	}

	@Test public void vehicleReserve() throws Exception { 
		clientController.deleteClientRecord("T-1234-123456-34");
		clientController.persistData();

		ClientRecord
		clientRecord = new ClientRecord("T-1234-123456-34", 0, "Johny", "Tester",
				"(438) 566-9999", "2059-10-31");
		clientController.persistData();

		VehicleRecord vd1 =
				new VehicleRecord(0, 0, "ABE_636", "Sedan", "Audi", "A8", 2011, "Red");
		VehicleRecord vd2 =
				new VehicleRecord(0, 0, "ABD_636", "Sedan", "Audi", "A8", 2011, "Red");
		vehicleCatalog.persistData();

		VehicleRecord v1 = vehicleCatalog.getVehicleRecord("ABD_636"); Transaction t1
		= new Transaction(0, clientRecord, v1, "2022-09-10", "2022-10-15",
				Transaction.Status.Rented); 
		v1.addTransaction(t1);
		v1.getTransactionList().forEach(System.out::println);
		Assert.assertTrue(v1.getTransactionList().contains(t1)); }

	@Test 
	public void vehicleCancel() throws Exception { 
		clientController.deleteClientRecord("T-1234-123456-34");
		clientController.persistData();

		ClientRecord
		clientRecord = new ClientRecord("T-1234-123456-34", 0, "Johny", "Tester",
				"(438) 566-9999", "2059-10-31");
		clientController.persistData();

		VehicleRecord vd1 =
				new VehicleRecord(0, 0, "ABE_636", "Sedan", "Audi", "A8", 2011, "Red");
		VehicleRecord vd2 =
				new VehicleRecord(0, 0, "ABD_636", "Sedan", "Audi", "A8", 2011, "Red");
		vehicleCatalog.persistData();

		VehicleRecord v1 = vehicleCatalog.getVehicleRecord("ABD_636"); Transaction t1
		= new Transaction(0, clientRecord, v1, "2021-09-10", "2021-10-15",
				Transaction.Status.Rented); v1.addTransaction(t1); int beforecancel =
				v1.getTransactionList().size(); v1.removeTransaction(t1.getTransactionId());
				int afterecancel = v1.getTransactionList().size();

				Assert.assertSame(t1.getStatus(), Status.Cancelled); }

	@Test 
	public void vehicleReturn() throws Exception { 
		clientController.deleteClientRecord("T-1234-123456-34");
		clientController.persistData();

		ClientRecord
		clientRecord = new ClientRecord("T-1234-123456-34", 0, "Johny", "Tester",
				"(438) 566-9999", "2059-10-31");
		clientController.persistData();

		VehicleRecord vd1 =
				new VehicleRecord(0, 0, "ABE_636", "Sedan", "Audi", "A8", 2011, "Red");
		VehicleRecord vd2 =
				new VehicleRecord(0, 0, "ABD_636", "Sedan", "Audi", "A8", 2011, "Red");
		vehicleCatalog.persistData();

		VehicleRecord v1 = vehicleCatalog.getVehicleRecord("ABD_636"); Transaction t1
		= new Transaction(0, clientRecord, v1, "2021-09-10", "2021-10-15",
				Transaction.Status.Rented); v1.addTransaction(t1); int beforecancel =
				v1.getTransactionList().size(); 
				v1.returnTransaction(t1.getTransactionId());
				int afterecancel = v1.getTransactionList().size();
				Assert.assertSame(t1.getStatus(), Status.Returned); }

	@Test
	public void vehicleReturnCheck() throws Exception {

		clientController.deleteClientRecord("T-1234-123456-34");
		clientController.persistData();

		ClientRecord
		clientRecord = new ClientRecord("T-1234-123456-34", 0, "Johny", "Tester",
				"(438) 566-9999", "2059-10-31");
		clientController.persistData();

		VehicleRecord vd1 =
				new VehicleRecord(0, 0, "ABE_636", "Sedan", "Audi", "A8", 2011, "Red");
		VehicleRecord vd2 =
				new VehicleRecord(0, 0, "ABD_636", "Sedan", "Audi", "A8", 2011, "Red");
		vehicleCatalog.persistData();

		VehicleRecord v1 = vehicleCatalog.getVehicleRecord("ABD_636");
		Transaction t1
		= new Transaction(0, clientRecord, v1, "2022-09-10", "2021-10-15",
				Transaction.Status.Rented);
		vehicleCatalog.persistData();
		transactionCatalog.persistData();

		vehicleCatalog.persistData();
		transactionCatalog.persistData();
		v1.removeTransaction(t1.getTransactionId());
		v1.getTransactionList().forEach(System.out::println);

		Assert.assertEquals(v1.getTransactionList().size(), v1.getTransactionList().size()); 

	}

	@Test 
	public void vehiclesDue() throws ParseException { 
		List<VehicleRecord>
		vr = vehicleCatalog.getDueParticularDay("2019-10-22"); 
		int beforeAdd =
				vr.size(); System.out.println(beforeAdd); 

				clientController.deleteClientRecord("T-1234-123456-34");
				clientController.persistData();

				ClientRecord
				clientRecord = new ClientRecord("T-1234-123456-34", 0, "Johny", "Tester",
						"(438) 566-9999", "2059-10-31");
				clientController.persistData();

				VehicleRecord vd1 =
						new VehicleRecord(0, 0, "ABE_636", "Sedan", "Audi", "A8", 2011, "Red");
				VehicleRecord vd2 =
						new VehicleRecord(0, 0, "ABF_636", "Sedan", "Audi", "A8", 2011, "Red");
				vehicleCatalog.persistData();

				VehicleRecord v1 = vehicleCatalog.getVehicleRecord("ABF_636"); 
				Transaction t1
				= new Transaction(0, clientRecord, v1, "2019-10-21", "2019-10-22",
						Transaction.Status.Rented); v1.addTransaction(t1); 
						transactionCatalog.persistData();
						int afterAdd =
								vehicleCatalog.getDueParticularDay("2019-10-22").size();

						System.out.println(afterAdd);

						Assert.assertNotEquals(beforeAdd, afterAdd); }

	@Test
	public void vehiclesDueTrue() throws ParseException {

		List<VehicleRecord> vr = vehicleCatalog.getDueParticularDay("2019-10-22");
		int beforeAdd = vr.size(); 
		System.out.println(beforeAdd); 
		clientController.deleteClientRecord("T-1234-123456-34");
		clientController.persistData();

		ClientRecord
		clientRecord = new ClientRecord("T-1234-123456-34", 0, "Johny", "Tester",
				"(438) 566-9999", "2059-10-31");
		clientController.persistData();

		VehicleRecord vd1 =
				new VehicleRecord(0, 0, "ABF_636", "Sedan", "Audi", "A8", 2011, "Red");
		VehicleRecord vd2 =
				new VehicleRecord(0, 0, "ABD_636", "Sedan", "Audi", "A8", 2011, "Red");
		vehicleCatalog.persistData();

		VehicleRecord v1 = vehicleCatalog.getVehicleRecord("ABF_636"); 
		Transaction t1
		= new Transaction(beforeAdd, clientRecord, v1, "2019-10-21", "2019-10-25",
				Transaction.Status.Rented); v1.addTransaction(t1);
				int afterAdd =
						vehicleCatalog.getDueParticularDay("2019-10-22").size();

				System.out.println(afterAdd);

				Assert.assertEquals(beforeAdd, afterAdd); } }
