package com.soen6461.carrentalapplication.unitofwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soen6461.carrentalapplication.mapper.VehicleRecordDataMapper;
import com.soen6461.carrentalapplication.model.VehicleRecord;
@Component
public class VehicleRepository implements IUnitOfWork<VehicleRecord> {
    HashMap<String,List<VehicleRecord>> context = new HashMap<String, List<VehicleRecord>>();
    /**
     * supports unit of work for vehicle data.
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleRecordDataMapper.class);

    @Autowired
    private VehicleRecordDataMapper vehicleRecordDataMapper;

    public VehicleRepository() {

    }

    @Override
    public void registerNew(VehicleRecord vehicleRecord) {
        LOGGER.info("Registering {} for insert in context.", vehicleRecord.getLpr());
        register(vehicleRecord, IUnitOfWork.INSERT);
    }

    @Override
    public void registerDirty(VehicleRecord vehicleRecord) {
        LOGGER.info("Registering {} for modify in context.", vehicleRecord.getLpr());
        register(vehicleRecord, IUnitOfWork.MODIFY);
    }

    @Override
    public void registerDeleted(VehicleRecord vehicleRecord) {
        LOGGER.info("Registering {} for delete in context.", vehicleRecord.getLpr());
        register(vehicleRecord, IUnitOfWork.DELETE);
    }

    private void register(VehicleRecord vehicleRecord, String operation) {
        List vehiclesToOperate = context.get(operation); // Retrieve list of vehicles that are newly registered to get
        if (vehiclesToOperate == null) {
            vehiclesToOperate = new ArrayList<>();
        }
        vehiclesToOperate.add(vehicleRecord);
        context.put(operation, vehiclesToOperate); // Old list is replaced with new list
    }

    /**
     * All UnitOfWork operations are batched and executed together on commit only.
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

    private void commitInsert() {
        List<VehicleRecord> vehiclesToBeInserted = context.get(IUnitOfWork.INSERT);
        for (VehicleRecord vehicleRecord : vehiclesToBeInserted) {
            LOGGER.info("Saving {} to database.", vehicleRecord.getLpr());
            vehicleRecordDataMapper.insert(vehicleRecord);
        }
    }

    private void commitModify() {
        List<VehicleRecord> modifiedClients = context.get(IUnitOfWork.MODIFY);
        for (VehicleRecord vehicleRecord : modifiedClients) {
            LOGGER.info("Modifying {} to database.", vehicleRecord.getLpr());
            vehicleRecordDataMapper.update(vehicleRecord);
        }
    }

    private void commitDelete() {
        List<VehicleRecord> deletedClients = context.get(IUnitOfWork.DELETE);
        for (VehicleRecord vehicleRecord : deletedClients) {
            LOGGER.info("Deleting {} to database.", vehicleRecord.getLpr());
            vehicleRecordDataMapper.delete(vehicleRecord.getLpr());
        }
    }

    //Todo: Implement rollback
    @Override
    public boolean rollback() {
        return false;
    }

    @Override
    public void registerClean(VehicleRecord obj) {
        //Todo:Implement
    }
}
