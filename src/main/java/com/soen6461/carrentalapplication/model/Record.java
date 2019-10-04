package com.soen6461.carrentalapplication.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import com.soen6461.carrentalapplication.observer.view.TransactionObserver;


public class Record extends Observable {
	public String transactionType ;
	public Transaction trans;

	public Record()
	{
		TransactionObserver to = new TransactionObserver();
		this.addObserver(to);
	}

	protected List<Transaction> transactionList = new ArrayList<Transaction>();

	/**
	 * Adds a given transaction.
	 * 
	 * @param transaction The transaction to add.
	 */
	public void addTransaction(Transaction transaction) {
		this.transactionList.add(transaction);
		setObserver("Added",transaction);
	}

	/**
	 * Removes a given transaction.
	 * 
	 * @param transactionId The transaction to remove.
	 */
	public void removeTransaction(String transactionId) {

		Iterator<Transaction> iterator = this.transactionList.iterator();

		while (iterator.hasNext()) {
			Transaction t=iterator.next();

			if(t.getTransactionId().equals(transactionId)) {
				setObserver("Cancelled",t);

				iterator.remove();
			}


		}
	}

	/**
	 * Return transaction.
	 * 
	 * @param transactionId The transaction to remove.
	 */
	public void returnTransaction(String transactionId) {

		Iterator<Transaction> iterator = this.transactionList.iterator();

		while (iterator.hasNext()) {
			Transaction t=iterator.next();
			if(t.getTransactionId().equals(transactionId)) {
				setObserver("Returned",t);

				iterator.remove();
			}

		}

	}

	public List<Transaction> getTransactionList() {
		// To protect the main vehicle record list, only a copy is provided.
		// this avoids someone other than this class from adding or removing vehicles.

		List<Transaction> copy = new ArrayList<Transaction>();
		for (Transaction transaction : this.transactionList) {
			copy.add(transaction);
		}

		return copy;
	}

	void setObserver(String msg,Transaction t)
	{
		transactionType=msg;
		trans=t;
		
		setChanged();
		notifyObservers();
	}
}

