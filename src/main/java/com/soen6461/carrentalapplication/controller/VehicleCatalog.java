package com.soen6461.carrentalapplication.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

	/**
	 * Default class constructor
	 */
	public VehicleCatalog() {
	}

	/**
	 * This method will fetch and return all the vehicle records
	 * 
	 * @return list of vehicles
	 */
	public List getAllVehicleRecord() {
		// To protect the main vehicle record list, only a copy is provided.
		// this avoids someone other than this class from adding or removing vehicles.

		List<VehicleRecord> copy = new ArrayList<VehicleRecord>();
		for (VehicleRecord vehicle : vehicleRecordList) {
			copy.add(vehicle);
		}

		return copy;
	}

	/**
	 * This method will return filtered vehicle list
	 * 
	 * @param filter filter name
	 * @param value value if that applies
	 * @return list of filtered vehicles
	 */
	public List getFilteredList(@RequestParam(name = "filter") String filter, @RequestParam(name = "value") String value) {
		return this.getResultSet(filter, value);
	}

	/**
	 * This method will return filtered vehicle for 'Greater than' scenario
	 * 
	 * @param filter filter name
	 * @param value value if that applies
	 * @return list of filtered vehicles
	 */
	public List getGreaterThanFilteredList(@RequestParam(name = "filter") String filter, @RequestParam(name = "value") String value) {
		return this.getResultSet(filter, value);
	}

	/**
	 * This method will return filtered vehicle for 'Less than' scenario
	 * 
	 * @param filter filter name
	 * @param value value if that applies
	 * @return list of filtered vehicles
	 */
	public List getLesserThanFilteredList(@RequestParam(name = "filter") String filter, @RequestParam(name = "value") String value) {
		return this.getResultSet(filter, value);
	}

	/**
	 * This method will return filtered vehicle list.
	 * 
	 * @param filter filter name
	 * @param value value if that applies
	 * @return list of filtered vehicles
	 */
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

	/**
	 * This method is designed to assign vehicles to client
	 *  
	 * @param driversLicense driver's license
	 * @param licensePlateRecord vehicle's number plate
	 * @param startDate start date
	 * @param endDate end date
	 * @param status status
	 */
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

	/**
	 * This method is designed for handling return transactions
	 * 
	 * @param transactionId
	 * @param licensePlateRecord
	 */
	public void returnTransaction(String transactionId, String licensePlateRecord) {
		VehicleRecord selectedVehicle = this.getVehicleRecord(licensePlateRecord);
		selectedVehicle.returnTransaction(transactionId);
	}

	/**
	 * This method is designed for handling cancel transactions
	 * 
	 * @param licensePlateRecord
	 * @param transactionId
	 * @param redirectAttributes
	 * @return
	 */
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

	/**
	 * This method will return filtered transaction list
	 * 
	 * @param filter filter name
	 * @param value value if that applies
	 * @return list of transactions
	 */
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

	/**
	 * THis method will return all the transactions
	 * 
	 * @return list of transactions
	 */
	public List<Transaction> getAllTransactions() {
		List<Transaction> transList = new ArrayList<>();
		for (VehicleRecord v : vehicleRecordList) {
			transList.addAll(v.getTransactionList());
		}
		return transList;
	}

	/**
	 * This method is designed for searching the vehicle
	 * 
	 * @param lpr
	 * @return vehicle record
	 */
	public VehicleRecord searchVehicle(String lpr) {
		VehicleRecord vehicle = null;
		for (VehicleRecord v : vehicleRecordList) {
			if (v.getLpr().equalsIgnoreCase(lpr)) {
				vehicle = v;
			}

		}
		return vehicle;
	}

	/**
	 * This method is designed for deleting the vehicle
	 * 
	 * @param lpr license plate number
	 */
	public void deleteVehicleRecord(String lpr) {
		//		for (int i = 0; i < vehicleRecordList.size(); i++) {
		//			if (vehicleRecordList.get(i).getLpr().equals(lpr)) {
		vehicleRecordList.remove(searchVehicle(lpr));
		//			}
		//		}
	}

	/**
	 * This method is designed for updating the vehicle record
	 * 
	 * @param vehicleRecord vehicle record
	 * @param lpr license plate number
	 */
	public void updateVehicleRecord(VehicleRecord vehicleRecord, String lpr) {
		for (int i = 0; i < vehicleRecordList.size(); i++) {
			if (vehicleRecordList.get(i).getLpr().equals(lpr)) {
				vehicleRecordList.set(i,vehicleRecord);
			}
		}
	}


	/**
	 * This method is designed for fetching available vehicles between two dates
	 * 
	 * @param startdate start date
	 * @param enddate end date
	 * @return list of vehicles
	 * @throws ParseException
	 */
	public List<VehicleRecord> getAvailablabilityBetweenDates(String startdate, String enddate) throws ParseException {
		List<VehicleRecord> temp = new ArrayList<>();


		for (int i = 0; i < vehicleRecordList.size(); i++) {
			HashMap<String,String> vehStatus= new HashMap<String,String>();

			List<Transaction> trans =vehicleRecordList.get(i).getTransactionList();
			Date  sd = new Date();
			Date  ed = new Date();
			boolean transflag=false;

			for(Transaction t:trans)
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				sd=sdf.parse(startdate);
				ed=sdf.parse(enddate);
				if((sd.compareTo(sdf.parse(t.getEndDate()))>0 || ed.compareTo(sdf.parse(t.getStartDate()))<0 && (t.getStatus().equals(Transaction.Status.Rented) || t.getStatus().equals(Transaction.Status.Reserved)) ) || t.getStatus().equals(Transaction.Status.Available) ||t.getStatus().equals(Transaction.Status.Cancelled)||t.getStatus().equals(Transaction.Status.Returned))
				{
					transflag=true;
				}
				else
				{
					transflag=false;
					break;
				}
			}
			if(trans.size()==0)
			{
				transflag=true;

			}
			if(transflag)
			{
				vehStatus.put(vehicleRecordList.get(i).getLpr(),"Available");
				temp.add(vehicleRecordList.get(i));
			}
			else
			{
				vehStatus.put(vehicleRecordList.get(i).getLpr(),"NotAvailable");

			}
		}
		return temp;
	}

	/**
	 * This method is designed for fetching overdue vehicles after
	 * particular date
	 * 
	 * @param vehicledate particular date
	 * @return list of vehicle
	 * @throws ParseException
	 */
	public List<VehicleRecord> getOverDueParticularDay(String vehicledate) throws ParseException{
		List<VehicleRecord> temp = new ArrayList<>();
		Date  d1 = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		d1=sdf.parse(vehicledate);

		for (int i = 0; i < vehicleRecordList.size(); i++) {


			for (Transaction t :  vehicleRecordList.get(i).getVehicleTransactionList()) {
				if (sdf.parse(t.getEndDate() ).compareTo(d1)<0 && (t.getStatus().equals(Transaction.Status.Rented) || t.getStatus().equals(Transaction.Status.Reserved))) {
					temp.add(t.getVehicleRecord());
					break;
				}
			}

		}
		return temp;

	}

	/**
	 * This method is designed for fetching due vehicles on
	 * particular date
	 * 
	 * @param vehicledate particular date
	 * @return list of vehicle
	 * @throws ParseException
	 */
	public List<VehicleRecord> getDueParticularDay(String vehicledate) throws ParseException{
		List<VehicleRecord> temp = new ArrayList<>();
		Date  d1 = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		d1=sdf.parse(vehicledate);

		for (int i = 0; i < vehicleRecordList.size(); i++) {


			for (Transaction t :  vehicleRecordList.get(i).getVehicleTransactionList()) {
				if (sdf.parse(t.getEndDate() ).compareTo(d1)==0 && (t.getStatus().equals(Transaction.Status.Rented) || t.getStatus().equals(Transaction.Status.Reserved))) {
					temp.add(t.getVehicleRecord());
					break;
				}
			}

		}
		return temp;

	}

	/**
	 * This method is designed for fetching vehicles are not available
	 * 
	 * @return list of vehicles
	 * @throws ParseException
	 */
	public List<VehicleRecord> getCurrentlyOutVehciles() throws ParseException{
		List<VehicleRecord> temp = new ArrayList<>();
		Date  d1 = new Date();
		Date cd = new Date();  


		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		cd=sdf.parse(sdf.format(cd));

		for (int i = 0; i < vehicleRecordList.size(); i++) {


			for (Transaction t :  vehicleRecordList.get(i).getVehicleTransactionList()) {
				if (sdf.parse(t.getEndDate() ).compareTo(cd)>=0 && sdf.parse(t.getStartDate() ).compareTo(cd)<=0 &&(t.getStatus().equals(Transaction.Status.Rented) || t.getStatus().equals(Transaction.Status.Reserved)))
				{
					temp.add(t.getVehicleRecord());
					break;
				}
			}

		}
		return temp;

	}

}