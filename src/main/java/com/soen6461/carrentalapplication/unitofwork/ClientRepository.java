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

import com.soen6461.carrentalapplication.mapper.ClientRecordDataMapper;
import com.soen6461.carrentalapplication.model.ClientRecord;

@Component
public class ClientRepository implements IUnitOfWork<ClientRecord> {
    HashMap<String, List<ClientRecord>> context = new HashMap<String, List<ClientRecord>>();
    /**
     * supports unit of work for client data.
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRecordDataMapper.class);
    private Map<String, Boolean> dirtyMap = new HashMap<>();
    private LinkedList<String> deleteRecords = new LinkedList<>();
    private LinkedList<String> deletedClientRecords = new LinkedList<>();

    @Autowired
    private ClientRecordDataMapper clientRecordDataMapper;

    public ClientRepository() {
    }
    
    public Map<String, Boolean> getDirtyMap() {
        return dirtyMap;
    }

    public void setDirtyMap(Map<String, Boolean> dirtyMap) {
        this.dirtyMap = dirtyMap;
    }
    
    public LinkedList<String> getDeleteRecords() {
		return deleteRecords;
	}

	public void setDeleteRecords(LinkedList<String> deleteRecords) {
		this.deleteRecords = deleteRecords;
	}
	
    public LinkedList<String> getDeletedClientRecords() {
		return deletedClientRecords;
	}

	public void setDeletedClientRecords(LinkedList<String> deletedClientRecords) {
		this.deletedClientRecords = deletedClientRecords;
	}

	@Override
    public void registerNew(ClientRecord clientRecord) {
        LOGGER.info("Registering {} for insert in context.", clientRecord.getFirstName());
        register(clientRecord, IUnitOfWork.INSERT);
    }

    @Override
    public void registerDirty(ClientRecord clientRecord) {
        LOGGER.info("Registering {} for modify in context.", clientRecord.getFirstName());
        register(clientRecord, IUnitOfWork.MODIFY);
    }

    @Override
    public void registerDeleted(ClientRecord clientRecord) {
        LOGGER.info("Registering {} for delete in context.", clientRecord.getFirstName());
        register(clientRecord, IUnitOfWork.DELETE);
    }

    private void register(ClientRecord clientRecord, String operation) {
        List clientsToOperate = context.get(operation); // Retrieve list of clients that are newly registered to get
        if (clientsToOperate == null) {
            clientsToOperate = new ArrayList<>();
        }
        clientsToOperate.add(clientRecord);
        context.put(operation, clientsToOperate); // Old list is replaced with new list
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
        context = new HashMap<String, List<ClientRecord>>();
        LOGGER.info("Commit finished.");
        return true;
    }

    private void commitInsert() {
        List<ClientRecord> clientsToBeInserted = context.get(IUnitOfWork.INSERT);
        for (ClientRecord clientRecord : clientsToBeInserted) {
            LOGGER.info("Saving {} to database.", clientRecord.getFirstName());
            clientRecordDataMapper.insert(clientRecord);
        }
    }

    private void commitModify() {
        List<ClientRecord> modifiedClients = context.get(IUnitOfWork.MODIFY);
        for (ClientRecord clientRecord : modifiedClients) {
            LOGGER.info("Modifying {} to database.", clientRecord.getFirstName());
            clientRecordDataMapper.update(clientRecord);
        }
        
        this.dirtyMap = new HashMap<>();
    }

    private void commitDelete() {
        List<ClientRecord> deletedClients = context.get(IUnitOfWork.DELETE);
        for (ClientRecord clientRecord : deletedClients) {
            LOGGER.info("Deleting {} to database.", clientRecord.getFirstName());
            clientRecordDataMapper.delete(clientRecord.getDriversLicenseNumber());
        }
        this.deleteRecords = new LinkedList<>();
    }

    //Todo: Implement rollback
    @Override
    public boolean rollback() {
        return false;
    }

    @Override
    public void registerClean(ClientRecord obj) {
        //Todo:Implement
    }
}
