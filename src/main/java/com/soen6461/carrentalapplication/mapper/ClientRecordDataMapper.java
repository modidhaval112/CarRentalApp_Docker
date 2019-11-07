package com.soen6461.carrentalapplication.mapper;

import com.soen6461.carrentalapplication.model.ClientRecord;
import com.soen6461.carrentalapplication.tabledatagateway.ClientRecordTdg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class ClientRecordDataMapper implements RowMapper<ClientRecord> {

    @Autowired
    private ClientRecordTdg clientRecordTdg;

    public ClientRecordDataMapper(){

    }

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
    public void update(ClientRecord objectToUpdate) {
        clientRecordTdg.update(objectToUpdate);
    }

    /**
     * Delete the client record
     * @param driversLicenseNumber
     * @return
     */
    public void delete(String driversLicenseNumber) {
         clientRecordTdg.delete(driversLicenseNumber);
    }

    /**
     * Get a client record
     * @param driversLicenseNumber
     * @return
     */
    public ClientRecord getObject(String driversLicenseNumber) {
        ClientRecord clientRecord= null;
        ResultSet rs= clientRecordTdg.getObject(driversLicenseNumber);
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
     */
    public List getAllObjects() {
        List<ClientRecord> clientRecords = new ArrayList<>();
        clientRecords= clientRecordTdg.getAllObjects();
        return clientRecords;
    }


    /**
     * Maps the result set to client objects
     * @param clientRecords
     * @param rs
     * @return
     */
    private List MapClientRecords(List<ClientRecord> clientRecords, ResultSet rs) {
        try{
            System.out.println(rs+"------------------------------------------------------------------------------");
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
//         this.clientRecordTdg.update(objectToInsert);
//    }

    @Override
    public ClientRecord mapRow(ResultSet rs, int i) throws SQLException {
        ClientRecord clientRecord =  new ClientRecord(
                rs.getString("driversLicenseNumber"),
                rs.getInt("version"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("phoneNumber"),
                rs.getDate("expirationDate")

        );
        return clientRecord;
    }
}