package com.soen6461.carrentalapplication.unitofwork;

public interface IUnitOfWork<T> {
    String INSERT = "INSERT";
    String DELETE = "DELETE";
    String MODIFY = "MODIFY";

    /**
     * Commit the work.
     *
     * @return True if the operation was a success, false otherwise.
     */
    boolean commit();

    /**
     * Register the object to be deleted.
     *
     * @param obj the object to register deleted
     */
    void registerDeleted(T obj);

    /**
     * Register the object with dirty data.
     *
     * @param obj The object to register dirty.
     */
    void registerDirty(T obj);

    /**
     * Register the new object.
     *
     * @param obj The object to register new.
     */
    void registerNew(T obj);

    /**
     * Register clean.
     *
     * @param obj The object to register clean.
     */
    void registerClean(T obj);

    /**
     * Rollback.
     *
     * @return True if the operation was a success, false otherwise.
     */
    boolean rollback();
}
