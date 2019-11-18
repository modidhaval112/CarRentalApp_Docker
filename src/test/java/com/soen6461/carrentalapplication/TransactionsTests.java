package com.soen6461.carrentalapplication;

import java.text.ParseException;
import java.util.List;

import com.soen6461.carrentalapplication.controller.ClientController;
import com.soen6461.carrentalapplication.controller.TransactionCatalog;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionsTests {
	@Autowired private VehicleCatalog vehicleCatalog;
	@Autowired 
	private ClientController clientController;
	@Autowired
	private TransactionCatalog transactionCatalog;

    @Test
    public void vehiclesCurrentlyOut() throws ParseException {
        List<VehicleRecord> vr = vehicleCatalog.getCurrentlyOutVehicles();
        int beforeadd = vr.size();
        System.out.println(beforeadd);
        
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

        VehicleRecord v1 = vehicleCatalog.getVehicleRecord("ABE_636");
        Transaction t1 = new Transaction(1, clientRecord, v1, "2019-10-23", "2019-12-27", Transaction.Status.Rented);
        v1.addTransaction(t1);
		vehicleCatalog.persistData();

        transactionCatalog.persistData();
        int afteradd = vehicleCatalog.getCurrentlyOutVehicles().size();

        System.out.println(afteradd);

        Assert.assertFalse(beforeadd == afteradd);
    }

    @Test
    public void vehiclesCurrentlyOutFalse() throws ParseException {
        List<VehicleRecord> vr = vehicleCatalog.getCurrentlyOutVehicles();
        int beforeadd = vr.size();
        System.out.println(beforeadd);
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

              VehicleRecord v1 = vehicleCatalog.getVehicleRecord("ABE_636");
        Transaction t1 = new Transaction(0, clientRecord, v1, "2019-12-23", "2019-12-27", Transaction.Status.Rented);
        v1.addTransaction(t1);
        vehicleCatalog.persistData();

        transactionCatalog.persistData();
        int afteradd = vehicleCatalog.getCurrentlyOutVehicles().size();

        System.out.println(afteradd);

        Assert.assertTrue(beforeadd == afteradd);
    }

    @Test
    public void vehiclesCurrentlyAvailable() throws ParseException {
        List<VehicleRecord> vr = vehicleCatalog.getAvailabilityBetweenDates("2019-12-23", "2019-12-27");
        int beforeadd = vr.size();
        System.out.println(beforeadd);
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

              VehicleRecord v1 = vehicleCatalog.getVehicleRecord("ABE_636");
        Transaction t1 = new Transaction(0, clientRecord, v1, "2019-12-23", "2019-12-27", Transaction.Status.Rented);
        v1.addTransaction(t1);
        vehicleCatalog.persistData();

        transactionCatalog.persistData();
        int afteradd = vehicleCatalog.getAvailabilityBetweenDates("2019-12-23", "2019-12-27").size();

        System.out.println(afteradd);

        Assert.assertTrue(beforeadd == afteradd);
    }

    @Test
    public void vehiclesCurrentlyAvailableFalse() throws ParseException {
        List<VehicleRecord> vr = vehicleCatalog.getAvailabilityBetweenDates("2019-12-23", "2019-12-27");
        int beforeadd = vr.size();
        System.out.println(beforeadd);
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

              VehicleRecord v1 = vehicleCatalog.getVehicleRecord("ABE_636");
        Transaction t1 = new Transaction(0, clientRecord, v1, "2019-12-23", "2019-12-27", Transaction.Status.Rented);
        v1.addTransaction(t1);
        vehicleCatalog.persistData();

        transactionCatalog.persistData();
        int afteradd = vehicleCatalog.getAvailabilityBetweenDates("2019-12-23", "2019-12-27").size();

        System.out.println(afteradd);

        Assert.assertFalse(beforeadd != afteradd);
    }

    @Test
    public void vehiclesOverDue() throws ParseException {
        List<VehicleRecord> vr = vehicleCatalog.getOverDueParticularDay("2019-10-22");
        int beforeadd = vr.size();
        System.out.println(beforeadd);
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

              VehicleRecord v1 = vehicleCatalog.getVehicleRecord("ABE_636");       

        Transaction t1 = new Transaction(0, clientRecord, v1, "2019-10-19", "2019-10-21", Transaction.Status.Rented);
        v1.addTransaction(t1);
        vehicleCatalog.persistData();

        transactionCatalog.persistData();
        int afteradd = vehicleCatalog.getOverDueParticularDay("2019-10-22").size();

        System.out.println(afteradd);

        Assert.assertFalse(beforeadd == afteradd);
    }

    @Test
    public void vehiclesOverDueTrue() throws ParseException {
        List<VehicleRecord> vr = vehicleCatalog.getOverDueParticularDay("2019-10-22");
        int beforeadd = vr.size();
        System.out.println(beforeadd);
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

              VehicleRecord v1 = vehicleCatalog.getVehicleRecord("ABE_636");
        Transaction t1 = new Transaction(0, clientRecord, v1, "2019-10-19", "2019-10-24", Transaction.Status.Rented);
        v1.addTransaction(t1);
        vehicleCatalog.persistData();

        transactionCatalog.persistData();
        int afteradd = vehicleCatalog.getOverDueParticularDay("2019-10-22").size();

        System.out.println(afteradd);

        Assert.assertTrue(beforeadd == afteradd);
    }
}