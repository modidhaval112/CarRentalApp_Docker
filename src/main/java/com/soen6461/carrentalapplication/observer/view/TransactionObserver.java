package com.soen6461.carrentalapplication.observer.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

import com.soen6461.carrentalapplication.model.Record;
import com.soen6461.carrentalapplication.model.TransactionHistory;
import com.soen6461.carrentalapplication.unitofwork.TransactionHistoryRepository;
import com.soen6461.carrentalapplication.unitofwork.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * Class designed for implementing observer pattern
 *
 * @author Admin
 */

public class TransactionObserver implements Observer {

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();

	/**
	 * This method will be notified whenever there is any changes in states of
	 * observable classes
	 *
	 * @param o   Observable instance
	 * @param arg Object instance
	 */
	@Override
	public void update(Observable o, Object arg) {

		Record r = (Record) o;

		TransactionHistory transactionHistory = new TransactionHistory(r.transaction.getTransactionId(),
				r.transaction.getVehicleRecord().getCarType(), r.transaction.getVehicleRecord().getLpr(),
				r.transaction.getVehicleRecord().getLpr(), r.transaction.getClientRecord().getFirstName(),
				r.transaction.getStartDate(), r.transaction.getEndDate(), r.transaction.getStatus().toString(),
				dtf.format(now).toString(), r.transaction.getVehicleRecord().getColor(),
				r.transaction.getVehicleRecord().getMake(), r.transaction.getVehicleRecord().getYear());
		r.transactionHistory.add(transactionHistory);

		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Transaction Observer View :" + "Created at Timestamp: " + transactionHistory.getTimeStamp()
				+ "\n" + "Status: " + r.transactionType + "\n" + r.transaction.toString() + "\n"
				+ r.transaction.getClientRecord().toString() + "\n" + r.transaction.getVehicleRecord().toString()
				+ "\n");

		for (int i = 0; i < r.transactionHistory.size(); i++) {
			System.out.println("No " + i + " " + r.transactionHistory.get(i).getTransactionId() + " "
					+ r.transactionHistory.get(i).getStatus() + " " + r.transactionHistory.get(i).getClientName());
		}
	}
}
