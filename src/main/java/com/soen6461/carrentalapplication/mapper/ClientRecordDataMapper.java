package com.soen6461.carrentalapplication.mapper;

import com.soen6461.carrentalapplication.Helpers.DataValidationHelper;
import com.soen6461.carrentalapplication.model.ClientRecord;


import com.soen6461.carrentalapplication.tabledatagateway.ClientRecordTdg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ClientRecordDataMapper {

    @Autowired
    private ClientRecordTdg clientRecordTdg;

    /**
     * Insert a client record
     *
     * @param clientRecordToInsert
     * @return
     */
    public boolean insert(ClientRecord clientRecordToInsert) {
        return clientRecordTdg.insert(clientRecordToInsert.getFirstName(), clientRecordToInsert.getLastName(), clientRecordToInsert.getPhoneNumber(), clientRecordToInsert.getExpirationDateObject(), clientRecordToInsert.getDriversLicenseNumber(), clientRecordToInsert.getRecordVersion());
    }

    /**
     * Update a client record
     *
     * @param objectToUpdate
     * @return
     */
    public boolean update(ClientRecord objectToUpdate) {
        return this.clientRecordTdg.update(objectToUpdate.getFirstName(), objectToUpdate.getLastName(), objectToUpdate.getPhoneNumber(), objectToUpdate.getExpirationDateObject(), objectToUpdate.getDriversLicenseNumber(), objectToUpdate.getRecordVersion());
    }

    /**
     * Delete the client record
     *
     * @param driversLicenseNumber
     * @return
     */
    public boolean delete(String driversLicenseNumber) {
        return clientRecordTdg.delete(driversLicenseNumber);
    }

    /**
     * Get all client records
     *
     * @return
     * @throws ParseException
     * @throws NumberFormatException
     */
    public List findAll() throws NumberFormatException, ParseException {
        List<ClientRecord> clientRecords = new ArrayList<>();
        List<Map<String, Object>> records = clientRecordTdg.findAll();

        if(records != null) {
            for (int i = 0; i < records.size(); i++) {
                ClientRecord clientRecord = new ClientRecord(
                        records.get(i).get("driversLicenseNumber").toString(),
                        Integer.parseInt(records.get(i).get("version").toString()),
                        records.get(i).get("firstName").toString(),
                        records.get(i).get("lastName").toString(),
                        records.get(i).get("phoneNumber").toString(),
                        DataValidationHelper.dateFormat.parse(records.get(i).get("expirationDate").toString()));
                clientRecords.add(clientRecord);
            }
        }

        return clientRecords;
    }

    /**
     * Save the client Record
     * @param objectToInsert
     * @return
     */
//    public boolean save(ClientRecord objectToInsert) {
//
//        if (this.clientRecordTdg.getObject(objectToInsert.getDriversLicenseNumber()) == null) {
//            return this.clientRecordTdg.insert(objectToInsert);
//        }
//        return this.clientRecordTdg.update(objectToInsert);
//    }
}