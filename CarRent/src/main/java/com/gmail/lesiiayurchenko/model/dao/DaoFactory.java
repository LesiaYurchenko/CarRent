package com.gmail.lesiiayurchenko.model.dao;

import com.gmail.lesiiayurchenko.model.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract AccountDao createAccountDao() throws DBException;
    public abstract CarDao createCarDao() throws DBException;
    public abstract BookingDao createBookingDao() throws DBException;

    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}
