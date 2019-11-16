package com.soen6461.carrentalapplication.tabledatagateway;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.soen6461.carrentalapplication.Helpers.DatabaseHelper;


@Repository
public class TransactionHistoryTdg {

	@Autowired
	DataSource dataSource;
	Connection con;
	Statement stmt = null;

	/**
	 * This method inserts transactionhistory
	 * @param transactionId transaction id to gets inserted into table
	 * @param timeStamp timestamp of transaction
	 * @param status status of transaction
	 * @return
	 */
	public boolean insert(String transactionId, String timeStamp,String status
			) {
		String sql = "CREATE TABLE IF NOT EXISTS " + DatabaseHelper.databaseName + ".  transactionHistory   ( \n" +
				"                    transactionId VARCHAR(100) , \n" +
				"                    status VARCHAR(60), \n" +
				"                    timestamp VARCHAR(60),\n" +
				"\n" +
				"\n" +
				"                );";

		String sqlRecord = "INSERT INTO `" + DatabaseHelper.databaseName + "`.`" + "transactionHistory" + "`" +
				"(`transactionId`," +
				"`status`," +
				"`timestamp`) " +
				"VALUES " +
				"(\"" +  transactionId + "\"," +
				"\"" + timeStamp +"\","+
				"\"" + status + "\");";


		try {
			con = this.dataSource.getConnection();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.execute(sqlRecord);
			return true;

		} catch (Exception e) {
			System.out.println("Get object exception" + e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}


	/**
	 * Method gets all transactions based on history
	 * @return returns history of transactions
	 */
	public List<Map<String, Object>> findAll() {
		String sql = "SELECT * FROM " + DatabaseHelper.databaseName + ".transactionHistory";
		try {
			con = this.dataSource.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();

			List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
			while (rs.next()) {

				Map<String, Object> row = new HashMap<String, Object>(columns);
				for (int i = 1; i <= columns; ++i) {
					row.put(md.getColumnName(i), rs.getObject(i));
				}
				rows.add(row);
			}

			return rows;
		} catch (Exception e) {
			System.out.println("Get object exception" + e.getMessage());
			return null;
		} finally {
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}