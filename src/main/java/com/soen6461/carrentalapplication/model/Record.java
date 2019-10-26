package com.soen6461.carrentalapplication.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import com.soen6461.carrentalapplication.observer.view.TransactionObserver;

public class Record extends Observable {
    public String transactionType;
    public Transaction transaction;
    public HashMap<ClientRecord, Transaction> clientTransactions = new HashMap<ClientRecord, Transaction>();
    public static List<TransactionHistory> transactionHistory = new ArrayList<>();

    public Record() {
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
        if (transaction.getStatus() == Transaction.Status.Rented) {
            setObserver("Rented", transaction);
        } else if (transaction.getStatus() == Transaction.Status.Reserved) {
            setObserver("Reserved", transaction);
        }
    }

    /**
     * Removes a given transaction.
     *
     * @param transactionId The transaction to remove.
     */
    public void removeTransaction(String transactionId) {

        Iterator<Transaction> iterator = this.transactionList.iterator();

        while (iterator.hasNext()) {
            Transaction t = iterator.next();

            if (t.getTransactionId().equals(transactionId)) {
                setObserver("Cancelled", t);
                t.setStatus(Transaction.Status.Cancelled);
                //iterator.remove();
            }
        }
    }

    /**
     * Handle vehicle return.
     *
     * @param transactionId The transaction to remove.
     */
   public void returnTransaction(String transactionId) {

        Iterator<Transaction> iterator = this.transactionList.iterator();

        while (iterator.hasNext()) {
            Transaction t = iterator.next();
            if (t.getTransactionId().equals(transactionId)) {
                setObserver("Returned", t);
                t.setStatus(Transaction.Status.Returned);
                //iterator.remove();
            }
        }
    }

	/**
	 * Gets the transaction list.
	 *
	 * @return The transaction list.
	 */
	public List<Transaction> getTransactionList() {
        // To protect the main vehicle record list, only a copy is provided.
        // this avoids someone other than this class from adding or removing vehicles.

        List<Transaction> copy = new ArrayList<Transaction>();
        for (Transaction transaction : this.transactionList) {
            copy.add(transaction);
        }

        return copy;
    }

	/**
	 * Gets the list of all transactions.
	 *
	 * @return
	 */
	public List<TransactionHistory> getAllTransactionHistory() {
        // To protect the main history list, only a copy is provided.
        // this avoids someone other than this class from adding or removing vehicles.

        List<TransactionHistory> copy = new ArrayList<TransactionHistory>();
        for (TransactionHistory transactionHistory : this.transactionHistory) {
            copy.add(transactionHistory);
        }

        return copy;
    }

	/**
	 * Sets the observer.
	 *
	 * @param message
	 * @param transaction
	 */
	void setObserver(String message, Transaction transaction) {
        transactionType = message;
        this.transaction = transaction;

        setChanged();
        notifyObservers();
    }
}

