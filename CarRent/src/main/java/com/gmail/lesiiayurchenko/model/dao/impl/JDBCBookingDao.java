package com.gmail.lesiiayurchenko.model.dao.impl;

import com.gmail.lesiiayurchenko.model.dao.BookingDao;
import com.gmail.lesiiayurchenko.model.dao.DBException;
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
        PreparedStatement pstmt = null;
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            addBooking(entity);
            entity.setId(getBookingId(entity));
            for (Car car : entity.getCars()) {
                addBookingCars(entity, car);
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DBException("DB exception", e);
        } finally {
            close(pstmt);
            setDefaultCommitParameters(connection);
        }
    }

    private void addBooking(Booking booking) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("insert into booking (id, account_id, passport, lease_term, " +
                    "driver, status_id, damage, damage_paid) values (?,?,?,?,?,?,?,?)");
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

    private int getBookingId(Booking booking) throws SQLException {
        int id=0;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID() as id")) {
            while (rs.next()) {
                id = rs.getInt("id");
            }
            return id;
        } catch (SQLException e) {
            throw e;
        }
    }

    private void addBookingCars(Booking booking, Car car) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("insert into booking_has_car (booking_id, car_id) values (?,?)");
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
        Map<Integer, Booking> bookings = new HashMap<>();

        String query = "" +
                " select b.id as id_booking, b.passport, b.lease_term, b.driver, b.status_id, b.damage, b.damage_paid, " +
                " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
                " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
                " from booking b" +
                " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
                " left join car c ON (booking_has_car.car_id = c.id)" +
                " left join account a ON (b.account_id = a.id)" +
                "where b.id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            bookings = find(rs);
            return bookings.get(id);
        } catch (SQLException e) {
            throw new DBException("DB exception", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Booking> findAll() throws DBException{
        Map<Integer, Booking> bookings = new HashMap<>();

        String query = "" +
                " select b.id as id_booking, b.passport, b.lease_term, b.driver, b.status_id, b.damage, b.damage_paid, " +
                " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
                " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
                " from booking b" +
                " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
                " left join car c ON (booking_has_car.car_id = c.id)" +
                " left join account a ON (b.account_id = a.id)";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            bookings = find(rs);
            return new ArrayList<>(bookings.values());
        } catch (SQLException e) {
            throw new DBException("DB exception", e);
        }
    }

    @Override
    public List<Booking> findAllNew() throws DBException{
        Map<Integer, Booking> bookings = new HashMap<>();

        String query = "" +
                " select b.id as id_booking, b.passport, b.lease_term, b.driver, b.status_id, b.damage, b.damage_paid, " +
                " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
                " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
                " from booking b" +
                " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
                " left join car c ON (booking_has_car.car_id = c.id)" +
                " left join account a ON (b.account_id = a.id)" +
                "where b.status_id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, Booking.Status.NEW.ordinal()+1);
            rs = pstmt.executeQuery();
            bookings = find(rs);
            return new ArrayList<>(bookings.values());
        } catch (SQLException e) {
            throw new DBException("DB exception", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Booking> findAllApproved()throws DBException {
        Map<Integer, Booking> bookings = new HashMap<>();

        String query = "" +
                " select b.id as id_booking, b.passport, b.lease_term, b.driver, b.status_id, b.damage, b.damage_paid, " +
                " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
                " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
                " from booking b" +
                " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
                " left join car c ON (booking_has_car.car_id = c.id)" +
                " left join account a ON (b.account_id = a.id)" +
                "where b.status_id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, Booking.Status.APPROVED.ordinal()+1);
            rs = pstmt.executeQuery();
            bookings = find(rs);
            return new ArrayList<>(bookings.values());
        } catch (SQLException e) {
            throw new DBException("DB exception", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Booking> findAllRejected() throws DBException{
        Map<Integer, Booking> bookings = new HashMap<>();

        String query = "" +
                " select b.id as id_booking, b.passport, b.lease_term, b.driver, b.status_id, b.damage, b.damage_paid, " +
                " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
                " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
                " from booking b" +
                " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
                " left join car c ON (booking_has_car.car_id = c.id)" +
                " left join account a ON (b.account_id = a.id)" +
                "where b.status_id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, Booking.Status.REJECTED.ordinal()+1);
            rs = pstmt.executeQuery();
            bookings = find(rs);
            return new ArrayList<>(bookings.values());
        } catch (SQLException e) {
            throw new DBException("DB exception", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    public List<Booking> findAllPaid() throws DBException{
        Map<Integer, Booking> bookings = new HashMap<>();

        String query = "" +
                " select b.id as id_booking, b.passport, b.lease_term, b.driver, b.status_id, b.damage, b.damage_paid, " +
                " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
                " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
                " from booking b" +
                " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
                " left join car c ON (booking_has_car.car_id = c.id)" +
                " left join account a ON (b.account_id = a.id)" +
                "where b.status_id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, Booking.Status.PAID.ordinal()+1);
            rs = pstmt.executeQuery();
            bookings = find(rs);
            return new ArrayList<>(bookings.values());
        } catch (SQLException e) {
            throw new DBException("DB exception", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    public List<Booking> findAllReturned() throws DBException{
        Map<Integer, Booking> bookings = new HashMap<>();

        String query = "" +
                " select b.id as id_booking, b.passport, b.lease_term, b.driver, b.status_id, b.damage, b.damage_paid, " +
                " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
                " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
                " from booking b" +
                " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
                " left join car c ON (booking_has_car.car_id = c.id)" +
                " left join account a ON (b.account_id = a.id)" +
                "where b.status_id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, Booking.Status.RETURNED.ordinal()+1);
            rs = pstmt.executeQuery();
            bookings = find(rs);
            return new ArrayList<>(bookings.values());
        } catch (SQLException e) {
            throw new DBException("DB exception", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Booking> findAllByCustomer(int customerID) throws DBException{
        Map<Integer, Booking> bookings = new HashMap<>();

        String query = "" +
                " select b.id as id_booking, b.passport, b.lease_term, b.driver, b.status_id, b.damage, b.damage_paid, " +
                " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
                " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
                " from booking b" +
                " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
                " left join car c ON (booking_has_car.car_id = c.id)" +
                " left join account a ON (b.account_id = a.id)" +
                "where a.id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, customerID);
            rs = pstmt.executeQuery();
            bookings = find(rs);
            return new ArrayList<>(bookings.values());
        } catch (SQLException e) {
            throw new DBException("DB exception", e);
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
        PreparedStatement pstmt = null;
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
            throw new DBException("DB exception", e);
        } finally {
            setDefaultCommitParameters(connection);
            close(pstmt);
        }
    }

    private void updateBooking(Booking booking) throws DBException{
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("UPDATE booking SET account_id = ?, passport = ?, " +
                    "lease_term = ?, driver = ?, status_id = ?, damage = ?, damage_paid = ?" +
                    "	WHERE id = ?");
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
            throw new DBException("DB exception", e);
        } finally {
            close(pstmt);
        }
    }

    public void deleteBookingCars(int bookingID) throws DBException{
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("delete from booking_has_car where booking_id = ?");
            pstmt.setInt(1, bookingID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("DB exception", e);
        } finally {
            close(pstmt);
        }
    }

    @Override
    public void delete(int id) throws DBException {
        PreparedStatement pstmt = null;
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            deleteBooking(id);
            deleteBookingCars(id);
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DBException("DB exception", e);
        } finally {
            setDefaultCommitParameters(connection);
            close(pstmt);
        }
    }

    private void deleteBooking(int id) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("delete from booking where id = ?");
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
