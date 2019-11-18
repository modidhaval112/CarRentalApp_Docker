package com.soen6461.carrentalapplication.mapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soen6461.carrentalapplication.model.TransactionHistory;
import com.soen6461.carrentalapplication.tabledatagateway.TransactionHistoryTdg;

@Component
public class TransactionHistoryMapper implements IDataMapper<TransactionHistory> {

    @Autowired
    private TransactionHistoryTdg transactionHistoryTdg;

    @Autowired
    private TransactionDataMapper tdm;

    public TransactionHistoryMapper() {
    }

    /**
     * Save the data in the database to persist it.
     *
     * @param objectToSave Object to save in the database.
     * @return True if the operation was a success, false otherwise.
     */
    @Override
    public boolean save(TransactionHistory objectToSave) {
        return false;
    }

    /**
     * Insert an object in the database to persist it.
     *
     * @param objectToInsert The object record to insert in the database.
     * @return True if the operation was a success, false otherwise.
     */
    @Override
    public boolean insert(TransactionHistory objectToInsert) {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        return transactionHistoryTdg.insert(objectToInsert.getTransactionId(), objectToInsert.getVehicleType(),
                objectToInsert.getModel(), objectToInsert.getLpr(), objectToInsert.getClientName(),
                df.format(objectToInsert.getStartDate().getTime()).toString(),
                df.format(objectToInsert.getEndDate().getTime()).toString(), objectToInsert.getStatus(),
                objectToInsert.getTimeStamp(), objectToInsert.getColor(), objectToInsert.getMake(),
                objectToInsert.getYear());
    }

    /**
     * Update an object in the database to persist it.
     *
     * @param objectToUpdate The object record to update in the database.
     * @return True if the operation was a success, false otherwise.
     */
    @Override
    public boolean update(TransactionHistory objectToUpdate) {
        return false;
    }

    /**
     * Method to delete a record from the database
     *
     * @param id Id of the record to delete from the database.
     * @return True if the operation was a success, false otherwise.
     */
    @Override
    public boolean delete(String id) {
        return false;
    }

    /**
     * Method to retrieve an object from the database.
     *
     * @param id The id of the object to retrieve from the database.
     * @return The object mapping to the given id.
     */
    @Override
    public TransactionHistory find(String id) {
        return null;
    }

    /**
     * Get all object records from the database.
     *
     * @return The list of objects from the database.
     */
    @Override
    public List<TransactionHistory> findAll() {
        List<TransactionHistory> transactionsHistory = new ArrayList<>();
        List<Map<String, Object>> records = transactionHistoryTdg.findAll();

        if (records != null) {
            for (int i = 0; i < records.size(); i++) {
                TransactionHistory transactionHistory = null;
                try {
                    transactionHistory = new TransactionHistory(records.get(i).get("transactionId").toString(),
                            records.get(i).get("vehicleType").toString(), records.get(i).get("model").toString(),
                            records.get(i).get("lpr").toString(), records.get(i).get("clientName").toString(),
                            records.get(i).get("startDate").toString(), records.get(i).get("endDate").toString(),
                            records.get(i).get("status").toString(), records.get(i).get("timestamp").toString(),
                            records.get(i).get("color").toString(), records.get(i).get("make").toString(),
                            Integer.parseInt(records.get(i).get("year").toString()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                transactionsHistory.add(transactionHistory);
            }
        }

        return transactionsHistory;
    }
}
