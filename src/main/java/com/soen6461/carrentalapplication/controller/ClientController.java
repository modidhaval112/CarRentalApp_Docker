package com.soen6461.carrentalapplication.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soen6461.carrentalapplication.mapper.ClientRecordDataMapper;
import com.soen6461.carrentalapplication.model.ClientRecord;
import com.soen6461.carrentalapplication.model.VehicleRecord;
import com.soen6461.carrentalapplication.unitofwork.ClientRepository;

/**
 * This class is responsible to control the REST services related to client
 * record creation, search, modify, delete.
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

	void loadClientRecords() throws ParseException{
		this.clientRecordList = this.clientRecordDataMapper.findAll();
	}

	@PostMapping(value = "/search")
	public ClientRecord searchClient(@RequestParam("dl") String dl) {
		return this.search(dl);
	}

	/**
	 * Add a new client record. //TODO: Protect this method against unauthorised
	 * access from administrator.
	 *
	 * @param clientRecord The client record to add.
	 */
	public void addClientRecord(ClientRecord clientRecord) {

		for (ClientRecord existingClientRecord : this.clientRecordList) {
			if (clientRecord.getDriversLicenseNumber() == existingClientRecord.getDriversLicenseNumber()) {
				// throw new Exception("There is already a client with drivers license: " +
				// clientRecord.getDriversLicenseNumber() + " in the registry.");
				return;
			}
		}

		clientRecordList.add(clientRecord);
		clientRepository.registerNew(clientRecord);
	}

	@PostMapping(value = "/get-all-client-records")
	public List getAllClientRecord() {
		 List<ClientRecord> copy = new ArrayList<ClientRecord>();
	        try {
	            copy.addAll(this.clientRecordDataMapper.findAll());
	        } catch (NumberFormatException | ParseException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        return copy;
	}

	/**
	 * Delete a client record.
	 *
	 * @param driversLicenseNumber the drivers license number to use to identify the
	 *                             client record to remove.
	 */
	public boolean deleteClientRecord(String driversLicenseNumber) {

		LinkedList<String> deleteRecords = clientRepository.getDeleteRecords();
		LinkedList<String> deletedClientRecords = clientRepository.getDeletedClientRecords();

		if (!deleteRecords.contains(driversLicenseNumber) && !deletedClientRecords.contains(driversLicenseNumber)) {
			for (int i = 0; i < clientRecordList.size(); i++) {
				if (clientRecordList.get(i).getDriversLicenseNumber().equalsIgnoreCase(driversLicenseNumber)) {
					clientRepository.registerDeleted(clientRecordList.get(i));
					clientRecordList.remove(clientRecordList.get(i));

					deleteRecords.add(driversLicenseNumber);
					clientRepository.setDeleteRecords(deleteRecords);

					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Update a given client record.
	 *
	 * @param clientRecord the updated client record.
	 */
	public boolean updateClientRecord(ClientRecord clientRecord, String driversLicense) {

		int version_db = 0;
		try {
			version_db = this.clientRecordDataMapper.findclient(driversLicense).getVersion();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Version_db : " + version_db);
		System.out.println("Version : " + clientRecord.getVersion());
		
		if (version_db == clientRecord.getVersion()
				&& !clientRepository.getDirtyMap().containsKey(clientRecord.getDriversLicenseNumber())) {

			for (int i = 0; i < clientRecordList.size(); i++) {
				if (clientRecordList.get(i).getDriversLicenseNumber().equals(driversLicense)) {
					clientRecordList.set(i, clientRecord);
					clientRepository.registerDirty(clientRecord);
				}
			}

			Map<String, Boolean> dirtyMap = clientRepository.getDirtyMap();
			dirtyMap.put(clientRecord.getDriversLicenseNumber(), false);
			clientRepository.setDirtyMap(dirtyMap);
			return true;

		}

		return false;

	}

	/**
	 * Searching a client record.
	 *
	 * @param driversLicenseNumber The drivers license number to use to find a
	 *                             client.
	 * @return Returns the client found.
	 */
	private ClientRecord search(String driversLicenseNumber) {
		ClientRecord clientRecord = null;
		
        try {
        	clientRecord = this.clientRecordDataMapper.findclient(driversLicenseNumber);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return clientRecord;
	}

	public void persistData() {
		this.clientRepository.commit();
	}
}
