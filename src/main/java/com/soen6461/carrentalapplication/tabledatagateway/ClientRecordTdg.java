//package com.soen6461.carrentalapplication.tabledatagateway;
//
//import com.soen6461.carrentalapplication.database.DatabaseConnection;
//import com.soen6461.carrentalapplication.mapper.ClientRecordDataMapper;
//import com.soen6461.carrentalapplication.model.ClientRecord;
//
//import java.sql.*;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.util.*;
//
//@Repository
//public class ClientRecordTdg {
//
//    @Autowired
//    DataSource dataSource;
//
////    private DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
////    private Connection connection = databaseConnection.getConnection();
//
//    public ClientRecordTdg() throws SQLException {
//    }
//
//    /**
//     * Insert a new record in the database to persist its data.
//     *
//     * @param clientRecordToInsert Client record to insert in the database.
//     */
//    //@Override
//    public boolean insert(ClientRecord clientRecordToInsert) {
//        String sql = "CREATE TABLE IF NOT EXISTS `carrentaldb`.`" + this.getClientRecordTableName() + "` (" +
//                "    `driversLicenseNumber` VARCHAR(50) PRIMARY KEY," +
//                "    `version` INT," +
//                "    `firstname` VARCHAR(60)," +
//                "    `lastname` VARCHAR(50)," +
//                "    `phoneNumber` VARCHAR(50)," +
//                "    `expirationDate` Date" +
//                ");";
//
//            String sqlRecord = "INSERT INTO `carrentaldb`.`" + this.getClientRecordTableName() + "`" +
//                    "(`driversLicenseNumber`, `version`, `firstname`, `lastname`, `phoneNumber`, `expirationDate`) " +
//                    "VALUES (" +
//                    clientRecordToInsert.getDriversLicenseNumber() + ", " +
//                    clientRecordToInsert.getRecordVersion() + ", " +
//                    "\"" + clientRecordToInsert.getFirstName() + "\", " +
//                    "\"" + clientRecordToInsert.getLastName() + "\", " +
//                    "\"" + clientRecordToInsert.getPhoneNumber() + "\", " +
//                    "\"" + clientRecordToInsert.getExpirationDate() + "\", " +
//                    ");";
//
//        try {
//            Statement stmt = this.dataSource.getConnection().createStatement();
//            stmt.executeUpdate(sql);
//            stmt.execute(sqlRecord);
//            return true;
//
//        } catch (Exception e) {
//            System.out.println("Get object exception" + e.getMessage());
//            return false;
//        }
//
//    }
//
//    /**
//     * Method to delete a record from the database
//     *
//     * @param driversLicenseNumber Driver's license number of the record to delete from the database.
//     */
//    //@Override
//    public boolean delete(String driversLicenseNumber) {
//        String sql = "DELETE from `carrentaldb`.`" + this.getClientRecordTableName() + "`   WHERE `driversLicenseNumber` = '" + driversLicenseNumber + "'";
//        try {
//            Statement stmt = this.dataSource.getConnection().createStatement();
//            stmt.executeUpdate(sql);
//            return true;
//
//        } catch (Exception e) {
//            System.out.println("Get object exception" + e.getMessage());
//            return false;
//        }
//    }
//
//    /**
//     * Method to update an object data in the database.
//     *
//     * @param objectToUpdate Object to update.
//     */
//    //@Override
//    public boolean update(ClientRecord objectToUpdate) {
//        String sql = " UPDATE  `carrentaldb`.`" + this.getClientRecordTableName() + "` SET " +
//                "`driversLicenseNumber`=" +  objectToUpdate.getDriversLicenseNumber() + ", " +
//                "`version`=" + objectToUpdate.getRecordVersion() + ", " +
//                "`firstname`= \"" + objectToUpdate.getFirstName() + "\", " +
//                "`lastname`=\"" + objectToUpdate.getLastName() + "\", " +
//                "`phoneNumber`=\"" + objectToUpdate.getPhoneNumber() + "\", " +
//                "`expirationDate`=\"" + objectToUpdate.getExpirationDate() + "\", " +
//                " WHERE driversLicense=" + objectToUpdate.getDriversLicenseNumber() +";";
//        try {
//            Statement stmt = this.dataSource.getConnection().createStatement();
//            stmt.executeUpdate(sql);
//            return true;
//
//        } catch (Exception e) {
//            System.out.println("Get object exception" + e.getMessage());
//            return false;
//        }
//
//    }
//
//    /**
//     * Method to retrieve an object from the database.
//     *
//     * @param driversLicenseNumber The id of the object to retrieve from the database.
//     * @return The object mapping to the given id.
//     */
//    // @Override
//    public ResultSet getObject(String driversLicenseNumber) {
//        String sql = "SELECT * FROM `carrentaldb`.`" + this.getClientRecordTableName() + "` WHERE driversLicenseNumber = " + driversLicenseNumber;
//
//        try {
//            Statement stmt = this.dataSource.getConnection().createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            return rs;
//
//        } catch (Exception e) {
//            System.out.println("Get object exception" + e.getMessage());
//        }
//        return null;
//    }
//
//    public  List findAll() {
////        List<ClientRecord> results = jdbcTemplate.query("SELECT * FROM carrentaldb.clientRecord", new ClientRecordDataMapper());
////        return results;
//        String sql = "SELECT * FROM `carrentaldb`.`" + "`clientRecord`" ;
//        try{
//        Statement stmt = this.dataSource.getConnection().createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//        ResultSetMetaData md = rs.getMetaData();
//        int columns = md.getColumnCount();
//
//        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
//        while (rs.next()){
//
//            Map<String, Object> row = new HashMap<String, Object>(columns);
//            for(int i = 1; i <= columns; ++i){
//                row.put(md.getColumnName(i), rs.getObject(i));
//            }
//            rows.add(row);
//        }
//        return rows;
//        } catch (Exception e) {
//            System.out.println("Get object exception" + e.getMessage());
//        }
//        return null;
//    }
//
//    private boolean sqlUpdateStatement(String sql) throws SQLException {
//        Boolean isSuccess;
//        Statement statement;
//        try {
//            statement = this.dataSource.getConnection().createStatement();
//            statement.executeUpdate(sql);
//
//            // Releasing the resources
//            if (statement != null) {
//                statement.close();
//            }
//
//            isSuccess = true;
//        } catch (Exception exception) {
//            System.out.println(exception.getMessage());
//            System.out.println(sql);
//            isSuccess = false;
//        }
//
//        return isSuccess;
//    }
//
//    private String getClientRecordTableName() {
//        return "ClientRecord";
//    }
//}
