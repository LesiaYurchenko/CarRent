package com.gmail.lesiiayurchenko.model.dao.impl;

import com.gmail.lesiiayurchenko.model.dao.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public AccountDao createAccountDao() throws DBException {
        return new JDBCAccountDao(getConnection());
    }
    @Override
    public CarDao createCarDao() throws DBException {
        return new JDBCCarDao(getConnection());
    }
    @Override
    public BookingDao createBookingDao() throws DBException {
        return new JDBCBookingDao(getConnection());
    }

    private Connection getConnection() throws DBException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        }
    }
}
