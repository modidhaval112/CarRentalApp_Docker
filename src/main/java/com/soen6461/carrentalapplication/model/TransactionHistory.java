package com.soen6461.carrentalapplication.model;

import java.time.ZonedDateTime;

public class TransactionHistory {

    private Transaction transaction;
    private ZonedDateTime timeStamp;
    String status;

    public TransactionHistory(Transaction transaction, ZonedDateTime timeStamp, String status) {
        this.transaction = transaction;
        this.timeStamp = timeStamp;
        this.status = status;
    }
}