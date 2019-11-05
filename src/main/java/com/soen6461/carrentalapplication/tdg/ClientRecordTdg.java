package com.soen6461.carrentalapplication.tdg;

import com.soen6461.carrentalapplication.model.ClientRecord;
import com.soen6461.carrentalapplication.model.ClientRecordDataMapper;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class ClientRecordTdg {

    @Autowired
    JdbcTemplate jdbcTemplate;

    boolean isDataMapperTest;

    private String dbName = "carRentalDb";
    private String dbms = "mysql";
    private String portNumber = "3306";
    private String serverName = "localhost";
    private Object password = "root";
    private Object userName = "root";

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
    public List getObject(String driversLicenseNumber) {
        String sql = "SELECT * FROM `carrentaldb`.`" + this.getClientRecordTableName() + "` WHERE driversLicenseNumber = " + driversLicenseNumber;

        try (Statement stmt = this.connection.createStatement()) {

            List<ClientRecord> results = jdbcTemplate.query("SELECT * FROM clientRecord", new ClientRecordDataMapper());
            return results;

        } catch (Exception e) {
            System.out.println("Get object exception" + e.getMessage());
        }

        return null;
    }

    public List getAllObjects(String driversLicenseNumber) {
        String sql = "SELECT * FROM `carrentaldb`.`" + this.getClientRecordTableName();

        try (Statement stmt = this.connection.createStatement()) {

            List<ClientRecord> results = jdbcTemplate.query("SELECT * FROM clientRecord", new ClientRecordDataMapper());
            return results;

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
            this.getConnection();
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
    public void getConnection() {

        try {
            Connection conn = null;
            Properties connectionProps = new Properties();
            connectionProps.put("user", this.userName);
            connectionProps.put("password", this.password);

            if (this.dbms.equals("mysql")) {
                conn = DriverManager.getConnection(
                        "jdbc:" + this.dbms + "://" +
                                this.serverName +
                                ":" + this.portNumber + "/",
                        connectionProps);
            } else if (this.dbms.equals("derby")) {
                conn = DriverManager.getConnection(
                        "jdbc:" + this.dbms + ":" +
                                this.dbName +
                                ";create=true",
                        connectionProps);
            }
            System.out.println("Connected to database");
            this.connection = conn;
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
