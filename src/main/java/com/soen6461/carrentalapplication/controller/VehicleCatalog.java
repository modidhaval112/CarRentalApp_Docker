package com.soen6461.carrentalapplication.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soen6461.carrentalapplication.model.ClientRecord;
import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.model.VehicleRecord;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class VehicleCatalog {
	@Autowired
	private ClientController clientController;
	private List<VehicleRecord> vehicleRecordList = new ArrayList<VehicleRecord>();
	private static VehicleCatalog instance = null;

	public VehicleCatalog() {
	}

	public List getAllVehicleRecord() {
		// To protect the main vehicle record list, only a copy is provided.
		// this avoids someone other than this class from adding or removing vehicles.

		List<VehicleRecord> copy = new ArrayList<VehicleRecord>();
		for (VehicleRecord vehicle : vehicleRecordList) {
			copy.add(vehicle);
		}

		return copy;
	}

	public List getFilteredList(@RequestParam(name = "filter") String filter, @RequestParam(name = "value") String value) {
		return this.getResultSet(filter, value);
	}

	public List getGreaterThanFilteredList(@RequestParam(name = "filter") String filter, @RequestParam(name = "value") String value) {
		return this.getResultSet(filter, value);
	}

	public List getLesserThanFilteredList(@RequestParam(name = "filter") String filter, @RequestParam(name = "value") String value) {
		return this.getResultSet(filter, value);
	}

	public List<VehicleRecord> getResultSet(String filter, String value) {

		List<VehicleRecord> temp = new ArrayList<>();
		if (filter.equals("make")) {
			for (int i = 0; i < vehicleRecordList.size(); i++) {
				if (vehicleRecordList.get(i).getMake().equalsIgnoreCase(value)) {
					temp.add(vehicleRecordList.get(i));
				}
			}

		} else if (filter.equals("model")) {
			for (int i = 0; i < vehicleRecordList.size(); i++) {
				if (vehicleRecordList.get(i).getModel().equalsIgnoreCase(value)) {
					temp.add(vehicleRecordList.get(i));
				}

			}
		} else if (filter.equals("carType")) {
			for (int i = 0; i < vehicleRecordList.size(); i++) {
				if (vehicleRecordList.get(i).getCarType().equals(value)) {
					temp.add(vehicleRecordList.get(i));
				}
			}
		} else if (filter.equals("color")) {
			for (int i = 0; i < vehicleRecordList.size(); i++) {
				if (vehicleRecordList.get(i).getColor().equalsIgnoreCase(value)) {
					temp.add(vehicleRecordList.get(i));
				}

			}

		} else if (filter.equals("greater")) {
			for (int i = 0; i < vehicleRecordList.size(); i++) {
				if (vehicleRecordList.get(i).getYear() >= Integer.parseInt(value)) {
					temp.add(vehicleRecordList.get(i));
				}

			}

		} else if (filter.equals("lesser")) {
			for (int i = 0; i < vehicleRecordList.size(); i++) {
				if (vehicleRecordList.get(i).getYear() <= Integer.parseInt(value)) {
					temp.add(vehicleRecordList.get(i));
				}

			}

		}
		return temp;
	}

	/**
	 * Gets a vehicle that has the give license plate number.
	 *
	 * @param licensePlateNumber The license plate number.
	 */
	public VehicleRecord getVehicleRecord(String licensePlateNumber) {
		for (int i = 0; i < vehicleRecordList.size(); i++) {
			if (vehicleRecordList.get(i).getLpr().equals(licensePlateNumber)) {
				return vehicleRecordList.get(i);
			}
		}

		return null;
	}

	/**
	 * Add vehicle records to the catalog //TODO: Protect this method against
	 * unauthorised access from clerk.
	 *
	 * @param vehicleRecord
	 */
	public void addVehicleRecord(VehicleRecord vehicleRecord) {
		for (VehicleRecord existingVehicleRecord: this.vehicleRecordList) {
			if (vehicleRecord.getLpr() == existingVehicleRecord.getLpr())
			{
				// throw new Exception("There is already a vehicle with license registration plate: " + vehicleRecord.getLpr() + " in the catalog.");
				return;
			}
		}

		this.vehicleRecordList.add(vehicleRecord);
	}

	public void assignVehicle(String driversLicense, String licensePlateRecord, String startDate, String endDate,
							  String status) {

		ClientRecord forClient = clientController.searchClient(driversLicense);
		VehicleRecord seletctedVehicle = this.getVehicleRecord(licensePlateRecord);

		if (status.equals("Rented")) {
			Transaction newTransaction = new Transaction(forClient, seletctedVehicle, startDate, endDate,
					Transaction.Status.Rented);
			seletctedVehicle.addTransaction(newTransaction);
		} else if (status.equals("Reserved")) {
			Transaction newTransaction = new Transaction(forClient, seletctedVehicle, startDate, endDate,
					Transaction.Status.Reserved);
			seletctedVehicle.addTransaction(newTransaction);
		} else {
			Transaction newTransaction = new Transaction(forClient, seletctedVehicle, startDate, endDate,
					Transaction.Status.Available);
			seletctedVehicle.addTransaction(newTransaction);
		}
	}

	public void returnTransaction(String transactionId, String licensePlateRecord) {
		VehicleRecord selectedVehicle = this.getVehicleRecord(licensePlateRecord);
		selectedVehicle.returnTransaction(transactionId);
	}

	public RedirectAttributes cancelTransaction(String licensePlateRecord, String transactionId, RedirectAttributes redirectAttributes) {
		VehicleRecord selectedVehicle = this.getVehicleRecord(licensePlateRecord);
		List<Transaction> transactionList = selectedVehicle.getVehicleTransactionList();
		for (int i = 0; i < transactionList.size(); i++) {
			if (transactionList.get(i).getTransactionId().equalsIgnoreCase(transactionId)) {
				if (transactionList.get(i).getStatus().toString().equals("Rented")) {
					redirectAttributes.addFlashAttribute("warningMsg",
							"  Transaction can not be cancelled as vehicle is already Rented.");
				} else {
					selectedVehicle.removeTransaction(transactionId);
					redirectAttributes.addFlashAttribute("warningMsg", "  Transaction has been cancelled.");
				}
				break;
			}

		}

		return redirectAttributes;
	}

	public List getFilteredTransactionList(@RequestParam(name = "filter") String filter,
										   @RequestParam(name = "value") String value) {
		return this.getTransactionSet(filter, value);
	}

	public List<Transaction> getTransactionSet(String filter, String value) {

		List<Transaction> temp = new ArrayList<>();
		if (filter.equals("vehicle-make")) {
			for (int i = 0; i < vehicleRecordList.size(); i++) {
				if (vehicleRecordList.get(i).getMake().equalsIgnoreCase(value)) {
					temp = (vehicleRecordList.get(i).getTransactionList());
				}
			}

		} else if (filter.equals("vehicle-model")) {
			for (int i = 0; i < vehicleRecordList.size(); i++) {
				if (vehicleRecordList.get(i).getModel().equalsIgnoreCase(value)) {
					temp = (vehicleRecordList.get(i).getTransactionList());
				}

			}
		} else if (filter.equals("car-type")) {
			for (int i = 0; i < vehicleRecordList.size(); i++) {
				if (vehicleRecordList.get(i).getCarType().equalsIgnoreCase(value)) {
					temp = (vehicleRecordList.get(i).getTransactionList());
				}
			}
		} else if (filter.equals("car-color")) {
			for (int i = 0; i < vehicleRecordList.size(); i++) {
				if (vehicleRecordList.get(i).getColor().equalsIgnoreCase(value)) {
					temp = (vehicleRecordList.get(i).getTransactionList());
				}
			}

		} else if (filter.equals("client")) {


			for (int i = 0; i < vehicleRecordList.size(); i++) {
				List<Transaction> transList = new ArrayList<>();
				transList = vehicleRecordList.get(i).getTransactionList();
				for (Transaction t : transList) {
					String firstLast = t.getClientRecord().getFirstName().toLowerCase() + " " + t.getClientRecord().getLastName().toLowerCase();
					if (t.getClientRecord().getFirstName().toLowerCase().contains(value) || t.getClientRecord().getLastName().toLowerCase().contains(value) || firstLast.contains(value)) {
						temp.add(t);
					}
				}
			}
		}

		return temp;
	}

	public List<Transaction> getAllTransactions() {
		List<Transaction> transList = new ArrayList<>();
		for (VehicleRecord v : vehicleRecordList) {
			transList.addAll(v.getTransactionList());
		}
		return transList;
	}

	public VehicleRecord searchVehicle(String lpr) {
		VehicleRecord vehicle = null;
		for (VehicleRecord v : vehicleRecordList) {
			if (v.getLpr().equalsIgnoreCase(lpr)) {
				vehicle = v;
			}

		}
		return vehicle;
	}

	public void deleteVehicleRecord(String lpr) {
		for (int i = 0; i < vehicleRecordList.size(); i++) {
			if (vehicleRecordList.get(i).getLpr().equals(lpr)) {
				vehicleRecordList.remove(vehicleRecordList.get(i));
			}
		}
	}

	public void updateVehicleRecord(VehicleRecord vehicleRecord, String lpr) {
		for (int i = 0; i < vehicleRecordList.size(); i++) {
			if (vehicleRecordList.get(i).getLpr().equals(lpr)) {
				vehicleRecordList.set(i,vehicleRecord);
			}
		}
	}
}