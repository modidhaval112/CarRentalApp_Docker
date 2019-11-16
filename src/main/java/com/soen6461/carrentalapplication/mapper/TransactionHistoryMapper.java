package com.soen6461.carrentalapplication.mapper;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.model.TransactionHistory;
import com.soen6461.carrentalapplication.tabledatagateway.TransactionHistoryTdg;
import com.soen6461.carrentalapplication.tabledatagateway.TransactionTdg;
@Component
public class TransactionHistoryMapper implements IDataMapper<TransactionHistory>{
	
	@Autowired
	private TransactionHistoryTdg transactionHistoryTdg;
	@Autowired
	private TransactionDataMapper tdm;

	public TransactionHistoryMapper() {
	}

	@Override
	public boolean save(TransactionHistory objectToSave) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insert(TransactionHistory objectToInsert) {
		return transactionHistoryTdg.insert(objectToInsert.getTransaction().getTransactionId(), objectToInsert.getTimeStamp(), objectToInsert.getStatus());
	}

	@Override
	public boolean update(TransactionHistory objectToUpdate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TransactionHistory find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransactionHistory> findAll()  {
		List<TransactionHistory> transactionsHistory = new ArrayList<>();
		List<Map<String, Object>> records = transactionHistoryTdg.findAll();

		if(records != null) {
			for (int i = 0; i < records.size(); i++) {
				TransactionHistory transactionHistory = null;
				try {
					transactionHistory = new TransactionHistory(
							tdm.findTransaction(records.get(i).get("transactionId").toString()),
							records.get(i).get("status").toString(), records.get(i).get("timestamp").toString());
				} catch (NumberFormatException | ParseException | SQLException e) {
					e.printStackTrace();
				}
				transactionsHistory.add(transactionHistory);
			}
		}

		return transactionsHistory;
	}
}
