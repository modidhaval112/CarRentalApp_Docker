package com.soen6461.carrentalapplication.unitofwork;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soen6461.carrentalapplication.mapper.TransactionDataMapper;
import com.soen6461.carrentalapplication.model.Transaction;

@Component
public class TransactionRepository implements IUnitOfWork<Transaction> {
    HashMap<String, List<Transaction>> context = new HashMap<String, List<Transaction>>();
    private Map<String, Boolean> dirtyMap = new HashMap<>();
    private LinkedList<String> deleteRecords = new LinkedList<>();
    private LinkedList<String> deletedTransactionRecords = new LinkedList<>();
    
    @Autowired
    private TransactionDataMapper transactionDataMapper;

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
	public boolean commit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerDeleted(Transaction obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerDirty(Transaction obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerNew(Transaction obj) {
		// TODO Auto-generated method stub
		
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
