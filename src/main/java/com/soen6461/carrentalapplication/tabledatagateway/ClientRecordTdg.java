package com.soen6461.carrentalapplication.tabledatagateway;

import com.soen6461.carrentalapplication.mapper.ClientRecordDataMapper;
import com.soen6461.carrentalapplication.model.ClientRecord;

import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@Repository
public class ClientRecordTdg {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Insert a new record in the database to persist its data.
     *
     * @param clientRecordToInsert Client record to insert in the database.
     */
    //@Override
    public boolean insert(ClientRecord clientRecordToInsert) {
        String sql = "CREATE TABLE IF NOT EXISTS `carrentaldb`.`" + this.getClientRecordTableName() + "` (" +
                "    `driversLicenseNumber` VARCHAR(50) PRIMARY KEY," +
                "    `version` INT," +
                "    `firstname` VARCHAR(60)," +
                "    `lastname` VARCHAR(50)," +
                "    `phoneNumber` VARCHAR(50)," +
                "    `expirationDate` Date" +
                ");";

            String sqlRecord = "INSERT INTO `carrentaldb`.`" + this.getClientRecordTableName() + "`" +
                    "(`driversLicenseNumber`, `version`, `firstname`, `lastname`, `phoneNumber`, `expirationDate`) " +
                    "VALUES (" +
                    clientRecordToInsert.getDriversLicenseNumber() + ", " +
                    clientRecordToInsert.getRecordVersion() + ", " +
                    "\"" + clientRecordToInsert.getFirstName() + "\", " +
                    "\"" + clientRecordToInsert.getLastName() + "\", " +
                    "\"" + clientRecordToInsert.getPhoneNumber() + "\", " +
                    "\"" + clientRecordToInsert.getExpirationDate() + "\", " +
                    ");";
            try{
                jdbcTemplate.execute(sql);
            jdbcTemplate.execute(sqlRecord);
            } catch (DataIntegrityViolationException e) {
                e.printStackTrace();
                return false;
            }
        return true;

    }

    /**
     * Method to delete a record from the database
     *
     * @param driversLicenseNumber Driver's license number of the record to delete from the database.
     */
    //@Override
    public void delete(String driversLicenseNumber) {
        String sql = "DELETE from `carrentaldb`.`" + this.getClientRecordTableName() + "`   WHERE `driversLicenseNumber` = '" + driversLicenseNumber + "'";
        jdbcTemplate.execute(sql);
    }

    /**
     * Method to update an object data in the database.
     *
     * @param objectToUpdate Object to update.
     */
    //@Override
    public void update(ClientRecord objectToUpdate) {
        String sql = " UPDATE  `carrentaldb`.`" + this.getClientRecordTableName() + "` SET " +
                "`driversLicenseNumber`=" +  objectToUpdate.getDriversLicenseNumber() + ", " +
                "`version`=" + objectToUpdate.getRecordVersion() + ", " +
                "`firstname`= \"" + objectToUpdate.getFirstName() + "\", " +
                "`lastname`=\"" + objectToUpdate.getLastName() + "\", " +
                "`phoneNumber`=\"" + objectToUpdate.getPhoneNumber() + "\", " +
                "`expirationDate`=\"" + objectToUpdate.getExpirationDate() + "\", " +
                " WHERE driversLicense=" + objectToUpdate.getDriversLicenseNumber() +";";
        jdbcTemplate.execute(sql);
    }

    /**
     * Method to retrieve an object from the database.
     *
     * @param driversLicenseNumber The id of the object to retrieve from the database.
     * @return The object mapping to the given id.
     */
    // @Override
    public ResultSet getObject(String driversLicenseNumber) {
        String sql = "SELECT * FROM `carrentaldb`.`" + this.getClientRecordTableName() + "` WHERE driversLicenseNumber = " + driversLicenseNumber;

        try  {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            System.out.println("Get object exception" + e.getMessage());
        }

        return null;
    }

    public List<ClientRecord> getAllObjects() {
        List<ClientRecord> results = jdbcTemplate.query("SELECT * FROM carrentaldb.clientRecord", new ClientRecordDataMapper());
        return results;
    }

    /**
     * Clear the testing data.
     *
     * @return True if the testing data is cleared, false otherwise.
     */



    private String getClientRecordTableName() {

        return "ClientRecord";
    }
}
