package com.soen6461.carrentalapplication.controller;

import java.util.ArrayList;
import java.util.List;

import com.soen6461.carrentalapplication.model.Record;
import com.soen6461.carrentalapplication.model.TransactionHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.model.VehicleRecord;



@RestController
public class TransactionCatalog {

	Record record= new Record();
	private List<TransactionHistory> transactionHistoryList = new ArrayList<TransactionHistory>();

	public List getFilteredTransactionHistory( String filter,
			String value) {
		return this.getTransactionSet(filter, value);
	}