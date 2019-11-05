package com.soen6461.carrentalapplication.model;

import com.soen6461.carrentalapplication.Helpers.ITableGatewayMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * The vehicle table data gateway dissociates the
 */
public class VehicleTableDataGateway implements ITableGatewayMapper<VehicleRecord> {

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
     * @param vehicleRecordToInsert Vehicle record to insert in the database.
     */
    @Override
    public boolean insert(VehicleRecord vehicleRecordToInsert) {
        String sql = "CREATE TABLE IF NOT EXISTS `carrentaldb`.`" + this.getVehicleRecordTableName() + "` (" +
                "    `id` INT PRIMARY KEY," +
                "    `version` INT," +
                "    `lpr` VARCHAR(60)," +
                "    `carType` VARCHAR(50)," +
                "    `make` VARCHAR(50)," +
                "    `model` VARCHAR(50)," +
                "    `year` INT," +
                "    `color` VARCHAR(50)" +
                ");";
        if (this.sqlUpdateStatement(sql)) {
            String sqlRecord = "INSERT INTO `carrentaldb`.`" + this.getVehicleRecordTableName() + "`" +
                    "(`id`, `version`, `lpr`, `carType`, `make`, `model`, `year`, `color`) " +
                    "VALUES (" +
                    vehicleRecordToInsert.getId() + ", " +
                    vehicleRecordToInsert.getRecordVersion() + ", " +
                    "\"" + vehicleRecordToInsert.getLpr() + "\", " +
                    "\"" + vehicleRecordToInsert.getCarType() + "\", " +
                    "\"" + vehicleRecordToInsert.getMake() + "\", " +
                    "\"" + vehicleRecordToInsert.getModel() + "\", " +
                    vehicleRecordToInsert.getYear() + ", " +
                    "\"" + vehicleRecordToInsert.getColor() + "\"" +
                    ");";
            return this.sqlUpdateStatement(sqlRecord);
        }

        return false;
    }

    /**
     * Method to delete a record from the database
     *
     * @param id Id of the record to delete from the database.
     */
    @Override
    public boolean delete(int id) {
        String sql = "DELETE from `carrentaldb`.`" + this.getVehicleRecordTableName() + "` where id = '" + id + "'";
        return this.sqlUpdateStatement(sql);
    }

    /**
     * Method to update an object data in the database.
     *
     * @param id             Id of the object to map.
     * @param objectToUpdate Object to update.
     */
    @Override
    public boolean update(int id, VehicleRecord objectToUpdate) {
        String sql = " UPDATE  `carrentaldb`.`" + this.getVehicleRecordTableName() + "` SET " +
                "`id`=" +  objectToUpdate.getId() + ", " +
                "`version`=" + objectToUpdate.getRecordVersion() + ", " +
                "`lpr`= \"" + objectToUpdate.getLpr() + "\", " +
                "`carType`=\"" + objectToUpdate.getCarType() + "\", " +
                "`make`=\"" + objectToUpdate.getMake() + "\", " +
                "`model`=\"" + objectToUpdate.getModel() + "\", " +
                "`year`=" + objectToUpdate.getYear() + ", " +
                "`color`=\"" + objectToUpdate.getColor() +"\" " +
                " WHERE id=" + id +";";
        return this.sqlUpdateStatement(sql);
    }

    /**
     * Method to retrieve an object from the database.
     *
     * @param id The id of the object to retrieve from the database.
     * @return The object mapping to the given id.
     */
    @Override
    public VehicleRecord getObject(int id) {
        String sql = "SELECT * FROM `carrentaldb`.`" + this.getVehicleRecordTableName() + "` WHERE id = " + id;

        try (Statement stmt = this.connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            return new VehicleRecord(
                    rs.getInt("id"),
                    rs.getInt("version"),
                    rs.getString("lpr"),
                    rs.getString("carType"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getInt("year"),
                    rs.getString("color"));

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

    private String getVehicleRecordTableName() {
        if (this.isDataMapperTest) {
            return "VehicleRecordTestTable";
        }

        return "VehicleRecord";
    }
}
