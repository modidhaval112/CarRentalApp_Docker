package com.soen6461.carrentalapplication.observer.view;

import java.util.Observable;
import java.util.Observer;

import com.soen6461.carrentalapplication.model.Record;


public class TransactionObserver   implements Observer {

	

	@Override
	public void update(Observable o, Object arg) {
		
		Record r = (Record) o;
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Transaction Observer View :"+"--Status : "+  r.transactionType+ ", Transaction ID : "+r.trans.getTransactionId() +" ,Name : "+r.trans.getClientRecord().getFirstName() +" " + r.trans.getClientRecord().getLastName() +" ,License No : "+ r.trans.getClientRecord().getDriversLicenseNumber() +" ,Vehicle Type :  " +r.trans.getVehicleRecord().getCarType() +" ,Vehicle No : " +r.trans.getVehicleRecord().getLpr());

		
	}
	
	

}
