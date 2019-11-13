package com.soen6461.carrentalapplication.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.soen6461.carrentalapplication.mapper.ClientRecordDataMapper;
import com.soen6461.carrentalapplication.unitofwork.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.soen6461.carrentalapplication.model.ClientRecord;

/**
 * This class is responsible to control the REST services related to client record
 * creation, search, modify, delete.
 */
@Component
public class ClientController {

    @Autowired
    private ClientRecordDataMapper clientRecordDataMapper;

    @Autowired
    private ClientRepository clientRepository;

    private static List<ClientRecord> clientRecordList = new ArrayList<>();

    /**
     * ClientRController class constructor.
     */
    private ClientController() {
    }

    void loadClientRecords() throws ParseException, SQLException {
        this.clientRecordList = this.clientRecordDataMapper.findAll();
    }

    @PostMapping(value = "/search")
    public ClientRecord searchClient(@RequestParam("dl") String dl) {
        return this.search(dl);
    }

    /**
     * Add a new client record.
     * //TODO: Protect this method against unauthorised access from administrator.
     *
     * @param clientRecord The client record to add.
     */
    public void addClientRecord(ClientRecord clientRecord) {

        for (ClientRecord existingClientRecord : this.clientRecordList) {
            if (clientRecord.getDriversLicenseNumber() == existingClientRecord.getDriversLicenseNumber()) {
                // throw new Exception("There is already a client with drivers license: " + clientRecord.getDriversLicenseNumber() + " in the registry.");
                return;
            }
        }

        clientRecordList.add(clientRecord);
        clientRepository.registerNew(clientRecord);
    }

    @PostMapping(value = "/get-all-client-records")
    public List getAllClientRecord() {
        return this.clientRecordList;
    }

    /**
     * Delete a client record.
     *
     * @param driversLicenseNumber the drivers license number to use to identify the client record to remove.
     */
    public void deleteClientRecord(String driversLicenseNumber) {
        for (int i = 0; i < clientRecordList.size(); i++) {
            if (clientRecordList.get(i).getDriversLicenseNumber().equals(driversLicenseNumber)) {
                clientRepository.registerDeleted(clientRecordList.get(i));
                clientRecordList.remove(clientRecordList.get(i));
//                clientRepository.commit();
            }
        }
    }

    /**
     * Update a given client record.
     *
     * @param clientRecord the updated client record.
     */
    public void updateClientRecord(ClientRecord clientRecord, String driversLicense) {
        for (int i = 0; i < clientRecordList.size(); i++) {
            if (clientRecordList.get(i).getDriversLicenseNumber().equals(driversLicense)) {
                clientRecordList.set(i, clientRecord);
                clientRepository.registerDirty(clientRecord);
            }
        }
    }

    /**
     * Searching a client record.
     *
     * @param driversLicenseNumber The drivers license number to use to find a client.
     * @return Returns the client found.
     */
    private ClientRecord search(String driversLicenseNumber) {
        for (int i = 0; i < clientRecordList.size(); i++) {
            if (clientRecordList.get(i).getDriversLicenseNumber().equals(driversLicenseNumber)) {
                return clientRecordList.get(i);
            }
        }

        return null;
    }

    public void persistData() {
        this.clientRepository.commit();
    }
}