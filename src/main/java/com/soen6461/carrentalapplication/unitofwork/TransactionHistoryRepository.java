package com.soen6461.carrentalapplication.unitofwork;

import com.soen6461.carrentalapplication.mapper.TransactionDataMapper;
import com.soen6461.carrentalapplication.mapper.TransactionHistoryMapper;
import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.model.TransactionHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TransactionHistoryRepository implements IUnitOfWork<TransactionHistory> {
    HashMap<String, List<TransactionHistory>> context = new HashMap<String, List<TransactionHistory>>();
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionHistoryMapper.class);

    private Map<String, Boolean> dirtyMap = new HashMap<>();
    private LinkedList<String> deleteRecords = new LinkedList<>();
    private LinkedList<String> deletedTransactionRecords = new LinkedList<>();

    @Autowired
    private TransactionHistoryMapper transactionHistoryDataMapper;

    /**
     * Default constructor.
     */
    public TransactionHistoryRepository() {
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
     * Gets deleted client records.
     *
     * @return A list of deleted client records.
     */
    public LinkedList<String> getDeletedTransactionRecords() {
        return deletedTransactionRecords;
    }

    /**
     * Sets the deleted client records.
     *
     * @param deletedTransactionRecords A list of deleted client records.
     */
    public void setDeletedTransactionRecords(LinkedList<String> deletedTransactionRecords) {
        this.deletedTransactionRecords = deletedTransactionRecords;
    }

    /**
     * Register the new object.
     *
     * @param transactionHistory The object to register new.
     */
    @Override
    public void registerNew(TransactionHistory transactionHistory) {
        register(transactionHistory, IUnitOfWork.INSERT);
    }

    /**
     * Register the object with dirty data.
     *
     * @param transactionHistory The object to register dirty.
     */
    @Override
    public void registerDirty(TransactionHistory transactionHistory) {
    }

    /**
     * Register the object to be deleted.
     *
     * @param transactionHistory the object to register deleted
     */
    @Override
    public void registerDeleted(TransactionHistory transactionHistory) {
    }

    /**
     * Register work.
     *
     * @param transactionHistory The transaction history.
     * @param operation          The operation.
     */
    private void register(TransactionHistory transactionHistory, String operation) {
        List transactionsToOperate = context.get(operation); // Retrieve list of vehicles that are newly registered to get
        if (transactionsToOperate == null) {
            transactionsToOperate = new ArrayList<>();
        }
        transactionsToOperate.add(transactionHistory);
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
        context = new HashMap<String, List<TransactionHistory>>();
        LOGGER.info("Commit finished.");
        return true;
    }

    /**
     * Commit insert work.
     */
    private void commitInsert() {
        List<TransactionHistory> transactionsToBeInserted = context.get(IUnitOfWork.INSERT);
        for (TransactionHistory transactionHistoryRecord : transactionsToBeInserted) {
            transactionHistoryDataMapper.insert(transactionHistoryRecord);
        }
    }

    /**
     * Commit modify work.
     */
    private void commitModify() {
    }

    /**
     * Commit delete work.
     */
    private void commitDelete() {
    }

    /**
     * Register clean.
     *
     * @param obj The object to register clean.
     */
    @Override
    public void registerClean(TransactionHistory obj) {
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
