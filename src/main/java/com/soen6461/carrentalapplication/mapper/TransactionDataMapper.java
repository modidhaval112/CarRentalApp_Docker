package com.soen6461.carrentalapplication.mapper;

import java.text.ParseException;
import java.util.List;

import com.soen6461.carrentalapplication.tabledatagateway.ITableGatewayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.tabledatagateway.TransactionTdg;

@Component
public class TransactionDataMapper implements ITableGatewayMapper<Transaction> {
    @Autowired
    private TransactionTdg transactionTdg;

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
    public boolean delete(int id) {
        return false;
    }

    /**
     * Method to update an object data in the database.
     *
     * @param id                Id of the object to map.
     * @param transactionRecord Object to update.
     */
    @Override
    public boolean update(int id, Transaction transactionRecord) {
        return transactionTdg.update(id, transactionRecord.getTransactionId(), transactionRecord.getStatus().toString(),
                transactionRecord.getStartDateObject(), transactionRecord.getEndDateObject(), transactionRecord.getVehicleRecord().getLpr(),
                transactionRecord.getClientRecord().getDriversLicenseNumber());
    }

    /**
     * Method to retrieve an object from the database.
     *
     * @param id The id of the object to retrieve from the database.
     * @return The object mapping to the given id.
     */
    @Override
    public Transaction getObject(int id) {
        return null;
    }

    /**
     * Get all Transaction records.
     *
     * @return True if the operation was a success, false otherwise.
     * @throws ParseException
     * @throws NumberFormatException
     */
    public List findAll() throws NumberFormatException, ParseException {
        return null;
    }
}
