package com.soen6461.carrentalapplication.model;

import com.soen6461.carrentalapplication.Helpers.IDataMapper;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Repository;


@Repository
public class VehicleRecordDataMapper implements IDataMapper<VehicleRecord> {

    private VehicleTableDataGateway vehicleTableDataGateway;

    /**
     * VehicleRecordDataMapper constructor.
     */
    public VehicleRecordDataMapper() {
        this.vehicleTableDataGateway = new VehicleTableDataGateway();
    }

    /**
     * Save the data in the database to persist it.
     *
     * @param objectToInsert Object to save in the database.
     */
    @Override
    public boolean save(VehicleRecord objectToInsert) {

        if (this.vehicleTableDataGateway.getObject(objectToInsert.getId()) == null) {
            return this.vehicleTableDataGateway.insert(objectToInsert);
        }

        return this.vehicleTableDataGateway.update(objectToInsert.getId(), objectToInsert);
    }

    /**
     * Insert a new record in the database to persist its data.
     *
     * @param vehicleRecordToInsert Vehicle record to insert in the database.
     */
    @Override
    public boolean insert(VehicleRecord vehicleRecordToInsert) {

        return this.vehicleTableDataGateway.insert(vehicleRecordToInsert);
    }

    /**
     * Method to delete a record from the database
     *
     * @param id Id of the record to delete from the database.
     */
    @Override
    public boolean delete(int id) {
        return this.vehicleTableDataGateway.delete(id);
    }

    /**
     * Method to update an object data in the database.
     *
     * @param id             Id of the object to map.
     * @param objectToUpdate Object to update.
     */
    @Override
    public boolean update(int id, VehicleRecord objectToUpdate) {
        return this.vehicleTableDataGateway.update(id, objectToUpdate);
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
