package com.soen6461.carrentalapplication.controller;

import com.soen6461.carrentalapplication.model.Record;
import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.model.TransactionHistory;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class TransactionCatalog {

    Record record = new Record();
    private List<TransactionHistory> transactionHistoryList = new ArrayList<TransactionHistory>();

    public List getFilteredTransactionHistory(String filter,
                                              String value) {
        return this.getTransactionSet(filter, value);
    }

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

    public List<TransactionHistory> getAllTransactionHistory() {
        List<TransactionHistory> temp = new ArrayList<>();
        for (TransactionHistory t : record.getAllTransactionHistory()) {
            temp.add(t);
        }
        return temp;
    }

    public List<TransactionHistory> getDueTodayTransactionHistory() {
        List<TransactionHistory> temp = new ArrayList<>();
        Date date = new Date();
        for (TransactionHistory t : record.getAllTransactionHistory()) {
            if (t.getTransaction().getEndDate().equals(date.getDate())) {
                temp.add(t);
            }
        }
        return temp;
    }

    public List<TransactionHistory> getOverDueTransactionHistory() {
        List<TransactionHistory> temp = new ArrayList<>();
        Date date = new Date();
        for (TransactionHistory t : record.getAllTransactionHistory()) {
        	// check if end date is less than today and check if the status is rented or reserved
            if (t.getTransaction().getEndDate().getTime() <= date.getTime() && (t.getTransaction().getStatus().equals(Transaction.Status.Reserved) || t.getTransaction().getStatus().equals(Transaction.Status.Rented))) {
                temp.add(t);
            }
        }
        return temp;
    }
}
