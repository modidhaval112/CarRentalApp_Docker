package com.soen6461.carrentalapplication.unitofwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soen6461.carrentalapplication.mapper.VehicleRecordDataMapper;
import com.soen6461.carrentalapplication.model.VehicleRecord;

@Component
public class VehicleRepository implements IUnitOfWork<VehicleRecord> {
    HashMap<String, List<VehicleRecord>> context = new HashMap<String, List<VehicleRecord>>();
    /**
     * supports unit of work for vehicle data.
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleRecordDataMapper.class);
    private Map<String, Boolean> dirtyMap = new HashMap<>();
    private LinkedList<String> deleteRecords = new LinkedList<>();
    private LinkedList<String> deletedVehicleRecords = new LinkedList<>();

    @Autowired
    private VehicleRecordDataMapper vehicleRecordDataMapper;

    /**
     * Default constructor.
     */
    public VehicleRepository() {

    }

    /**
     * Gets the dirty map.
     *
     * @return The dirty map.
     */
    public Map<String, Boolean> getDirtyMap() {
        return dirtyMap;
    }

    /**
     * Sets the dirty map.
     *
     * @param dirtyMap The dirty map.
     */
    public void setDirtyMap(Map<String, Boolean> dirtyMap) {
        this.dirtyMap = dirtyMap;
    }

    /**
     * Gets the delete record.
     *
     * @return List of deleted records.
     */
    public LinkedList<String> getDeleteRecords() {
        return deleteRecords;
    }

    /**
     * Sets the deleted records.
     *
     * @param deleteRecords List of deleted records.
     */
    public void setDeleteRecords(LinkedList<String> deleteRecords) {
        this.deleteRecords = deleteRecords;
    }

    /**
     * Gets deleted client records.
     *
     * @return A list of deleted client records.
     */
    public LinkedList<String> getDeletedVehicleRecords() {
        return deletedVehicleRecords;
    }

    /**
     * Sets the deleted vehicle records.
     *
     * @param deletedVehicleRecords A list of deleted client records.
     */
    public void setDeletedVehicleRecords(LinkedList<String> deletedVehicleRecords) {
        this.deletedVehicleRecords = deletedVehicleRecords;
    }

    /**
     * Register the new object.
     *
     * @param vehicleRecord The object to register new.
     */
    @Override
    public void registerNew(VehicleRecord vehicleRecord) {
        LOGGER.info("Registering {} for insert in context.", vehicleRecord.getLpr());
        register(vehicleRecord, IUnitOfWork.INSERT);
    }

    /**
     * Register the object with dirty data.
     *
     * @param vehicleRecord The object to register dirty.
     */
    @Override
    public void registerDirty(VehicleRecord vehicleRecord) {
        LOGGER.info("Registering {} for modify in context.", vehicleRecord.getLpr());
        register(vehicleRecord, IUnitOfWork.MODIFY);
    }

    /**
     * Register the object to be deleted.
     *
     * @param vehicleRecord the object to register deleted
     */
    @Override
    public void registerDeleted(VehicleRecord vehicleRecord) {
        LOGGER.info("Registering {} for delete in context.", vehicleRecord.getLpr());
        register(vehicleRecord, IUnitOfWork.DELETE);
    }

    /**
     * Register work.
     *
     * @param vehicleRecord The vehicle record.
     * @param operation     The operation.
     */
    private void register(VehicleRecord vehicleRecord, String operation) {
        List vehiclesToOperate = context.get(operation); // Retrieve list of vehicles that are newly registered to get
        if (vehiclesToOperate == null) {
            vehiclesToOperate = new ArrayList<>();
        }
        vehiclesToOperate.add(vehicleRecord);
        context.put(operation, vehiclesToOperate); // Old list is replaced with new list
    }

    /**
     * Commit the work.
     *
     * @return True if the operation was a success, false otherwise.
     */
    @Override
    public boolean commit() {
        if (context == null || context.size() == 0) {
            return false;
        }
        LOGGER.info("Commit started");
        if (context.containsKey(IUnitOfWork.INSERT)) {
            commitInsert();
        }

        if (context.containsKey(IUnitOfWork.MODIFY)) {
            commitModify();
        }
        if (context.containsKey(IUnitOfWork.DELETE)) {
            commitDelete();
        }
        context = new HashMap<String, List<VehicleRecord>>();
        LOGGER.info("Commit finished.");
        return true;
    }

    /**
     * Commit insert work.
     */
    private void commitInsert() {
        List<VehicleRecord> vehiclesToBeInserted = context.get(IUnitOfWork.INSERT);
        for (VehicleRecord vehicleRecord : vehiclesToBeInserted) {
            LOGGER.info("Saving {} to database.", vehicleRecord.getLpr());
            vehicleRecordDataMapper.insert(vehicleRecord);
        }
    }

    /**
     * Commit modify work.
     */
    private void commitModify() {
        List<VehicleRecord> modifiedClients = context.get(IUnitOfWork.MODIFY);
        for (VehicleRecord vehicleRecord : modifiedClients) {
            LOGGER.info("Modifying {} to database.", vehicleRecord.getLpr());
            vehicleRecordDataMapper.update(vehicleRecord);
        }

        this.dirtyMap = new HashMap<>();
    }

    /**
     * Commit delete work.
     */
    private void commitDelete() {
        List<VehicleRecord> deletedClients = context.get(IUnitOfWork.DELETE);
        for (VehicleRecord vehicleRecord : deletedClients) {
            LOGGER.info("Deleting {} to database.", vehicleRecord.getLpr());
            vehicleRecordDataMapper.delete(vehicleRecord.getLpr());
            deletedVehicleRecords.add(vehicleRecord.getLpr());
        }
        this.deleteRecords = new LinkedList<>();
    }

    /**
     * Rollback.
     *
     * @return True if the operation was a success, false otherwise.
     */
    @Override
    public boolean rollback() {
        return false;
    }

    /**
     * Register clean.
     *
     * @param obj The object to register clean.
     */
    @Override
    public void registerClean(VehicleRecord obj) {
    }
}
