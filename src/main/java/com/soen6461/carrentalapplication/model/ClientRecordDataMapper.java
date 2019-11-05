package com.soen6461.carrentalapplication.model;

import com.soen6461.carrentalapplication.tdg.ClientRecordTdg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ClientRecordDataMapper implements RowMapper {

    @Autowired
    ClientRecordTdg clientRecordTdg;

    @Override
    public ClientRecord mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new ClientRecord(
                rs.getString("driversLicenseNumber"),
                rs.getInt("version"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("phoneNumber"),
                rs.getDate("expirationDate")
        );

    }

        public boolean insert(ClientRecord clientRecordToInsert) {
        return clientRecordTdg.insert(clientRecordToInsert);
        }

        public boolean update(ClientRecord objectToUpdate) {
        return clientRecordTdg.update(objectToUpdate);
    }


}