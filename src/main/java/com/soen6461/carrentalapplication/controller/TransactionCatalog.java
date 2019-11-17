package com.soen6461.carrentalapplication.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.soen6461.carrentalapplication.mapper.TransactionDataMapper;
import com.soen6461.carrentalapplication.mapper.TransactionHistoryMapper;
import com.soen6461.carrentalapplication.unitofwork.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.soen6461.carrentalapplication.model.Record;
import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.model.TransactionHistory;
import com.soen6461.carrentalapplication.model.VehicleRecord;

/**
 * This is class designed for displaying transaction for Admin role
 *
 * @author Admin
 */
@RestController
public class TransactionCatalog {
    @Autowired
    private Record record;
    @Autowired
    private TransactionHistoryMapper transactionHistoryMapper;
    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    public TransactionCatalog() {
    }

    /**
     * This method will return filtered transaction list
     *
     * @param filter filter name
     * @param value  value if that applies
     * @return list of transactions
     */
    public List getFilteredTransactionHistory(String filter, String value) {
        return this.getTransactionSet(filter, value);
    }

    /**
     * This method will return filtered transaction list
     *
     * @param filter filter name
     * @param value  value if that applies
     * @return list of transactions
     */
    public List<TransactionHistory> getTransactionSet(String filter, String value) {

        List<TransactionHistory> temp = new ArrayList<>();
        System.out.println(filter + " " + value);
        if (filter.equals("vehicle-model")) {
            for (int i = 0; i < record.getAllTransactionHistory().size(); i++) {
                if (record.getAllTransactionHistory().get(i).getTransaction().getVehicleRecord().getMake().equalsIgnoreCase(value)) {
                    temp.add(record.getAllTransactionHistory().get(i));
                }
            }
        } else if (filter.equals("car-type")) {
            for (int i = 0; i < record.getAllTransactionHistory().size(); i++) {
                if (record.getAllTransactionHistory().get(i).getTransaction().getVehicleRecord().getCarType().equals(value)) {
                    temp.add(record.getAllTransactionHistory().get(i));
                }
            }
        } else if (filter.equals("car-color")) {
            for (int i = 0; i < record.getAllTransactionHistory().size(); i++) {
                if (record.getAllTransactionHistory().get(i).getTransaction().getVehicleRecord().getColor().equalsIgnoreCase(value)) {
                    temp.add(record.getAllTransactionHistory().get(i));
                }
            }
        } else if (filter.equals("client")) {

            for (int i = 0; i < record.getAllTransactionHistory().size(); i++) {

                for (TransactionHistory t : record.getAllTransactionHistory()) {
                    String firstLast = t.getTransaction().getClientRecord().getFirstName().toLowerCase() + " " + t.getTransaction().getClientRecord().getLastName().toLowerCase();
                    if (t.getTransaction().getClientRecord().getFirstName().toLowerCase().contains(value) || t.getTransaction().getClientRecord().getLastName().toLowerCase().contains(value) || firstLast.contains(value)) {
                        temp.add(t);
                    }
                }
            }
        }

        return temp;
    }

    /**
     * This method will return all the transactions
     *
     * @return transaction list
     */
    public List<TransactionHistory> getAllTransactionHistory() {

        return new ArrayList<>(record.getAllTransactionHistory());
    }

    /**
     * Method developed to filter out overdue transactions
     *
     * @return filtered transaction list
     */
    public List<TransactionHistory> getOverDueTransactionHistory() {
        List<TransactionHistory> temp = new ArrayList<>();
        Date date = new Date();
        Instant inst = date.toInstant();
        LocalDate localDate = inst.atZone(ZoneId.systemDefault()).toLocalDate();
        Instant dayInst = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date day = Date.from(dayInst);

        for (TransactionHistory t : record.getAllTransactionHistory()) {
            if ((t.getTransaction().getEndDateObject().compareTo(day) == -1)
                    && (t.getTransaction().getStatus().equals(Transaction.Status.Reserved)
                    || t.getTransaction().getStatus().equals(Transaction.Status.Rented))) {
                temp.add(t);
            }
        }

        return temp;
    }

    /**
     * Method developed to filter out due today transactions
     *
     * @return filtered transaction list
     */
    public List<TransactionHistory> getDueTodayTransactionHistory() {
        List<TransactionHistory> temp = new ArrayList<>();
        Date date = new Date();
        Instant inst = date.toInstant();
        LocalDate localDate = inst.atZone(ZoneId.systemDefault()).toLocalDate();
        Instant dayInst = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date day = Date.from(dayInst);
        for (TransactionHistory t : record.getAllTransactionHistory()) {
            // check if end date is equal to today and check if the status is rented or reserved
            if (t.getTransaction().getEndDateObject().compareTo(day) == 0 && (t.getTransaction().getStatus().equals(Transaction.Status.Reserved) || t.getTransaction().getStatus().equals(Transaction.Status.Rented))) {
                temp.add(t);
            }
        }

        return temp;
    }

    /**
     * Method developed to filter out due transaction for particular day
     *
     * @param vehicleDate particular date
     * @return filtered transaction list
     * @throws ParseException
     */
    public List<VehicleRecord> getDueParticularDay(String vehicleDate) throws ParseException {
        List<VehicleRecord> temp = new ArrayList<>();
        Date d1 = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        d1 = sdf.parse(vehicleDate);

        for (Transaction t : record.getTransactionList()) {
            // check if end date is equal to today and check if the status is rented or reserved
            if (sdf.parse(t.getEndDate()).compareTo(d1) == 0 || t.getStatus().equals(Transaction.Status.Rented)) {
                temp.add(t.getVehicleRecord());
            }
        }

        return temp;
    }

    /**
     * Method developed to filter out overdue transaction for particular day
     *
     * @param vehicleDate particular date
     * @return filtered transaction list
     * @throws ParseException
     */
    public List<VehicleRecord> getOverDueParticularDay(String vehicleDate) throws ParseException {
        List<VehicleRecord> temp = new ArrayList<>();
        Date d1 = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        d1 = sdf.parse(vehicleDate);


        for (TransactionHistory t : record.getAllTransactionHistory()) {
            // check if end date is equal to today and check if the status is rented or reserved
            if (sdf.parse(t.getTransaction().getEndDate()).compareTo(d1) > 0 || t.getTransaction().getStatus().equals(Transaction.Status.Rented)) {
                temp.add(t.getTransaction().getVehicleRecord());
            }
        }

        return temp;
    }

    public void loadTransactionHistory(){
        Record.transactionHistory = transactionHistoryMapper.findAll();

    }

    public  void persistData(){
        transactionHistoryRepository.commit();
    }

}