package com.soen6461.carrentalapplication.Helpers;

public interface IDataMapper<T> {

    /**
     * Save the data in the database to persist it.
     *
     * @param objectToInsert Object to save in the database.
     */
    boolean save(T objectToInsert);

    /**
     * Method to delete a record from the database
     *
     * @param id Id of the record to delete from the database.
     */
    boolean delete(int id);

    /**
     * Method to retrieve an object from the database.
     *
     * @param id The id of the object to retrieve from the database.
     * @return The object mapping to the given id.
     */
    T getObject(int id);
}
