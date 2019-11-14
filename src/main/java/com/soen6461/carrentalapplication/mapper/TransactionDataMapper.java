package com.soen6461.carrentalapplication.mapper;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.soen6461.carrentalapplication.tabledatagateway.ITableGatewayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;

import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.model.VehicleRecord;
import com.soen6461.carrentalapplication.tabledatagateway.TransactionTdg;

@Component
public class TransactionDataMapper implements ITableGatewayMapper<Transaction> {
	@Autowired
	private TransactionTdg transactionTdg;
	VehicleRecordDataMapper vrdm;
	ClientRecordDataMapper crdm;
	

	/**
	 * Transaction record insert mapper
	 *
	 * @param transactionRecord The transaction record to insert in the database.
	 * @return True if the operation was a success, false otherwise.
	 */
	public boolean insert(Transaction transactionRecord) {
		return transactionTdg.insert(1, transactionRecord.getTransactionId(), transactionRecord.getStatus().toString(),
				transactionRecord.getStartDateObject(), transactionRecord.getEndDateObject(), transactionRecord.getVehicleRecord().getLpr(),
				transactionRecord.getClientRecord().getDriversLicenseNumber());
	}

	/**
	 * Delete the Transaction record.
	 *
	 * @param string The transaction id to delete.
	 * @return True if the operation was a success, false otherwise.
	 */
	@Override
	public boolean delete(String string) {
		return false;
	}

	/**
	 * Method to update an object data in the database.
	 *
	 * @param id                Id of the object to map.
	 * @param transactionRecord Object to update.
	 */
	@Override
	public boolean update( Transaction transactionRecord) {
		return transactionTdg.update(transactionRecord.getVersion(), transactionRecord.getTransactionId(), transactionRecord.getStatus().toString(),
				transactionRecord.getStartDateObject(), transactionRecord.getEndDateObject(), transactionRecord.getVehicleRecord().getLpr(),
				transactionRecord.getClientRecord().getDriversLicenseNumber());
	}

	/**
	 * Method to retrieve an object from the database.
	 *
	 * @param id The id of the object to retrieve from the database.
	 * @return The object mapping to the given id.
	 */
	@Override
	public Transaction getObject(int id) {
		return null;
	}

	/**
	 * Get all Transaction records.
	 *
	 * @return True if the operation was a success, false otherwise.
	 * @throws ParseException
	 * @throws NumberFormatException
	 * @throws SQLException 
	 */
	public List findAll() throws NumberFormatException, ParseException, SQLException {
		List<Transaction> transactions = new ArrayList<>();
		List<Map<String, Object>> records = transactionTdg.findAll();

		if(records != null) {
			for (int i = 0; i < records.size(); i++) {
				Transaction transaction = new Transaction(Integer.parseInt(records.get(i).get("version").toString()),
						crdm.find(records.get(i).get("driversLicenseNumber").toString()),
						vrdm.find(records.get(i).get("licensePlateNumber").toString()),
						records.get(i).get("startDate").toString(),
						records.get(i).get("endDate").toString(),
						Transaction.getStatus((records.get(i).get("status").toString())));
				transactions.add(transaction);
			}
		}

		return transactions;
	}    }

