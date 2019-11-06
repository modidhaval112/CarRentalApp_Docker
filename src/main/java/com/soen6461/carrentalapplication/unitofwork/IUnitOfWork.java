package com.soen6461.carrentalapplication.unitofwork;

public interface IUnitOfWork<T> {
    String INSERT = "INSERT";
    String DELETE = "DELETE";
    String MODIFY = "MODIFY";

    /**
     * @return
     */
    boolean commit();

    /**
     * @param obj
     * @return
     */
    void registerDeleted(T obj);

    /**
     * @param obj
     * @return
     */
    void registerDirty(T obj);

    /**
     * @param obj
     * @return
     */
    void registerNew(T obj);

    /**
     * @param obj
     * @return
     */
    void registerClean(T obj);

    /**
     * @return
     */
    boolean rollback();
}
