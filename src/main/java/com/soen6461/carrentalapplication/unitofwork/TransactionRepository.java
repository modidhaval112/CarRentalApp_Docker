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
    

    private TransactionDataMapper transactionDataMapper = new TransactionDataMapper();

    public  TransactionRepository() {
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
	
	public LinkedList<String> getDeletedTransactionRecords() {
		return deletedTransactionRecords;
	}

	public void setDeletedTransactionRecords(LinkedList<String> deletedTransactionRecords) {
		this.deletedTransactionRecords = deletedTransactionRecords;
	}
	
	

	 @Override
	    public void registerNew(Transaction transaction) {
	        LOGGER.info("Registering {} for insert in context.", transaction.getTransactionId());
	        register(transaction, IUnitOfWork.INSERT);
	    }

	    @Override
	    public void registerDirty(Transaction transaction) {
	        LOGGER.info("Registering {} for modify in context.", transaction.getTransactionId());
	        register(transaction, IUnitOfWork.MODIFY);
	    }

	    @Override
	    public void registerDeleted(Transaction transaction) {
	        LOGGER.info("Registering {} for delete in context.", transaction.getTransactionId());
	        register(transaction, IUnitOfWork.DELETE);
	    }

	    private void register(Transaction transaction, String operation) {
	        List transactionsToOperate = context.get(operation); // Retrieve list of vehicles that are newly registered to get
	        if (transactionsToOperate == null) {
	        	transactionsToOperate = new ArrayList<>();
	        }
	        transactionsToOperate.add(transaction);
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
        context = new HashMap<String, List<Transaction>>();
        LOGGER.info("Commit finished.");
        return true;
    }

    private void commitInsert() {
        List<Transaction> transactionsToBeInserted = context.get(IUnitOfWork.INSERT);
        for (Transaction transactionRecord : transactionsToBeInserted) {
            LOGGER.info("Saving {} to database.", transactionRecord.getTransactionId());
            transactionDataMapper.insert(transactionRecord);
        }
    }

    private void commitModify() {
        List<Transaction> modifiedTransactions = context.get(IUnitOfWork.MODIFY);
        for (Transaction transactionRecord : modifiedTransactions) {
            LOGGER.info("Modifying {} to database.", transactionRecord.getTransactionId());
            transactionDataMapper.update(transactionRecord);
        }

        this.dirtyMap = new HashMap<>();
    }

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean rollback() {
		// TODO Auto-generated method stub
		return false;
	}

	


}
