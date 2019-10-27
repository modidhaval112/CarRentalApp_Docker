package com.soen6461.carrentalapplication.observer.view;

import java.util.Observable;
import java.util.Observer;

import com.soen6461.carrentalapplication.model.Record;
import com.soen6461.carrentalapplication.model.TransactionHistory;


/**
 * Class designed for implementing observer pattern
 * @author Admin
 *
 */
public class TransactionObserver implements Observer {

	/**
	 * This method will be notified whenever there is any changes in states
	 * of observable classes
	 * @param o Observable instance
	 * @param arg Object instance
	 */
	@Override
	public void update(Observable o, Object arg) {

		Record r = (Record) o;

		TransactionHistory transactionHistory = new TransactionHistory(r.transaction, r.transactionType);
		r.transactionHistory.add(transactionHistory);

		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Transaction Observer View :" +
				"Created at Timestamp: " + transactionHistory.getTimeStamp() + "\n" +
				"Status: " + r.transactionType + "\n" +
				r.transaction.toString() + "\n" +
				r.transaction.getClientRecord().toString() + "\n" +
				r.transaction.getVehicleRecord().toString() + "\n");

		for (int i = 0; i < r.transactionHistory.size(); i++) {
			System.out.println("No " + i + " " + r.transactionHistory.get(i).getTransaction().getTransactionId() + " " + r.transactionHistory.get(i).getStatus() + " " + r.transactionHistory.get(i).getTransaction().getClientRecord().getFirstName());
		}
	}
}
