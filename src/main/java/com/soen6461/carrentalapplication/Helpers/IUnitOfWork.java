package com.soen6461.carrentalapplication.Helpers;

public interface IUnitOfWork {

    /**
     * @return
     */
    boolean commit();

    /**
     * @param obj
     * @return
     */
    boolean registerDeleted(Object obj);

    /**
     * @param obj
     * @return
     */
    boolean registerDirty(Object obj);

    /**
     * @param obj
     * @return
     */
    boolean registerNew(Object obj);

    /**
     * @param obj
     * @return
     */
    boolean registerClean(Object obj);

    /**
     * @return
     */
    boolean rollback();
}
