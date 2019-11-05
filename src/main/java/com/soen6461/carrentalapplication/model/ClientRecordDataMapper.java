package com.soen6461.carrentalapplication.model;

import com.soen6461.carrentalapplication.tabledatagateway.ClientRecordTdg;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClientRecordDataMapper {

    @Autowired
    private ClientRecordTdg clientRecordTdg;

    /**
     * Insert a client record
     * @param clientRecordToInsert
     * @return
     */
    public boolean insert(ClientRecord clientRecordToInsert) {
        return clientRecordTdg.insert(clientRecordToInsert);
    }

    /**
     * Update a client record
     * @param objectToUpdate
     * @return
     */
    public boolean update(ClientRecord objectToUpdate) {
        return clientRecordTdg.update(objectToUpdate);
    }

    /**
     * Delete the client record
     * @param driversLicenseNumber
     * @return
     */
    public boolean delete(String driversLicenseNumber) {
        return clientRecordTdg.delete(driversLicenseNumber);
    }

    /**
     * Get a client record
     * @param driversLicenseNumber
     * @return
     */
    public List getObject(String driversLicenseNumber) {
        List<ClientRecord> clientRecords = new ArrayList<>();
        ResultSet rs= clientRecordTdg.getObject(driversLicenseNumber);
        return MapClientRecords(clientRecords, rs);
    }

    /**
     * Get all client records
     * @return
     */
    public List getAllObjects() {
        List<ClientRecord> clientRecords = new ArrayList<>();
        ResultSet rs= clientRecordTdg.getAllObjects();
        return MapClientRecords(clientRecords, rs);
    }

    /**
     * Maps the result set to client objects
     * @param clientRecords
     * @param rs
     * @return
     */
    private List MapClientRecords(List<ClientRecord> clientRecords, ResultSet rs) {
        try{
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
    public boolean save(ClientRecord objectToInsert) {

        if (this.clientRecordTdg.getObject(objectToInsert.getDriversLicenseNumber()) == null) {
            return this.clientRecordTdg.insert(objectToInsert);
        }
        return this.clientRecordTdg.update(objectToInsert);
    }
}