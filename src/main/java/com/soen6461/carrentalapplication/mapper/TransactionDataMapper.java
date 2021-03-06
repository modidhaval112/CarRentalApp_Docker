package com.soen6461.carrentalapplication.mapper;

import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.tabledatagateway.TransactionTdg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TransactionDataMapper implements IDataMapper<Transaction> {
    @Autowired
    private TransactionTdg transactionTdg;

    @Autowired
    VehicleRecordDataMapper vrdm;

    @Autowired
    ClientRecordDataMapper crdm;

    /**
     * Transaction record insert mapper
     *
     * @param transactionRecord The transaction record to insert in the database.
     * @return True if the operation was a success, false otherwise.
     */
    public boolean insert(Transaction transactionRecord) {
        return transactionTdg.insert(1, transactionRecord.getTransactionId(), transactionRecord.getStatus().toString(),
                transactionRecord.getStartDateObject(), transactionRecord.getEndDateObject(), transactionRecord.getVehicleRecord().getLpr(),
                transactionRecord.getClientRecord().getDriversLicenseNumber());
    }

    /**
     * Delete the Transaction record.
     *
     * @param id The transaction id to delete.
     * @return True if the operation was a success, false otherwise.
     */
    @Override
    public boolean delete(String id) {
        return false;
    }

    /**
     * Method to update an object data in the database.
     *
     * @param transactionRecord Object to update.
     * @return True if the operation was a success, false otherwise.
     */
    @Override
    public boolean update(Transaction transactionRecord) {
        return transactionTdg.delete(transactionRecord.getTransactionId());
    }

    /**
     * Get all Transaction records.
     *
     * @return True if the operation was a success, false otherwise.
     */
    public List findAll() {
        List<Transaction> transactions = new ArrayList<>();
        List<Map<String, Object>> records = transactionTdg.findAll();

        if (records != null) {
            for (int i = 0; i < records.size(); i++) {
                Transaction transaction = new Transaction(Integer.parseInt(records.get(i).get("version").toString()),
                        crdm.find(records.get(i).get("driversLicenseNumber").toString()),
                        vrdm.find(records.get(i).get("licensePlateNumber").toString()),
                        records.get(i).get("startDate").toString(),
                        records.get(i).get("endDate").toString(),
                        Transaction.getStatus((records.get(i).get("status").toString())));
                transactions.add(transaction);
            }
        }

        return transactions;
    }

    /**
     * Find all transactions related to a vehicle license plate number.
     *
     * @param lpr A vehicle license plate number.
     * @return A list of transactions.
     */
    public List findAll(String lpr) {
        try {
            List<Transaction> transactions = new ArrayList<>();
            List<Map<String, Object>> records = transactionTdg.findAll(lpr);

            if (records != null) {
                for (int i = 0; i < records.size(); i++) {

                    Transaction transaction = new Transaction(records.get(i).get("transactionId").toString(), Integer.parseInt(records.get(i).get("version").toString()),
                            crdm.findclient(records.get(i).get("driversLicenseNumber").toString()),
                            vrdm.findVehicle(records.get(i).get("licensePlateNumber").toString()),
                            records.get(i).get("startDate").toString(),
                            records.get(i).get("endDate").toString(),
                            Transaction.getStatus((records.get(i).get("status").toString())));
                    transactions.add(transaction);
                }
            }

            return transactions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Save the transaction.
     *
     * @param objectToSave Object to save in the database.
     * @return True if the transaction is saved, false otherwise.
     */
    @Override
    public boolean save(Transaction objectToSave) {
        return false;
    }

    /**
     * Find transaction
     *
     * @param transactionId The id of the transaction.
     * @return The transaction.
     * @throws NumberFormatException Number formatting exceptions.
     * @throws ParseException        Parsing exceptions.
     * @throws SQLException          Exception related to sql.
     */
    public Transaction findTransaction(String transactionId) throws NumberFormatException, ParseException, SQLException {

        Map<String, Object> record = transactionTdg.findTransaction(transactionId);

        if (record == null || record.isEmpty()) {
            return null;
        }

        Transaction transaction = new Transaction(record.get("transactionId").toString(), Integer.parseInt(record.get("version").toString()),
                crdm.findclient(record.get("driversLicenseNumber").toString()),
                vrdm.findVehicle(record.get("licensePlateNumber").toString()),
                record.get("startDate").toString(),
                record.get("endDate").toString(),
                Transaction.getStatus((record.get("status").toString())));

        return transaction;
    }

    /**
     * Find transaction.
     *
     * @param id The id of the object to retrieve from the database.
     * @return The transaction matching the id.
     */
    @Override
    public Transaction find(String id) {
        return null;
    }
}