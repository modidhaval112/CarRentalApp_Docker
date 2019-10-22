package com.soen6461.carrentalapplication.observer.view;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Observable;
import java.util.Observer;

import com.soen6461.carrentalapplication.model.Record;
import com.soen6461.carrentalapplication.model.TransactionHistory;

public class TransactionObserver implements Observer {

	@Override
	public void update(Observable o, Object arg) {

		Record r = (Record) o;
		ZonedDateTime timeStamp = ZonedDateTime.now(ZoneId.of("America/Montreal"));

		TransactionHistory transactionHistory = new TransactionHistory(r.transaction, timeStamp, r.transactionType);
		r.transactionHistory.add(transactionHistory);

		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Transaction Observer View :" +
				"Created at Timestamp: " + timeStamp + "\n" +
				"Status: " + r.transactionType + "\n" +
				r.transaction.toString() + "\n" +
				r.transaction.getClientRecord().toString() + "\n" +
				r.transaction.getVehicleRecord().toString() + "\n");

		for (int i = 0; i < r.transactionHistory.size(); i++) {
			System.out.println("No " + i + " " + r.transactionHistory.get(i).getTransaction().getTransactionId() + " " + r.transactionHistory.get(i).getStatus() + " " + r.transactionHistory.get(i).getTransaction().getClientRecord().getFirstName());
		}
	}
}
