package com.soen6461.carrentalapplication.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soen6461.carrentalapplication.model.ClientRecord;

/**
 * This class is responsible to control the REST services related to client record
 * creation, search, modify, delete.
 */
@RestController
public class ClientController {

    private List<ClientRecord> clientRecordList = new ArrayList<ClientRecord>();

    /**
     *  ClientRecord class constructor.
     */
    private ClientController() {
    }

    @PostMapping(value = "/search")
    public ClientRecord searchClient(@RequestParam("dl") String dl) {
        return this.search(dl);
    }

    /**
     * Add a new client record.
     * //TODO: Protect this method against unauthorised access from administrator.
     * @param clientRecord The client record to add.
     * @throws Exception Throws an exception if the record already exists.
     */
    public void addClientRecord(ClientRecord clientRecord) {

        for (ClientRecord existingClientRecord: this.clientRecordList) {
            if(clientRecord.getDriversLicenseNumber() == existingClientRecord.getDriversLicenseNumber()) {
                throw new Exception("There is already a client with drivers license: " + clientRecord.getDriversLicenseNumber() + " in the registry.");
            }
        }

        clientRecordList.add(clientRecord);
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
                clientRecordList.remove(clientRecordList.get(i));
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
                clientRecordList.set(i,clientRecord);
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
}