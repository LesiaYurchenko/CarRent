package com.gmail.lesiiayurchenko.model.dao.impl;

import com.gmail.lesiiayurchenko.model.dao.BookingDao;
import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.dao.SQLConstants;
import com.gmail.lesiiayurchenko.model.dao.mapper.AccountMapper;
import com.gmail.lesiiayurchenko.model.dao.mapper.BookingMapper;
import com.gmail.lesiiayurchenko.model.dao.mapper.CarMapper;
import com.gmail.lesiiayurchenko.model.entity.Account;
import com.gmail.lesiiayurchenko.model.entity.Booking;
import com.gmail.lesiiayurchenko.model.entity.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCBookingDao implements BookingDao {
    private Connection connection;

    public JDBCBookingDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(Booking entity) throws DBException{
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            addBooking(entity);
            entity.setId(getBookingId());
            for (Car car : entity.getCars()) {
                addBookingCars(entity, car);
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DBException(DBException.DBEXCEPTION, e);
        } finally {
            setDefaultCommitParameters(connection);
        }
    }

    private void addBooking(Booking booking) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQLConstants.ADD_BOOKING);
            int k = 1;
            pstmt.setInt(k++, booking.getId());
            pstmt.setInt(k++, booking.getAccount().getId());
            pstmt.setString(k++, booking.getPassport());
            pstmt.setInt(k++, booking.getLeaseTerm());
            pstmt.setInt(k++, booking.isDriver() ? 1 : 0);
            pstmt.setInt(k++, booking.getStatus().ordinal() + 1);
            pstmt.setInt(k++, booking.isDamage() ? 1 : 0);
            pstmt.setInt(k, booking.isDamagePaid() ? 1 : 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            close(pstmt);
        }
    }

    private int getBookingId() throws SQLException {
        int id=0;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(SQLConstants.GET_LAST_INSERT_BOOKING_ID)) {
            while (rs.next()) {
                id = rs.getInt(SQLConstants.ID);
            }
            return id;
        } catch (SQLException e) {
            throw e;
        }
    }

    private void addBookingCars(Booking booking, Car car) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQLConstants.ADD_BOOKING_HAS_CAR);
            int k = 1;
            pstmt.setInt(k++, booking.getId());
            pstmt.setInt(k, car.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            close(pstmt);
        }
    }

    @Override
    public Booking findById(int id) throws DBException{
        Map<Integer, Booking> bookings;

        String query = SQLConstants.FIND_BOOKING_BY_ID;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            bookings = find(rs);
            return bookings.get(id);
        } catch (SQLException e) {
            throw new DBException(DBException.DBEXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Booking> findAll() throws DBException{
        Map<Integer, Booking> bookings;

        String query = SQLConstants.FIND_ALL_BOOKINGS;

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            bookings = find(rs);
            return new ArrayList<>(bookings.values());
        } catch (SQLException e) {
            throw new DBException(DBException.DBEXCEPTION, e);
        }
    }

    @Override
    public List<Booking> findAllNew() throws DBException{
        Map<Integer, Booking> bookings;

        String query = SQLConstants.FIND_ALL_NEW_BOOKINGS;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, Booking.Status.NEW.ordinal()+1);
            rs = pstmt.executeQuery();
            bookings = find(rs);
            return new ArrayList<>(bookings.values());
        } catch (SQLException e) {
            throw new DBException(DBException.DBEXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Booking> findAllApproved()throws DBException {
        Map<Integer, Booking> bookings;

        String query = SQLConstants.FIND_ALL_APPROVED_BOOKINGS;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, Booking.Status.APPROVED.ordinal()+1);
            rs = pstmt.executeQuery();
            bookings = find(rs);
            return new ArrayList<>(bookings.values());
        } catch (SQLException e) {
            throw new DBException(DBException.DBEXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Booking> findAllRejected() throws DBException{
        Map<Integer, Booking> bookings = new HashMap<>();

        String query = SQLConstants.FIND_ALL_REJECTED_BOOKINGS;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, Booking.Status.REJECTED.ordinal()+1);
            rs = pstmt.executeQuery();
            bookings = find(rs);
            return new ArrayList<>(bookings.values());
        } catch (SQLException e) {
            throw new DBException(DBException.DBEXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    public List<Booking> findAllPaid() throws DBException{
        Map<Integer, Booking> bookings = new HashMap<>();

        String query = SQLConstants.FIND_ALL_PAID_BOOKINGS;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, Booking.Status.PAID.ordinal()+1);
            rs = pstmt.executeQuery();
            bookings = find(rs);
            return new ArrayList<>(bookings.values());
        } catch (SQLException e) {
            throw new DBException(DBException.DBEXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    public List<Booking> findAllReturned() throws DBException{
        Map<Integer, Booking> bookings = new HashMap<>();

        String query = SQLConstants.FIND_ALL_RETURNED_BOOKINGS;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, Booking.Status.RETURNED.ordinal()+1);
            rs = pstmt.executeQuery();
            bookings = find(rs);
            return new ArrayList<>(bookings.values());
        } catch (SQLException e) {
            throw new DBException(DBException.DBEXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Booking> findAllByCustomer(int customerID) throws DBException{
        Map<Integer, Booking> bookings = new HashMap<>();

        String query = SQLConstants.FIND_ALL_BOOKINGS_BY_CUSTOMER;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, customerID);
            rs = pstmt.executeQuery();
            bookings = find(rs);
            return new ArrayList<>(bookings.values());
        } catch (SQLException e) {
            throw new DBException(DBException.DBEXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    private Map<Integer, Booking> find(ResultSet rs) throws SQLException {
        Map<Integer, Booking> bookings = new HashMap<>();
        Map<Integer, Car> cars = new HashMap<>();
        Map<Integer, Account> accounts = new HashMap<>();

        BookingMapper bookingMapper = new BookingMapper();
        CarMapper carMapper = new CarMapper();
        AccountMapper accountMapper = new AccountMapper();

        Booking booking = null;

        try {
            while (rs.next()) {
                booking = bookingMapper.extractFromResultSet(rs);
                Car car = carMapper.extractFromResultSet(rs);
                Account account = accountMapper.extractFromResultSet(rs);
                booking = bookingMapper.makeUnique(bookings, booking);
                car = carMapper.makeUnique(cars, car);
                booking.getCars().add(car);
                account = accountMapper.makeUnique(accounts, account);
                booking.setAccount(account);

            }
            return bookings;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void update(Booking entity) throws DBException{
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            updateBooking(entity);
            deleteBookingCars(entity.getId());
            for (Car car : entity.getCars()) {
                addBookingCars(entity, car);
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DBException(DBException.DBEXCEPTION, e);
        } finally {
            setDefaultCommitParameters(connection);
        }
    }

    private void updateBooking(Booking booking) throws DBException{
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQLConstants.UPDATE_BOOKING);
            int k = 1;
            pstmt.setInt(k++, booking.getAccount().getId());
            pstmt.setString(k++, booking.getPassport());
            pstmt.setInt(k++, booking.getLeaseTerm());
            pstmt.setInt(k++, booking.isDriver() ? 1 : 0);
            pstmt.setInt(k++, booking.getStatus().ordinal() + 1);
            pstmt.setInt(k++, booking.isDamage() ? 1 : 0);
            pstmt.setInt(k++, booking.isDamagePaid() ? 1 : 0);
            pstmt.setInt(k, booking.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(DBException.DBEXCEPTION, e);
        } finally {
            close(pstmt);
        }
    }

    public void deleteBookingCars(int bookingID) throws DBException{
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQLConstants.DELETE_BOOKING_HAS_CAR);
            pstmt.setInt(1, bookingID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(DBException.DBEXCEPTION, e);
        } finally {
            close(pstmt);
        }
    }

    @Override
    public void delete(int id) throws DBException {
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            deleteBooking(id);
            deleteBookingCars(id);
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DBException(DBException.DBEXCEPTION, e);
        } finally {
            setDefaultCommitParameters(connection);
        }
    }

    private void deleteBooking(int id) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQLConstants.DELETE_BOOKING);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            close(pstmt);
        }
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void setDefaultCommitParameters(Connection connection) {
        try {
            connection.setAutoCommit(true);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
