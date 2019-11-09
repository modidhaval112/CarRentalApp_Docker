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
     * @param vehicleRecordToInsert
     * @return
     */
    public boolean insert(VehicleRecord vehicleRecordToInsert) {
    	return vehicleRecordTdg.insert(vehicleRecordToInsert.getId(), vehicleRecordToInsert.getRecordVersion(),vehicleRecordToInsert.getLpr(),vehicleRecordToInsert.getCarType(),vehicleRecordToInsert.getMake(),vehicleRecordToInsert.getModel(),vehicleRecordToInsert.getYear(), vehicleRecordToInsert.getColor());
    }

    /**
     * Update a vehicle record
     * @param objectToUpdate
     * @return
     */
    public boolean update(VehicleRecord objectToUpdate) {
        return this.vehicleRecordTdg.update(objectToUpdate.getId(), objectToUpdate.getRecordVersion(),objectToUpdate.getLpr(),objectToUpdate.getCarType(),objectToUpdate.getMake(),objectToUpdate.getModel(),objectToUpdate.getYear(), objectToUpdate.getColor());
    }

    /**
     * Delete the vehicle record
     * @param lpr
     * @return
     */
    public boolean delete(String lpr) {
        return vehicleRecordTdg.delete(lpr);
    }

    /**
     * Get all vehicle records
     * @return
     * @throws ParseException 
     * @throws NumberFormatException 
     */
    public List findAll() throws NumberFormatException, ParseException, SQLException {
        List<VehicleRecord> vehicleRecords = new ArrayList<>();
        List<Map<String, Object>> records= vehicleRecordTdg.findAll();
        
		/*
		 * for(int i=0; i<records.size();i++) { VehicleRecord vehicleRecord= new
		 * VehicleRecord( Integer.parseInt(records.get(i).get("id").toString()),
		 * Integer.parseInt(records.get(i).get("version").toString()),
		 * records.get(i).get("lpr").toString(),
		 * records.get(i).get("carType").toString(),
		 * records.get(i).get("make").toString(),
		 * records.get(i).get("model").toString(),
		 * Integer.parseInt(records.get(i).get("year").toString()),
		 * records.get(i).get("color").toString()); vehicleRecords.add(vehicleRecord); }
		 */
        return vehicleRecords;
    }
}