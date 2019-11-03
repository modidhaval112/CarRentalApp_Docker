package com.soen6461.carrentalapplication.model;

import com.soen6461.carrentalapplication.Helpers.IDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Repository
public class VehicleRecordDataMapper implements IDataMapper<VehicleRecord> {

    @Autowired
    JdbcTemplate jdbcTemplate;

    boolean isDataMapperTest;
    private String dbName;
    private String dbms;
    private String portNumber;
    private String serverName;
    private Object password;
    private Object userName;

    /**
     * Insert a new record in the database to persist its data.
     *
     * @param id                    Id of the object to insert in the database.
     * @param vehicleRecordToInsert Vehicle record to insert in the database.
     */
    @Override
    public void insert(int id, VehicleRecord vehicleRecordToInsert) {
        this.jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS " + this.getVehicleRecordTableName() + " (" +
                        "    id INT PRIMARY KEY," +
                        "    lpr VARCHAR(60) PRIMARY KEY," +
                        "    carType VARCHAR(50)," +
                        "    make VARCHAR(50)," +
                        "    model VARCHAR(50)," +
                        "    year INT," +
                        "    color VARCHAR(50)" +
                        ");");
        String sql = "insert into " + this.getVehicleRecordTableName() + " values(?,?,?,?,?)";
        this.jdbcTemplate.update(
                sql,
                vehicleRecordToInsert.getLpr(),
                vehicleRecordToInsert.getCarType(),
                vehicleRecordToInsert.getMake(),
                vehicleRecordToInsert.getModel(),
                vehicleRecordToInsert.getYear(),
                vehicleRecordToInsert.getColor());
    }

    /**
     * Method to delete a record from the database
     *
     * @param id Id of the record to delete from the database.
     */
    @Override
    public void delete(int id) {
        this.jdbcTemplate.execute("DELETE from " + this.getVehicleRecordTableName() + " where id = '" + id + "'");
    }

    /**
     * Method to update an object data in the database.
     *
     * @param id             Id of the object to map.
     * @param objectToUpdate Object to update.
     */
    @Override
    public void update(int id, VehicleRecord objectToUpdate) {

    }

    /**
     * Method to retrieve an object from the database.
     *
     * @param id The id of the object to retrieve from the database.
     * @return The object mapping to the given id.
     */
    @Override
    public VehicleRecord getObject(int id) {
        String sql = "SELECT * FROM " + this.getVehicleRecordTableName() + " WHERE id = '" + id + "'";

        //// List<VehicleRecord> resultSet = jdbcTemplate.query(sql, new VehicleRowMapper());
        //// return resultSet;

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

    public void clearTestingData() {

        this.jdbcTemplate.execute("DROP TABLE IF EXISTS VehicleRecordTestTable");

    }

    private String getVehicleRecordTableName() {
        if (this.isDataMapperTest) {
            return "VehicleRecordTestTable";
        }

        return "VehicleRecord";
    }

    public Connection getConnection() throws SQLException {

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
        return conn;
    }
}
