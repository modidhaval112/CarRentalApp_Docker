package com.soen6461.carrentalapplication.unitofwork;

import com.soen6461.carrentalapplication.mapper.TransactionDataMapper;
import com.soen6461.carrentalapplication.mapper.TransactionHistoryDataMapper;
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

    public TransactionHistoryRepository() {
	}

    public Map<String, Boolean> getDirtyMap() {
        return dirtyMap;
    }

    public void setDirtyMap(Map<String, Boolean> dirtyMap) {
        this.dirtyMap = dirtyMap;
    }
    
    public LinkedList<String> getDeleteRecords() {
		return deleteRecords;
	}
    
    public void setDeleteRecords(LinkedList<String> deleteRecords) {
		this.deleteRecords = deleteRecords;
	}
	
	public LinkedList<String> getDeletedTransactionRecords () {
		return deletedTransactionRecords;
	}

	public void setDeletedTransactionRecords (LinkedList<String> deletedTransactionRecords) {
		this.deletedTransactionRecords = deletedTransactionRecords;
	}
	
	

	 @Override
	    public void registerNew(TransactionHistory transactionHistory) {
//	        LOGGER.info("Registering {} for insert in context.", transaction.getTransactionId());
	        register(transactionHistory, IUnitOfWork.INSERT);
	    }

	    @Override
	    public void registerDirty(TransactionHistory transactionHistory) {

	    }


    @Override
	    public void registerDeleted(TransactionHistory transactionHistory) {

	    }

	    private void register(TransactionHistory transactionHistory, String operation) {
	        List transactionsToOperate = context.get(operation); // Retrieve list of vehicles that are newly registered to get
	        if (transactionsToOperate == null) {
	        	transactionsToOperate = new ArrayList<>();
	        }
	        transactionsToOperate.add(transactionHistory);
	        context.put(operation, transactionsToOperate); // Old list is replaced with new list
	    }

    /**
     * All UnitOfWork operations are batched and executed together on commit only.
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

    private void commitInsert() {
        List<TransactionHistory> transactionsToBeInserted = context.get(IUnitOfWork.INSERT);
        for (TransactionHistory transactionHistoryRecord : transactionsToBeInserted) {
//            LOGGER.info("Saving {} to database.", transactionHistoryRecord.getTransactionId());
            transactionHistoryDataMapper.insert(transactionHistoryRecord);
        }
    }

    private void commitModify() {

    }

    private void commitDelete() {

    }

	@Override
	public void registerClean(TransactionHistory obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean rollback() {
		// TODO Auto-generated method stub
		return false;
	}

	


}
