package com.soen6461.carrentalapplication.observer.view;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Observable;
import java.util.Observer;

import com.soen6461.carrentalapplication.model.Record;


public class TransactionObserver   implements Observer {

	

	@Override
	public void update(Observable o, Object arg) {
		
		Record r = (Record) o;
		ZonedDateTime zonedDateTime = ZonedDateTime.now( ZoneId.of( "America/Montreal" ) );
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Transaction Observer View :"+"Created at Timestamp: "+zonedDateTime+"\n Transaction ID : "+r.trans.getTransactionId() +"\nClient Name : "+r.trans.getClientRecord().getFirstName() +" " + r.trans.getClientRecord().getLastName() +", License No : "+ r.trans.getClientRecord().getDriversLicenseNumber() +"\n Status: "+  r.transactionType+ "\n Vehicle Type :  " +r.trans.getVehicleRecord().getCarType() +" with Vehicle No : " +r.trans.getVehicleRecord().getLpr()+" Due Date : " +r.trans.getEndDate());

		
	}
	
	

}
