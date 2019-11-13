package com.soen6461.carrentalapplication.mapper;

import com.soen6461.carrentalapplication.Helpers.DataValidationHelper;
import com.soen6461.carrentalapplication.model.ClientRecord;


import com.soen6461.carrentalapplication.tabledatagateway.ClientRecordTdg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ClientRecordDataMapper implements IDataMapper<ClientRecord> {

    @Autowired
    private ClientRecordTdg clientRecordTdg;

    /**
     * Insert a client record.
     *
     * @param clientRecordToInsert Client record to insert in the database.
     * @return True if the operation was a success, false otherwise.
     */
    @Override
    public boolean insert(ClientRecord clientRecordToInsert) {
        return clientRecordTdg.insert(clientRecordToInsert.getFirstName(), clientRecordToInsert.getLastName(), clientRecordToInsert.getPhoneNumber(), clientRecordToInsert.getExpirationDateObject(), clientRecordToInsert.getDriversLicenseNumber(), clientRecordToInsert.getRecordVersion());
    }

    /**
     * Update a client record.
     *
     * @param objectToUpdate Client record to update in the database.
     * @return True if the operation was a success, false otherwise.
     */
    @Override
    public boolean update(ClientRecord objectToUpdate) {
        return this.clientRecordTdg.update(objectToUpdate.getFirstName(), objectToUpdate.getLastName(), objectToUpdate.getPhoneNumber(), objectToUpdate.getExpirationDateObject(), objectToUpdate.getDriversLicenseNumber(), objectToUpdate.getRecordVersion());
    }

    /**
     * Delete the client record.
     *
     * @param driversLicenseNumber The client drivers license number.
     * @return True if the operation was a success, false otherwise.
     */
    @Override
    public boolean delete(String driversLicenseNumber) {
        return clientRecordTdg.delete(driversLicenseNumber);
    }

    /**
     * Get all client records
     *
     * @return The complete list of clients.
     * @throws ParseException Can throw exception if an error occur when parsing an element from the database.
     * @throws NumberFormatException Can throw exception if an error occur when parsing a numeric element from the database.
     */
    @Override
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
     * Save the data in the database to persist it.
     *
     * @param objectToInsert Object to save in the database.
     */
    @Override
    public boolean save(ClientRecord objectToInsert) {
        // TODO: Not implemented.
        return false;
    }

    /**
     * Method to retrieve an object from the database.
     *
     * @param id The id of the object to retrieve from the database.
     * @return The object mapping to the given id.
     */
    @Override
    public ClientRecord find(String id) {
        // TODO: Not implemented.
        return null;
    }
}