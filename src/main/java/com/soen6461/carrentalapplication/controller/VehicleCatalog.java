package com.soen6461.carrentalapplication.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.soen6461.carrentalapplication.Helpers.DataValidationHelper;
import com.soen6461.carrentalapplication.mapper.TransactionDataMapper;
import com.soen6461.carrentalapplication.model.TransactionHistory;
import com.soen6461.carrentalapplication.unitofwork.TransactionHistoryRepository;
import com.soen6461.carrentalapplication.unitofwork.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soen6461.carrentalapplication.mapper.VehicleRecordDataMapper;
import com.soen6461.carrentalapplication.model.ClientRecord;
import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.model.VehicleRecord;
import com.soen6461.carrentalapplication.unitofwork.VehicleRepository;

@RestController
public class VehicleCatalog {
    @Autowired
    private ClientController clientController;

    @Autowired
    private VehicleRecordDataMapper vehicleRecordDataMapper;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private TransactionDataMapper transactionDataMapper;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    private List<VehicleRecord> vehicleRecordList = new ArrayList<VehicleRecord>();
    private static VehicleCatalog instance = null;

    void loadVehicleRecords() throws ParseException, SQLException {
        this.vehicleRecordList = this.vehicleRecordDataMapper.findAll();
    }

    public List<VehicleRecord> loadTransactions() {
        for (int i = 0; i < vehicleRecordList.size(); i++) {
            vehicleRecordList.get(i).transactionList = transactionDataMapper.findAll(vehicleRecordList.get(i).getLpr());
        }

        return vehicleRecordList;

    }

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
    public List<VehicleRecord> getAllVehicleRecord() {
        // To protect the main vehicle record list, only a copy is provided.
        // this avoids someone other than this class from adding or removing vehicles.
        List<VehicleRecord> copy = new ArrayList<VehicleRecord>();
        try {
            copy.addAll(this.vehicleRecordDataMapper.findAll());
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return copy;
    }

    /**
     * This method will return filtered vehicle list
     *
     * @param filter filter name
     * @param value  value if that applies
     * @return list of filtered vehicles
     */
    public List getFilteredList(@RequestParam(name = "filter") String filter,
                                @RequestParam(name = "value") String value) {
        return this.getResultSet(filter, value);
    }

    /**
     * This method will return filtered vehicle for 'Greater than' scenario
     *
     * @param filter filter name
     * @param value  value if that applies
     * @return list of filtered vehicles
     */
    public List getGreaterThanFilteredList(@RequestParam(name = "filter") String filter,
                                           @RequestParam(name = "value") String value) {
        return this.getResultSet(filter, value);
    }

    /**
     * This method will return filtered vehicle for 'Less than' scenario
     *
     * @param filter filter name
     * @param value  value if that applies
     * @return list of filtered vehicles
     */
    public List getLesserThanFilteredList(@RequestParam(name = "filter") String filter,
                                          @RequestParam(name = "value") String value) {
        return this.getResultSet(filter, value);
    }

    /**
     * This method will return filtered vehicle list.
     *
     * @param filter filter name
     * @param value  value if that applies
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
        for (VehicleRecord existingVehicleRecord : this.vehicleRecordList) {
            if (vehicleRecord.getLpr() == existingVehicleRecord.getLpr()) {
                // throw new Exception("There is already a vehicle with license registration
                // plate: " + vehicleRecord.getLpr() + " in the catalog.");
                return;
            }
        }

        this.vehicleRecordList.add(vehicleRecord);
        vehicleRepository.registerNew(vehicleRecord);
    }

    /**
     * This method is designed to assign vehicles to client
     *
     * @param driversLicense     driver's license
     * @param licensePlateRecord vehicle's number plate
     * @param startDate          start date
     * @param endDate            end date
     * @param status             status
     */
    public void assignVehicle(String driversLicense, String licensePlateRecord, String startDate, String endDate,
                              String status) {

        ClientRecord forClient = clientController.searchClient(driversLicense);
        VehicleRecord selectedVehicle = this.getVehicleRecord(licensePlateRecord);

        if (status.equals("Rented")) {
            Transaction newTransaction = new Transaction(1, forClient, selectedVehicle, startDate, endDate,
                    Transaction.Status.Rented);
            selectedVehicle.addTransaction(newTransaction);
            transactionRepository.registerNew(newTransaction);
            TransactionHistory transactionHistory = new TransactionHistory(newTransaction.getTransactionId(),
                    newTransaction.getVehicleRecord().getCarType(), newTransaction.getVehicleRecord().getModel(),
                    newTransaction.getVehicleRecord().getLpr(), newTransaction.getClientRecord().getFirstName(),
                    newTransaction.getStartDate(), newTransaction.getEndDate(), newTransaction.getStatus().toString(),
                    dtf.format(now).toString(), newTransaction.getVehicleRecord().getColor(), newTransaction.getVehicleRecord().getMake(),
                    newTransaction.getVehicleRecord().getYear());
            transactionHistoryRepository.registerNew(transactionHistory);

        } else if (status.equals("Reserved")) {
            Transaction newTransaction = new Transaction(1, forClient, selectedVehicle, startDate, endDate,
                    Transaction.Status.Reserved);
            selectedVehicle.addTransaction(newTransaction);
            transactionRepository.registerNew(newTransaction);
            TransactionHistory transactionHistory = new TransactionHistory(newTransaction.getTransactionId(),
                    newTransaction.getVehicleRecord().getCarType(), newTransaction.getVehicleRecord().getModel(),
                    newTransaction.getVehicleRecord().getLpr(), newTransaction.getClientRecord().getFirstName(),
                    newTransaction.getStartDate(), newTransaction.getEndDate(), newTransaction.getStatus().toString(),
                    dtf.format(now).toString(), newTransaction.getVehicleRecord().getColor(), newTransaction.getVehicleRecord().getMake(),
                    newTransaction.getVehicleRecord().getYear());
            transactionHistoryRepository.registerNew(transactionHistory);
        } else {
            Transaction newTransaction = new Transaction(1, forClient, selectedVehicle, startDate, endDate,
                    Transaction.Status.Available);
            selectedVehicle.addTransaction(newTransaction);
            transactionRepository.registerNew(newTransaction);
            TransactionHistory transactionHistory = new TransactionHistory(newTransaction.getTransactionId(),
                    newTransaction.getVehicleRecord().getCarType(), newTransaction.getVehicleRecord().getModel(),
                    newTransaction.getVehicleRecord().getLpr(), newTransaction.getClientRecord().getFirstName(),
                    newTransaction.getStartDate(), newTransaction.getEndDate(), newTransaction.getStatus().toString(),
                    dtf.format(now).toString(), newTransaction.getVehicleRecord().getColor(), newTransaction.getVehicleRecord().getMake(),
                    newTransaction.getVehicleRecord().getYear());
            transactionHistoryRepository.registerNew(transactionHistory);
        }
    }

    /**
     * This method is designed for handling return transactions
     *
     * @param transactionId
     * @param licensePlateRecord
     */
    public boolean returnTransaction(String transactionId, String licensePlateRecord, String version) {

        int version_db = 0;
        Transaction reference = null;
        try {
            Transaction transaction = this.transactionDataMapper.findTransaction(transactionId);
            reference = transaction;
            if (transaction == null) {
                return false;
            }

            version_db = transaction.getVersion();

        } catch (NumberFormatException | ParseException | SQLException e) {
            e.printStackTrace();
        }

        if (version_db == Integer.parseInt(version)) {
            VehicleRecord selectedVehicle = this.getVehicleRecord(licensePlateRecord);
            transactionRepository.registerDirty(selectedVehicle.returnTransaction(transactionId));// TODO: Make it
            // register Delete
            TransactionHistory transactionHistory = new TransactionHistory(reference.getTransactionId(),
                    reference.getVehicleRecord().getCarType(), reference.getVehicleRecord().getModel(),
                    reference.getVehicleRecord().getLpr(), reference.getClientRecord().getFirstName(),
                    reference.getStartDate(), reference.getEndDate(), "Returned", dtf.format(now).toString(),
                    reference.getVehicleRecord().getColor(), reference.getVehicleRecord().getMake(),
                    reference.getVehicleRecord().getYear());
            transactionHistoryRepository.registerNew(transactionHistory);
            return true;
        }

        return false;

    }

    /**
     * This method is designed for handling cancel transactions
     *
     * @param licensePlateRecord
     * @param transactionId
     * @param redirectAttributes
     * @return
     */
    public RedirectAttributes cancelTransaction(String licensePlateRecord, String transactionId,
                                                RedirectAttributes redirectAttributes, String version) {

        int version_db = 0;
        Transaction reference = null;
        try {
            Transaction transaction = this.transactionDataMapper.findTransaction(transactionId);
            reference = transaction;
            if (transaction == null) {
                redirectAttributes.addFlashAttribute("errorMsg",
                        "  Transaction has been already returned or cancelled by another clerk.");
                return redirectAttributes;
            }

            version_db = transaction.getVersion();

        } catch (NumberFormatException | ParseException | SQLException e) {
            e.printStackTrace();
        }

        if (version_db == Integer.parseInt(version)) {
            VehicleRecord selectedVehicle = this.getVehicleRecord(licensePlateRecord);
            List<Transaction> transactionList = selectedVehicle.getVehicleTransactionList();
            for (int i = 0; i < transactionList.size(); i++) {
                if (transactionList.get(i).getTransactionId().equalsIgnoreCase(transactionId)) {
                    if (transactionList.get(i).getStatus().toString().equals("Rented")) {
                        redirectAttributes.addFlashAttribute("warningMsg",
                                "  Transaction can not be cancelled as vehicle is already Rented.");
                    } else {
                        TransactionHistory transactionHistory = new TransactionHistory(reference.getTransactionId(),
                                reference.getVehicleRecord().getCarType(), reference.getVehicleRecord().getModel(),
                                reference.getVehicleRecord().getLpr(), reference.getClientRecord().getFirstName(),
                                reference.getStartDate(), reference.getEndDate(), "Cancelled",
                                dtf.format(now).toString(), reference.getVehicleRecord().getColor(), reference.getVehicleRecord().getMake(),
                                reference.getVehicleRecord().getYear());
                        transactionHistoryRepository.registerNew(transactionHistory);
                        transactionRepository.registerDirty(selectedVehicle.removeTransaction(transactionId));
                        redirectAttributes.addFlashAttribute("warningMsg", "  Transaction has been cancelled.");
                    }
                    break;
                }
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "  Transaction has been already returned or cancelled.");
        }

        return redirectAttributes;
    }

    /**
     * This method will return filtered transaction list
     *
     * @param filter filter name
     * @param value  value if that applies
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
                List<Transaction> transList;
                transList = vehicleRecordList.get(i).getTransactionList();
                for (Transaction t : transList) {
                    String firstLast = t.getClientRecord().getFirstName().toLowerCase() + " "
                            + t.getClientRecord().getLastName().toLowerCase();
                    if (t.getClientRecord().getFirstName().toLowerCase().contains(value)
                            || t.getClientRecord().getLastName().toLowerCase().contains(value)
                            || firstLast.contains(value)) {
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
        VehicleRecord vehicleRecord = null;
        try {
            vehicleRecord = this.vehicleRecordDataMapper.findVehicle(lpr);
        } catch (NumberFormatException | ParseException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return vehicleRecord;
    }

    /**
     * This method is designed for deleting the vehicle
     *
     * @param lpr license plate number
     */
    public String deleteVehicleRecord(String lpr) {


        VehicleRecord selectedVehicle = getVehicleRecord(lpr);
        List<Transaction> transactionList = transactionDataMapper.findAll(selectedVehicle.getLpr());

        for (int j = 0; j < transactionList.size(); j++) {
            if (transactionList.get(j).getVehicleRecord().getLpr()
                    .equalsIgnoreCase(lpr)) {
                return "  To delete vehicle record all the transactions under it's must be 'Returned' or 'Cancelled'.";
            }
        }
        LinkedList<String> deleteRecords = vehicleRepository.getDeleteRecords();
        LinkedList<String> deletedVehicleRecords = vehicleRepository.getDeletedVehicleRecords();

        if (!deleteRecords.contains(lpr) && !deletedVehicleRecords.contains(lpr)) {
            VehicleRecord vehicleRecord = searchVehicle(lpr);
            vehicleRecordList.remove(vehicleRecord);
            vehicleRepository.registerDeleted(vehicleRecord);

            deleteRecords.add(lpr);
            vehicleRepository.setDeleteRecords(deleteRecords);

            return " Vehicle Record has been deleted successfully";
        }

        return " Vehicle Record has been already deleted or updated by another Admin.";
    }

    /**
     * This method is designed for updating the vehicle record
     *
     * @param vehicleRecord vehicle record
     * @param lpr           license plate number
     */
    public boolean updateVehicleRecord(VehicleRecord vehicleRecord, String lpr) {

        int version_db = 0;
        try {
            version_db = this.vehicleRecordDataMapper.findVehicle(lpr).getVersion();
        } catch (NumberFormatException | ParseException | SQLException e) {
            e.printStackTrace();
        }

        if (version_db == vehicleRecord.getVersion()
                && !vehicleRepository.getDirtyMap().containsKey(vehicleRecord.getLpr())) {

            for (int i = 0; i < vehicleRecordList.size(); i++) {
                if (vehicleRecordList.get(i).getLpr().equals(lpr)) {
                    vehicleRecordList.set(i, vehicleRecord);
                    vehicleRepository.registerDirty(vehicleRecord);
                }
            }

            Map<String, Boolean> dirtyMap = vehicleRepository.getDirtyMap();
            dirtyMap.put(vehicleRecord.getLpr(), false);
            vehicleRepository.setDirtyMap(dirtyMap);
            return true;
        }

        return false;
    }

    /**
     * This method is designed for fetching available vehicles between two dates
     *
     * @param startDate start date
     * @param endDate   end date
     * @return list of vehicles
     * @throws ParseException
     */
    public List<VehicleRecord> getAvailabilityBetweenDates(String startDate, String endDate) throws ParseException {
        List<VehicleRecord> temp = new ArrayList<>();

        for (int i = 0; i < vehicleRecordList.size(); i++) {
            HashMap<String, String> vehStatus = new HashMap<String, String>();

            List<Transaction> trans = vehicleRecordList.get(i).getTransactionList();
            Date sd = new Date();
            Date ed = new Date();
            boolean transflag = false;

            for (Transaction t : trans) {
                sd = DataValidationHelper.dateFormat.parse(startDate);
                ed = DataValidationHelper.dateFormat.parse(endDate);
                if ((sd.compareTo(DataValidationHelper.dateFormat.parse(t.getEndDate())) > 0
                        || ed.compareTo(DataValidationHelper.dateFormat.parse(t.getStartDate())) < 0
                        && (t.getStatus().equals(Transaction.Status.Rented)
                        || t.getStatus().equals(Transaction.Status.Reserved)))
                        || t.getStatus().equals(Transaction.Status.Available)
                        || t.getStatus().equals(Transaction.Status.Cancelled)
                        || t.getStatus().equals(Transaction.Status.Returned)) {
                    transflag = true;
                } else {
                    transflag = false;
                    break;
                }
            }
            if (trans.size() == 0) {
                transflag = true;
            }
            if (transflag) {
                vehStatus.put(vehicleRecordList.get(i).getLpr(), "Available");
                temp.add(vehicleRecordList.get(i));
            } else {
                vehStatus.put(vehicleRecordList.get(i).getLpr(), "NotAvailable");
            }
        }

        return temp;
    }

    /**
     * This method is designed for fetching overdue vehicles after particular date
     *
     * @param vehicleDate particular date
     * @return list of vehicle
     * @throws ParseException
     */
    public List<VehicleRecord> getOverDueParticularDay(String vehicleDate) throws ParseException {
        List<VehicleRecord> temp = new ArrayList<>();
        Date d1 = DataValidationHelper.dateFormat.parse(vehicleDate);

        for (int i = 0; i < vehicleRecordList.size(); i++) {
            for (Transaction t : vehicleRecordList.get(i).getVehicleTransactionList()) {
                if (DataValidationHelper.dateFormat.parse(t.getEndDate()).compareTo(d1) < 0
                        && (t.getStatus().equals(Transaction.Status.Rented)
                        || t.getStatus().equals(Transaction.Status.Reserved))) {
                    temp.add(t.getVehicleRecord());
                    break;
                }
            }
        }

        return temp;
    }

    /**
     * This method is designed for fetching due vehicles on particular date
     *
     * @param vehicleDate particular date
     * @return list of vehicle
     * @throws ParseException
     */
    public List<VehicleRecord> getDueParticularDay(String vehicleDate) throws ParseException {
        List<VehicleRecord> temp = new ArrayList<>();
        Date d1 = DataValidationHelper.dateFormat.parse(vehicleDate);

        for (int i = 0; i < vehicleRecordList.size(); i++) {
            for (Transaction t : vehicleRecordList.get(i).getVehicleTransactionList()) {
                if (DataValidationHelper.dateFormat.parse(t.getEndDate()).compareTo(d1) == 0
                        && (t.getStatus().equals(Transaction.Status.Rented)
                        || t.getStatus().equals(Transaction.Status.Reserved))) {
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
     * @throws ParseException Can throw an exception if an error occurred when
     *                        parsing a date.
     */
    public List<VehicleRecord> getCurrentlyOutVehicles() throws ParseException {
        List<VehicleRecord> temp = new ArrayList<>();
        Date d1 = new Date();
        Date cd = new Date();

        cd = DataValidationHelper.dateFormat.parse(DataValidationHelper.dateFormat.format(cd));

        for (int i = 0; i < vehicleRecordList.size(); i++) {
            for (Transaction t : vehicleRecordList.get(i).getVehicleTransactionList()) {
                if (DataValidationHelper.dateFormat.parse(t.getEndDate()).compareTo(cd) >= 0
                        && DataValidationHelper.dateFormat.parse(t.getStartDate()).compareTo(cd) <= 0
                        && (t.getStatus().equals(Transaction.Status.Rented)
                        || t.getStatus().equals(Transaction.Status.Reserved))) {
                    temp.add(t.getVehicleRecord());
                    break;
                }
            }
        }

        return temp;
    }

    public void persistData() {
        this.transactionRepository.commit();
        this.transactionHistoryRepository.commit();
    }

    public void adminPersistData() {
        this.vehicleRepository.commit();
    }
}
