package com.soen6461.carrentalapplication.tabledatagateway;

import com.soen6461.carrentalapplication.model.ClientRecord;

import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;

public class ClientRecordTdg {

    @Autowired
    DataSource dataSource;
    boolean isDataMapperTest;

    private Connection connection;

    /**
     * Insert a new record in the database to persist its data.
     *
     * @param clientRecordToInsert Client record to insert in the database.
     */
    //@Override
    public boolean insert(ClientRecord clientRecordToInsert) {
        String sql = "CREATE TABLE IF NOT EXISTS `carrentaldb`.`" + this.getClientRecordTableName() + "` (" +
                "    `driversLicenseNumber` String PRIMARY KEY," +
                "    `version` INT," +
                "    `firstname` VARCHAR(60)," +
                "    `lastname` VARCHAR(50)," +
                "    `phoneNumber` VARCHAR(50)," +
                "    `expirationDate` Date(50)," +
                ");";
        if (this.sqlUpdateStatement(sql)) {
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
            return this.sqlUpdateStatement(sqlRecord);
        }

        return false;
    }

    /**
     * Method to delete a record from the database
     *
     * @param driversLicenseNumber Driver's license number of the record to delete from the database.
     */
    //@Override
    public boolean delete(String driversLicenseNumber) {
        String sql = "DELETE from `carrentaldb`.`" + this.getClientRecordTableName() + "` where driversLicenseNumber = '" + driversLicenseNumber + "'";
        return this.sqlUpdateStatement(sql);
    }

    /**
     * Method to update an object data in the database.
     *
     * @param objectToUpdate Object to update.
     */
    //@Override
    public boolean update(ClientRecord objectToUpdate) {
        String sql = " UPDATE  `carrentaldb`.`" + this.getClientRecordTableName() + "` SET " +
                "`driversLicenseNumber`=" +  objectToUpdate.getDriversLicenseNumber() + ", " +
                "`version`=" + objectToUpdate.getRecordVersion() + ", " +
                "`firstname`= \"" + objectToUpdate.getFirstName() + "\", " +
                "`lastname`=\"" + objectToUpdate.getLastName() + "\", " +
                "`phoneNumber`=\"" + objectToUpdate.getPhoneNumber() + "\", " +
                "`expirationDate`=\"" + objectToUpdate.getExpirationDate() + "\", " +
                " WHERE driversLicense=" + objectToUpdate.getDriversLicenseNumber() +";";
        return this.sqlUpdateStatement(sql);
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

        try (Statement stmt = this.connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            return rs;

        } catch (Exception e) {
            System.out.println("Get object exception" + e.getMessage());
        }

        return null;
    }

    public ResultSet getAllObjects() {
        String sql = "SELECT * FROM `carrentaldb`.`" + this.getClientRecordTableName();

        try (Statement stmt = this.connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            return rs;

        } catch (Exception e) {
            System.out.println("Get object exception" + e.getMessage());
        }

        return null;
    }

    /**
     * Method used to allow testing of te vehicle data mapper.
     *
     * @param isTest Set to true when testing the data mapper.
     */
    public void setIsDataMapperTest(Boolean isTest) {
        this.isDataMapperTest = isTest;
    }

    /**
     * Clear the testing data.
     *
     * @return True if the testing data is cleared, false otherwise.
     */
    public boolean clearTestingData() {
        String sql = "DROP TABLE IF EXISTS `carrentaldb`.`VehicleRecordTestTable`";
        return this.sqlUpdateStatement(sql);
    }

    /**
     * Execute an update statement on the database.
     *
     * @param sql The sql statement.
     * @return True if the statement was executed without issues, false otherwise.
     */
    private boolean sqlUpdateStatement(String sql) {

        if(this.connection == null)
        {
            try {
                this.getConnection();
            } catch (Exception e){
                System.out.println("Exception in connecting to the database");
                e.printStackTrace();
            }
        }

        boolean isSuccess;
        Statement statement;

        try {
            statement = this.connection.createStatement();

            statement.executeUpdate(sql);

            // Releasing the resources
            if (statement != null) {
                statement.close();
            }

            isSuccess = true;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println(sql);
            isSuccess = false;
        }

        return isSuccess;
    }

    /**
     * Gets the connection to the database.
     *
     */
    public void getConnection() throws SQLException {
        try{
        this.connection = this.dataSource.getConnection();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

    }


    private String getClientRecordTableName() {
        if (this.isDataMapperTest) {
            return "ClientRecordTestTable";
        }

        return "ClientRecord";
    }
}
