package com.soen6461.carrentalapplication;

import com.soen6461.carrentalapplication.controller.ClientController;

import static org.assertj.core.api.Assertions.*;

import java.text.ParseException;
import java.util.List;

import com.soen6461.carrentalapplication.controller.VehicleCatalog;
import com.soen6461.carrentalapplication.model.ClientRecord;
import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.model.VehicleRecord;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionsTests {
	@Autowired
	VehicleCatalog vehicleCatalog;
	@Test
	public void vehiclesCurrentlyOut() throws ParseException
	{
		
		List<VehicleRecord> vr= vehicleCatalog.getCurrentlyOutVehciles(); 
		int beforeadd=vr.size();
		System.out.println(beforeadd);
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");

		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("UDF_126");
		Transaction t1= new Transaction(clientRecord, v1, "2019-10-23", "2019-12-27", Transaction.Status.Rented);
		v1.addTransaction(t1);
		int afteradd= vehicleCatalog.getCurrentlyOutVehciles().size();

		System.out.println(afteradd);

		Assert.assertFalse(beforeadd==afteradd);
	}
	@Test
	public void vehiclesCurrentlyOutFalse() throws ParseException
	{
		List<VehicleRecord> vr= vehicleCatalog.getCurrentlyOutVehciles(); 
		int beforeadd=vr.size();
		System.out.println(beforeadd);
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");

		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("UDF_126");
		Transaction t1= new Transaction(clientRecord, v1, "2019-12-23", "2019-12-27", Transaction.Status.Rented);
		v1.addTransaction(t1);
		int afteradd= vehicleCatalog.getCurrentlyOutVehciles().size();

		System.out.println(afteradd);

		Assert.assertTrue(beforeadd==afteradd);
	}
	@Test
	public void vehiclesCurrentlyAvailable() throws ParseException
	{
		List<VehicleRecord> vr= vehicleCatalog.getAvailablabilityBetweenDates("2019-12-23", "2019-12-27");
		int beforeadd=vr.size();
		System.out.println(beforeadd);
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");

		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("UDF_126");
		Transaction t1= new Transaction(clientRecord, v1, "2019-12-23", "2019-12-27", Transaction.Status.Rented);
		v1.addTransaction(t1);
		int afteradd= vehicleCatalog.getAvailablabilityBetweenDates("2019-12-23", "2019-12-27").size();

		System.out.println(afteradd);

		Assert.assertTrue(beforeadd==afteradd);
	}
	
	@Test
	public void vehiclesCurrentlyAvailableFalse() throws ParseException
	{
		List<VehicleRecord> vr= vehicleCatalog.getAvailablabilityBetweenDates("2019-12-23", "2019-12-27");
		int beforeadd=vr.size();
		System.out.println(beforeadd);
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");

		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("ABF_636");
		Transaction t1= new Transaction(clientRecord, v1, "2019-12-23", "2019-12-27", Transaction.Status.Rented);
		v1.addTransaction(t1);
		int afteradd= vehicleCatalog.getAvailablabilityBetweenDates("2019-12-23", "2019-12-27").size();

		System.out.println(afteradd);

		Assert.assertFalse(beforeadd==afteradd);
	}
	@Test
	public void vehiclesOverDue() throws ParseException
	{
		List<VehicleRecord> vr= vehicleCatalog.getOverDueParticularDay("2019-10-22");
		int beforeadd=vr.size();
		System.out.println(beforeadd);
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");

		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("ABF_636");
		Transaction t1= new Transaction(clientRecord, v1, "2019-10-19", "2019-10-21", Transaction.Status.Rented);
		v1.addTransaction(t1);
		int afteradd= vehicleCatalog.getOverDueParticularDay("2019-10-22").size();

		System.out.println(afteradd);

		Assert.assertFalse(beforeadd==afteradd);

	}
	
	
	@Test
	public void vehiclesOverDueTrue() throws ParseException
	{
		List<VehicleRecord> vr= vehicleCatalog.getOverDueParticularDay("2019-10-22");
		int beforeadd=vr.size();
		System.out.println(beforeadd);
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");

		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("ABF_636");
		Transaction t1= new Transaction(clientRecord, v1, "2019-10-19", "2019-10-24", Transaction.Status.Rented);
		v1.addTransaction(t1);
		int afteradd= vehicleCatalog.getOverDueParticularDay("2019-10-22").size();

		System.out.println(afteradd);

		Assert.assertTrue(beforeadd==afteradd);
		
		

	}
}