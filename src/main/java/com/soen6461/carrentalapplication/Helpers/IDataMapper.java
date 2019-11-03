package com.soen6461.carrentalapplication.Helpers;

public interface IDataMapper<T> {

    /**
     * Insert a new record in the database to persist its data.
     *
     * @param id             Id of the object to insert in the database.
     * @param objectToInsert Object to insert in the database.
     */
    void insert(int id, T objectToInsert);

    /**
     * Method to delete a record from the database
     *
     * @param id Id of the record to delete from the database.
     */
    void delete(int id);

    /**
     * Method to update an object data in the database.
     *
     * @param id             Id of the object to map.
     * @param objectToUpdate Object to update.
     */
    void update(int id, T objectToUpdate);

    /**
     * Method to retrieve an object from the database.
     *
     * @param id The id of the object to retrieve from the database.
     * @return The object mapping to the given id.
     */
    T getObject(int id);
}
