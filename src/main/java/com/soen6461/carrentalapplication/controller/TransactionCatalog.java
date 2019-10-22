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
	
	public List<TransactionHistory> getTransactionSet(String filter, String value) {

		List<TransactionHistory> temp = new ArrayList<>();
		System.out.println(filter+" "+value);
		if (filter.equals("vehicle-model")) {
			for (int i = 0; i < record.getAllTransactionHistory().size(); i++) {
				if (record.getAllTransactionHistory().get(i).getTransaction().getVehicleRecord().getMake().equalsIgnoreCase(value)) {
					temp.add(record.getAllTransactionHistory().get(i));
				}
			}

		}
		else if (filter.equals("car-type")) {
			for (int i = 0; i < record.getAllTransactionHistory().size(); i++) {
				if (record.getAllTransactionHistory().get(i).getTransaction().getVehicleRecord().getCarType().equals(value)) {
					temp.add(record.getAllTransactionHistory().get(i));
				}
			}
		} else if (filter.equals("car-color")) {
			for (int i = 0; i < record.getAllTransactionHistory().size(); i++) {
				if (record.getAllTransactionHistory().get(i).getTransaction().getVehicleRecord().getColor().equalsIgnoreCase(value)) {
					temp.add(record.getAllTransactionHistory().get(i));
				}
			}

		}else if (filter.equals("client")) {

			for (int i = 0; i < record.getAllTransactionHistory().size(); i++) {

					for (TransactionHistory t:record.getAllTransactionHistory())
					{
						String firstLast= t.getTransaction().getClientRecord().getFirstName().toLowerCase()+" "+ t.getTransaction().getClientRecord().getLastName().toLowerCase();
						if(t.getTransaction().getClientRecord().getFirstName().toLowerCase().contains(value) ||  t.getTransaction().getClientRecord().getLastName().toLowerCase().contains(value) || firstLast.contains(value))
						{
							temp.add(t);
						}
					}
				}

			}
		return temp;
	}

	public List<TransactionHistory> getAllTransactionHistory() {
		List<TransactionHistory> temp = new ArrayList<>();
		for(TransactionHistory t: record.getAllTransactionHistory())
		{
				temp.add(t);
		}
		return temp;
	}
}
