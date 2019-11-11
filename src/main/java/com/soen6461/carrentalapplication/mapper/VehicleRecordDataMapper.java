package com.soen6461.carrentalapplication.mapper;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soen6461.carrentalapplication.model.VehicleRecord;
import com.soen6461.carrentalapplication.tabledatagateway.VehicleRecordTdg;

@Component
public class VehicleRecordDataMapper {

    @Autowired
    private VehicleRecordTdg vehicleRecordTdg;

    /**
     * Insert a vehicle record
     *
     * @param vehicleRecordToInsert The vehicle record to insert in the database.
     * @return True if the operation was a success, false otherwise.
     */
    public boolean insert(VehicleRecord vehicleRecordToInsert) {
        return vehicleRecordTdg.insert(vehicleRecordToInsert.getId(), vehicleRecordToInsert.getVersion(),
                vehicleRecordToInsert.getLpr(), vehicleRecordToInsert.getCarType(), vehicleRecordToInsert.getMake(),
                vehicleRecordToInsert.getModel(), vehicleRecordToInsert.getYear(), vehicleRecordToInsert.getColor());
    }

    /**
     * Update a vehicle record
     *
     * @param objectToUpdate Vehicle record to update
     * @return True if the operation was a success, false otherwise.
     */
    public boolean update(VehicleRecord objectToUpdate) {
        return this.vehicleRecordTdg.update(objectToUpdate.getId(), objectToUpdate.getVersion(),
                objectToUpdate.getLpr(), objectToUpdate.getCarType(), objectToUpdate.getMake(),
                objectToUpdate.getModel(), objectToUpdate.getYear(), objectToUpdate.getColor());
    }

    /**
     * Delete the vehicle record
     *
     * @param lpr The license plate number.
     * @return True if the operation was a success, false otherwise.
     */
    public boolean delete(String lpr) {
        return vehicleRecordTdg.delete(lpr);
    }

    /**
     * Get all vehicle records.
     *
     * @return True if the operation was a success, false otherwise.
     * @throws ParseException
     * @throws NumberFormatException
     */
    public List<VehicleRecord> findAll() throws NumberFormatException, ParseException, SQLException {
        List<VehicleRecord> vehicleRecords = new ArrayList<>();
        List<Map<String, Object>> records = vehicleRecordTdg.findAll();

        if(records != null) {
            for (int i = 0; i < records.size(); i++) {
                VehicleRecord vehicleRecord = new VehicleRecord(Integer.parseInt(records.get(i).get("id").toString()),
                        Integer.parseInt(records.get(i).get("version").toString()), records.get(i).get("licensePlateNumber").toString(),
                        records.get(i).get("carType").toString(), records.get(i).get("make").toString(),
                        records.get(i).get("model").toString(), Integer.parseInt(records.get(i).get("year").toString()),
                        records.get(i).get("color").toString());
                vehicleRecords.add(vehicleRecord);
            }
        }

        return vehicleRecords;
    }


    /**
     * Get all vehicle records
     *
     * @return True if the operation was a success, false otherwise.
     * @throws ParseException
     * @throws NumberFormatException
     */
    public VehicleRecord findVehicle(String lpr) throws NumberFormatException, ParseException, SQLException {
        Map<String, Object> record = vehicleRecordTdg.findVehicle(lpr);

        VehicleRecord vehicleRecord = new VehicleRecord(1,
                Integer.parseInt(record.get("version").toString()), record.get("licensePlateNumber").toString(),
                record.get("carType").toString(), record.get("make").toString(),
                record.get("model").toString(), Integer.parseInt(record.get("year").toString()),
                record.get("color").toString());

        return vehicleRecord;
    }
}
