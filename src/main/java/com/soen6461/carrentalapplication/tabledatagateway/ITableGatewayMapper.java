package com.soen6461.carrentalapplication.tabledatagateway;

public interface ITableGatewayMapper<T> {

    /**
     * Insert a new record in the database to persist its data.
     *
     * @param objectToInsert Object to insert in the database.
     * @return True if the operation was a success, false otherwise.
     */
    boolean insert(T objectToInsert);

    /**
     * Method to delete a record from the database
     *
     * @param id Id of the record to delete from the database.
     * @return True if the operation was a success, false otherwise.
     */
    boolean delete(int id);

    /**
     * Method to update an object data in the database.
     *
     * @param id             Id of the object to map.
     * @param objectToUpdate Object to update.
     * @return True if the operation was a success, false otherwise.
     */
    boolean update(int id, T objectToUpdate);

    /**
     * Method to retrieve an object from the database.
     *
     * @param id The id of the object to retrieve from the database.
     * @return The object mapping to the given id.
     */
    T getObject(int id);
}
