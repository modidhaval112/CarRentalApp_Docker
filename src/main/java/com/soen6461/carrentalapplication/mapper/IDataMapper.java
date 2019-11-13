package com.soen6461.carrentalapplication.mapper;

import java.text.ParseException;
import java.util.List;

public interface IDataMapper<T> {

    /**
     * Save the data in the database to persist it.
     *
     * @param objectToSave Object to save in the database.
     * @return True if the operation was a success, false otherwise.
     */
    boolean save(T objectToSave);

    /**
     * Insert an object in the database to persist it.
     *
     * @param objectToInsert The object record to insert in the database.
     * @return True if the operation was a success, false otherwise.
     */
    boolean insert(T objectToInsert);

    /**
     * Update an object in the database to persist it.
     *
     * @param objectToUpdate The object record to update in the database.
     * @return True if the operation was a success, false otherwise.
     */
    boolean update(T objectToUpdate);

    /**
     * Method to delete a record from the database
     *
     * @param id Id of the record to delete from the database.
     * @return True if the operation was a success, false otherwise.
     */
    boolean delete(String id);

    /**
     * Method to retrieve an object from the database.
     *
     * @param id The id of the object to retrieve from the database.
     * @return The object mapping to the given id.
     */
    T find(String id);

    /**
     * Get all object records from the database.
     *
     * @return The list of objects from the database.
     * @throws ParseException Can throw exception if an error occur when parsing an element from the database.
     */
    List<T> findAll() throws ParseException;
}
