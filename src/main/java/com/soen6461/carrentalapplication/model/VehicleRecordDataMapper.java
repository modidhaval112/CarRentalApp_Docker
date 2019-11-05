package com.soen6461.carrentalapplication.model;

import com.soen6461.carrentalapplication.Helpers.IDataMapper;
import com.soen6461.carrentalapplication.tabledatagateway.VehicleRecordTdg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class VehicleRecordDataMapper implements IDataMapper<VehicleRecord> {

    private VehicleRecordTdg vehicleRecordTdg;

    /**
     * VehicleRecordDataMapper constructor.
     */
    public VehicleRecordDataMapper() {
        this.vehicleRecordTdg = new VehicleRecordTdg();
    }

    /**
     * Save the data in the database to persist it.
     *
     * @param objectToInsert Object to save in the database.
     */
    @Override
    public boolean save(VehicleRecord objectToInsert) {

        if (this.vehicleRecordTdg.getObject(objectToInsert.getId()) == null) {
            return this.vehicleRecordTdg.insert(objectToInsert);
        }

        return this.vehicleRecordTdg.update(objectToInsert.getId(), objectToInsert);
    }

    /**
     * Method to delete a record from the database
     *
     * @param id Id of the record to delete from the database.
     */
    @Override
    public boolean delete(int id) {
        return this.vehicleRecordTdg.delete(id);
    }

    /**
     * Method to retrieve an object from the database.
     *
     * @param id The id of the object to retrieve from the database.
     * @return The object mapping to the given id.
     */
    @Override
    public VehicleRecord getObject(int id) {
        return this.getObject(id);
    }
}
