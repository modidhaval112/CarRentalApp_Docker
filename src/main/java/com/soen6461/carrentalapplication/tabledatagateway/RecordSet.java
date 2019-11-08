package com.soen6461.carrentalapplication.tabledatagateway;

import java.sql.ResultSet;

public interface RecordSet extends ResultSet {
	/* 
	 * Looks like a java.sql.ResultSet,
	 * but isn't.
	 * */
	
	public boolean next() ;
	public java.lang.String getString(java.lang.String arg0) throws RecordSetException
	public boolean getBoolean(java.lang.String arg0) throws RecordSetException
	public byte getByte(java.lang.String arg0) throws RecordSetException
	public short getShort(java.lang.String arg0) throws RecordSetException
	public int getInt(java.lang.String arg0) throws RecordSetException
	public long getLong(java.lang.String arg0) throws RecordSetException
	public float getFloat(java.lang.String arg0) throws RecordSetException
	public double getDouble(java.lang.String arg0) throws RecordSetException
	public java.math.BigDecimal getBigDecimal(java.lang.String arg0, int arg1) throws RecordSetException
	public java.math.BigDecimal getBigDecimal(java.lang.String arg0) throws RecordSetException
	public boolean first() throws RecordSetException
	public boolean last() throws RecordSetException
	public boolean previous() throws RecordSetException;
	
}