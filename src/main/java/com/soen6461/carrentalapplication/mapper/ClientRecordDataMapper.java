package com.soen6461.carrentalapplication.mapper;

import com.soen6461.carrentalapplication.model.ClientRecord;
import com.soen6461.carrentalapplication.tabledatagateway.ClientRecordTdg;
import com.soen6461.carrentalapplication.tabledatagateway.ClientTDG;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ClientRecordDataMapper {

    @Autowired
    private ClientRecordTdg clientRecordTdg;
    private ClientTDG clientTdg;

    /**
     * Insert a client record
     * @param clientRecordToInsert
     * @return
     */
    public boolean insert(ClientRecord clientRecordToInsert) {
    	return clientTdg.insert(clientRecordToInsert.getFirstName(),clientRecordToInsert.getLastName(),clientRecordToInsert.getPhoneNumber(),clientRecordToInsert.getExpirationDateObject(),clientRecordToInsert.getDriversLicenseNumber(),clientRecordToInsert.getRecordVersion());
    }

    /**
     * Update a client record
     * @param objectToUpdate
     * @return
     */
//    public boolean update(ClientRecord objectToUpdate) {
//        return this.clientRecordTdg.update(objectToUpdate);
//    }

    /**
     * Delete the client record
     * @param driversLicenseNumber
     * @return
     */
//    public boolean delete(String driversLicenseNumber) {
//        return clientRecordTdg.delete(driversLicenseNumber);
//    }

    /**
     * Get a client record
     * @param driversLicenseNumber
     * @return
     */
    public ClientRecord getObject(String driversLicenseNumber) {
        ClientRecord clientRecord= null;
        List<Map<String, Object>> rs= clientTdg.findClientRecord(driversLicenseNumber);
        try{
            clientRecord =  new ClientRecord(
                    rs.getString("driversLicenseNumber"),
                    rs.getInt("version"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("phoneNumber"),
                    rs.getDate("expirationDate")

            );
        } catch (SQLException e){
            e.printStackTrace();
        }
        return clientRecord;
    }

    /**
     * Get all client records
     * @return
     * @throws ParseException 
     * @throws NumberFormatException 
     */
    public List getAllObjects() throws NumberFormatException, ParseException {
        List<ClientRecord> clientRecords = new ArrayList<>();
        List<Map<String, Object>> records= clientTdg.findAll();
        
        for(int i=0; i<records.size();i++)
        {
        	ClientRecord clientRecord=  new ClientRecord(
        			records.get(i).get("driversLicenseNumber").toString(),
        			Integer.parseInt(records.get(i).get("version").toString()),
        			records.get(i).get("firstname").toString(),
        			records.get(i).get("lastname").toString(),
        			records.get(i).get("phoneNumber").toString(),
        			new SimpleDateFormat("yyyy-MM-dd").parse(records.get(i).get("expirationDate").toString()))	;
        	clientRecords.add(clientRecord);
        }
        return clientRecords;
    }

    /**
     * Maps the result set to client objects
     * @param clientRecords
     * @param rs
     * @return
     */
    public List FindAll() {
    	
        List<ClientRecord> clientRecords = new ArrayList<>();
        ResultSet rs= clientRecordTdg.getAllObjects();

        try{
            List<ClientRecord> clientRecords = new ArrayList<>();

            while (rs.next()) {
                // retrieve and print the values for the current row
                ClientRecord clientRecord=  new ClientRecord(
                        rs.getString("driversLicenseNumber"),
                        rs.getInt("version"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("phoneNumber"),
                        rs.getDate("expirationDate")

                );
                clientRecords.add(clientRecord);
            }
        } catch(SQLException e){
            System.out.println("Sql exception at ResultSet:" );
            e.printStackTrace();
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