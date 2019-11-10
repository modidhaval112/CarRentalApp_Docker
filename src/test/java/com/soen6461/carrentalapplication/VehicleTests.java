package com.soen6461.carrentalapplication;

import com.soen6461.carrentalapplication.controller.ClientController;

import static org.assertj.core.api.Assertions.*;

import com.soen6461.carrentalapplication.controller.VehicleCatalog;
import com.soen6461.carrentalapplication.model.ClientRecord;
import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.model.Transaction.Status;
import com.soen6461.carrentalapplication.model.VehicleRecord;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleTests {

	@Autowired
	private  VehicleCatalog vehicleCatalog;

	@Test
	public void vehicleCheck()  throws Exception{
		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("ABD_636");
		VehicleRecord v2 =  vehicleCatalog.getVehicleRecord("ABE_636");
		Assert.assertNotNull(v1);
		Assert.assertNotNull(v2);
	}

	@Test
	public void vehicleRentSizeCheck() throws Exception{
		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("ABD_636");
		VehicleRecord v2 =  vehicleCatalog.getVehicleRecord("ABE_636");
		Assert.assertTrue(v1.getVehicleTransactionList().size() != 0);
		Assert.assertTrue(v2.getVehicleTransactionList().size() != 0);
	}

	@Test
	public void vehicleNewRent() throws Exception{
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");

		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("ABD_636");
		Transaction t1= new Transaction(clientRecord, v1, "2021-09-10", "2021-10-15", Transaction.Status.Rented);
		v1.addTransaction(t1);
		v1.getTransactionList().forEach((n) -> System.out.println(n));
		Assert.assertTrue(v1.getTransactionList().contains(t1));
	}

	@Test
	public void vehicleReserve() throws Exception{
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");

		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("ABD_636");
		Transaction t1= new Transaction(clientRecord, v1, "2022-09-10", "2022-10-15", Transaction.Status.Rented);
		v1.addTransaction(t1);
		v1.getTransactionList().forEach((n) -> System.out.println(n));
		Assert.assertTrue(v1.getTransactionList().contains(t1));
	}

	@Test
	public void vehicleCancel() throws Exception{
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");

		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("ABD_636");
		Transaction t1= new Transaction(clientRecord, v1, "2021-09-10", "2021-10-15", Transaction.Status.Rented);
		v1.addTransaction(t1);
		int beforecancel= v1.getTransactionList().size();
		v1.removeTransaction(t1.getTransactionId());
		int afterecancel= v1.getTransactionList().size();

		Assert.assertTrue(t1.getStatus()==Status.Cancelled);
	}

	@Test
	public void vehicleReturn() throws Exception{
		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");

		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("ABD_636");
		Transaction t1= new Transaction(clientRecord, v1, "2021-09-10", "2021-10-15", Transaction.Status.Rented);
		v1.addTransaction(t1);
		int beforecancel= v1.getTransactionList().size();
		v1.returnTransaction(t1.getTransactionId());
		int afterecancel= v1.getTransactionList().size();

		Assert.assertTrue(t1.getStatus()==Status.Returned);
	}

	@Test
	public void vehicleReturnCheck() throws Exception{

		ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");

		VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("ABD_636");
		Transaction t1= new Transaction(clientRecord, v1, "2022-09-10", "2021-10-15", Transaction.Status.Rented);
		v1.removeTransaction(t1.getTransactionId());
		v1.getTransactionList().forEach((n) -> System.out.println(n));

		Assert.assertTrue(v1.getTransactionList().size()==3);
	}

    @Test
    public void vehiclesDue() throws ParseException
    {
        List<VehicleRecord> vr= vehicleCatalog.getDueParticularDay("2019-10-22");
        int beforeadd=vr.size();
        System.out.println(beforeadd);
        ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");

        VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("ABF_636");
        Transaction t1= new Transaction(clientRecord, v1, "2019-10-21", "2019-10-22", Transaction.Status.Rented);
        v1.addTransaction(t1);
        int afteradd= vehicleCatalog.getDueParticularDay("2019-10-22").size();

        System.out.println(afteradd);

        Assert.assertFalse(beforeadd==afteradd);
    }

    @Test
    public void vehiclesDueTrue() throws ParseException
    {
        List<VehicleRecord> vr= vehicleCatalog.getDueParticularDay("2019-10-22");
        int beforeadd=vr.size();
        System.out.println(beforeadd);
        ClientRecord clientRecord = new ClientRecord("T-1234-123456-12", "Johny", "Tester", "(438) 566-9999", "2059-10-31");

        VehicleRecord v1 =  vehicleCatalog.getVehicleRecord("ABF_636");
        Transaction t1= new Transaction(clientRecord, v1, "2019-10-21", "2019-10-25", Transaction.Status.Rented);
        v1.addTransaction(t1);
        int afteradd= vehicleCatalog.getDueParticularDay("2019-10-22").size();

        System.out.println(afteradd);

        Assert.assertTrue(beforeadd==afteradd);
    }
}
