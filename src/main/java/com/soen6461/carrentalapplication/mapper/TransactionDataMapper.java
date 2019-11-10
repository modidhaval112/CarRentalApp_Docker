package com.soen6461.carrentalapplication.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soen6461.carrentalapplication.model.ClientRecord;
import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.tabledatagateway.TransactionTdg;

@Component
public class TransactionDataMapper {
    @Autowired
    private TransactionTdg transactionTdg;

    /**
     * Transaction record insert mapper
     *
     * @param transrecord transaction record type
     * @return returns true or false
     */
    public boolean insert(Transaction transrecord) {
        return transactionTdg.insert(1, transrecord.getTransactionId(), transrecord.getStatus().toString(), transrecord.getStartDateObject(), transrecord.getEndDateObject(), transrecord.getVehicleRecord().getLpr(), transrecord.getClientRecord().getDriversLicenseNumber());


    }

    /**
     * Update a Transaction record
     *
     * @param objectToUpdate
     * @return
     */
    public boolean update(Transaction transrecord) {
        return transactionTdg.update(1, transrecord.getTransactionId(), transrecord.getStatus().toString(), transrecord.getStartDateObject(), transrecord.getEndDateObject(), transrecord.getVehicleRecord().getLpr(), transrecord.getClientRecord().getDriversLicenseNumber());
    }

    /**
     * Delete the Transaction record
     *
     * @param driversLicenseNumber
     * @return
     */
    public boolean delete() {
        return false;
    }

    /**
     * Get all Transaction records
     *
     * @return
     * @throws ParseException
     * @throws NumberFormatException
     */
    public List findAll() throws NumberFormatException, ParseException {
        return null;
    }
}
