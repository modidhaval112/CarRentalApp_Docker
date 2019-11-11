package com.soen6461.carrentalapplication.model;

public class TransactionHistory {

    private Transaction transaction;
    private String status;

    // The timestamp cannot be altered by any class.
    // A timestamp does not need a very comprehensible number. It simply needs to mark a moment in time very precisely.
    private long timeStamp = System.currentTimeMillis();

    /**
     * Constructor for an instance of a recorded transaction in history.
     *
     * @param transaction The transaction.
     * @param status      The transaction status.
     */
    public TransactionHistory(Transaction transaction, String status) {
        this.transaction = transaction;
        this.status = status;
    }

    /**
     * Gets the transaction.
     *
     * @return The transaction.
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Gets the transaction timestamp.
     *
     * @return The transaction timestamp
     */
    public String getTimeStamp() {
        return String.valueOf(timeStamp);
    }

    /**
     * Gets the transaction status.
     *
     * @return the transaction status.
     */
    public String getStatus() {
        return status;
    }
}