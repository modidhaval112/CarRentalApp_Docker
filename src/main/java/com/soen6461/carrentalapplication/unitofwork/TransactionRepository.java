package com.soen6461.carrentalapplication.unitofwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soen6461.carrentalapplication.mapper.TransactionDataMapper;
import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.model.VehicleRecord;

@Component
public class TransactionRepository implements IUnitOfWork<Transaction> {
    HashMap<String, List<Transaction>> context = new HashMap<String, List<Transaction>>();
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionDataMapper.class);

    private Map<String, Boolean> dirtyMap = new HashMap<>();
    private LinkedList<String> deleteRecords = new LinkedList<>();
    private LinkedList<String> deletedTransactionRecords = new LinkedList<>();

    @Autowired
    private TransactionDataMapper transactionDataMapper;

    /**
     * Default constructor.
     */
    public TransactionRepository() {
    }

    /**
     * Gets the dirty map.
     *
     * @return The dirty map.
     */
    public Map<String, Boolean> getDirtyMap() {
        return dirtyMap;
    }

    /**
     * Sets the dirty map.
     *
     * @param dirtyMap The dirty map.
     */
    public void setDirtyMap(Map<String, Boolean> dirtyMap) {
        this.dirtyMap = dirtyMap;
    }

    /**
     * Gets the delete record.
     *
     * @return List of deleted records.
     */
    public LinkedList<String> getDeleteRecords() {
        return deleteRecords;
    }

    /**
     * Sets the deleted records.
     *
     * @param deleteRecords List of deleted records.
     */
    public void setDeleteRecords(LinkedList<String> deleteRecords) {
        this.deleteRecords = deleteRecords;
    }

    /**
     * Gets deleted transaction records.
     *
     * @return A list of deleted client records.
     */
    public LinkedList<String> getDeletedTransactionRecords() {
        return deletedTransactionRecords;
    }

    public void setDeletedTransactionRecords(LinkedList<String> deletedTransactionRecords) {
        this.deletedTransactionRecords = deletedTransactionRecords;
    }

    /**
     * Register the new object.
     *
     * @param transaction The object to register new.
     */
    @Override
    public void registerNew(Transaction transaction) {
        LOGGER.info("Registering {} for insert in context.", transaction.getTransactionId());
        register(transaction, IUnitOfWork.INSERT);
    }

    /**
     * Register the object with dirty data.
     *
     * @param transaction The object to register dirty.
     */
    @Override
    public void registerDirty(Transaction transaction) {
        LOGGER.info("Registering {} for modify in context.", transaction.getTransactionId());
        register(transaction, IUnitOfWork.MODIFY);
    }

    /**
     * Register the object to be deleted.
     *
     * @param transaction the object to register deleted
     */
    @Override
    public void registerDeleted(Transaction transaction) {
        LOGGER.info("Registering {} for delete in context.", transaction.getTransactionId());
        register(transaction, IUnitOfWork.DELETE);
    }

    /**
     * Register work.
     *
     * @param transaction The transaction.
     * @param operation   The operation.
     */
    private void register(Transaction transaction, String operation) {
        List transactionsToOperate = context.get(operation); // Retrieve list of vehicles that are newly registered to get
        if (transactionsToOperate == null) {
            transactionsToOperate = new ArrayList<>();
        }
        transactionsToOperate.add(transaction);
        context.put(operation, transactionsToOperate); // Old list is replaced with new list
    }

    /**
     * Commit the work.
     *
     * @return True if the operation was a success, false otherwise.
     */
    @Override
    public boolean commit() {
        if (context == null || context.size() == 0) {
            return false;
        }
        LOGGER.info("Commit started");
        if (context.containsKey(IUnitOfWork.INSERT)) {
            commitInsert();
        }

        if (context.containsKey(IUnitOfWork.MODIFY)) {
            commitModify();
        }
        if (context.containsKey(IUnitOfWork.DELETE)) {
            commitDelete();
        }
        context = new HashMap<String, List<Transaction>>();
        LOGGER.info("Commit finished.");
        return true;
    }

    /**
     * Commit insert work.
     */
    private void commitInsert() {
        List<Transaction> transactionsToBeInserted = context.get(IUnitOfWork.INSERT);
        for (Transaction transactionRecord : transactionsToBeInserted) {
            LOGGER.info("Saving {} to database.", transactionRecord.getTransactionId());
            transactionDataMapper.insert(transactionRecord);
        }
    }

    /**
     * Commit modify work.
     */
    private void commitModify() {
        List<Transaction> modifiedTransactions = context.get(IUnitOfWork.MODIFY);
        for (Transaction transactionRecord : modifiedTransactions) {
            LOGGER.info("Modifying {} to database.", transactionRecord.getTransactionId());
            transactionDataMapper.update(transactionRecord);
        }

        this.dirtyMap = new HashMap<>();
    }

    /**
     * Commit delete work.
     */
    private void commitDelete() {
        List<Transaction> deletedTransactions = context.get(IUnitOfWork.DELETE);
        for (Transaction transactionRecord : deletedTransactions) {
            LOGGER.info("Deleting {} to database.", transactionRecord.getTransactionId());
            transactionDataMapper.delete(transactionRecord.getTransactionId());
            deletedTransactionRecords.add(transactionRecord.getTransactionId());
        }
        this.deleteRecords = new LinkedList<>();
    }

    @Override
    public void registerClean(Transaction obj) {
    }

    /**
     * Rollback.
     *
     * @return True if the operation was a success, false otherwise.
     */
    @Override
    public boolean rollback() {
        return false;
    }
}