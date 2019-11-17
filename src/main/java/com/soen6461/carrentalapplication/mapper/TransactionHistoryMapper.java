package com.soen6461.carrentalapplication.mapper;

import java.text.DateFormat;
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

	@Override
	public boolean save(TransactionHistory objectToSave) {
		// TODO Auto-generated method stub
		return false;
	}

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
