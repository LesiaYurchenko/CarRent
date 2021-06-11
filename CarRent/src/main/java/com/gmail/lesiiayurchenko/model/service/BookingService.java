package com.gmail.lesiiayurchenko.model.service;

import com.gmail.lesiiayurchenko.model.dao.BookingDao;
import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.dao.DaoFactory;
import com.gmail.lesiiayurchenko.model.entity.Account;
import com.gmail.lesiiayurchenko.model.entity.Booking;
import com.gmail.lesiiayurchenko.model.entity.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingService {

    DaoFactory daoFactory = DaoFactory.getInstance();

    public Optional<Booking> createBooking(Account account, List<Car> cars, String passport, int leaseTerm, boolean driver) throws DBException {
        Booking booking = new Booking();
        booking.setAccount(account);
        booking.setCars(cars);
        booking.setPassport(passport);
        booking.setLeaseTerm(leaseTerm);
        booking.setDriver(driver);
        booking.setStatus(Booking.Status.NEW);
        booking.setDamage(false);
        booking.setDamagePaid(false);
        try (BookingDao dao = daoFactory.createBookingDao()) {
            dao.create(booking);
            return Optional.ofNullable(booking);
        } catch (DBException e) {
            throw e;
        }
    }

    public Optional<Booking> getBookingById(int id) throws DBException {
        try (BookingDao dao = daoFactory.createBookingDao()) {
            return Optional.ofNullable(dao.findById(id));
        } catch (DBException e) {
            throw e;
        }
    }

    public Optional<List<Booking>> getNewBookings() throws DBException {
        List<Booking> bookings = new ArrayList<>();
        try (BookingDao dao = daoFactory.createBookingDao()) {
            bookings.addAll(dao.findAllNew());
            bookings.addAll(dao.findAllApproved());
            bookings.addAll(dao.findAllRejected());
            return Optional.ofNullable(bookings);
        } catch (DBException e) {
            throw e;
        }
    }

    public Optional<List<Booking>> getBookingsInUse() throws DBException {
        try (BookingDao dao = daoFactory.createBookingDao()) {
            return Optional.ofNullable(dao.findAllPaid());
        } catch (DBException e) {
            throw e;
        }
    }

    public Optional<List<Booking>>  getReturnedBookings() throws DBException {
        try (BookingDao dao = daoFactory.createBookingDao()) {
            return Optional.ofNullable(dao.findAllReturned());
        } catch (DBException e) {
            throw e;
        }
    }

    public Optional<List<Booking>> getBookingsByCustomer(int customerID) throws DBException {
        try (BookingDao dao = daoFactory.createBookingDao()) {
            return Optional.ofNullable(dao.findAllByCustomer(customerID));
        } catch (DBException e) {
            throw e;
        }
    }

    public Optional<Booking> approveBooking(Booking booking) throws DBException {
        booking.setStatus(Booking.Status.APPROVED);
        try (BookingDao dao = daoFactory.createBookingDao()) {
            dao.update(booking);
            return Optional.ofNullable(booking);
        } catch (DBException e) {
            throw e;
        }
    }

    public Optional<Booking> rejectBooking(Booking booking) throws DBException {
        booking.setStatus(Booking.Status.REJECTED);
        try (BookingDao dao = daoFactory.createBookingDao()) {
            dao.update(booking);
            return Optional.ofNullable(booking);
        } catch (DBException e) {
            throw e;
        }
    }

    public Optional<Booking> payBooking(Booking booking) throws DBException {
        booking.setStatus(Booking.Status.PAID);
        try (BookingDao dao = daoFactory.createBookingDao()) {
            dao.update(booking);
            return Optional.ofNullable(booking);
        } catch (DBException e) {
            throw e;
        }
    }

    public Optional<Booking> registerDamage(Booking booking) throws DBException {
        booking.setDamage(true);
        try (BookingDao dao = daoFactory.createBookingDao()) {
            dao.update(booking);
            return Optional.ofNullable(booking);
        } catch (DBException e) {
            throw e;
        }
    }

    public Optional<Booking> payDamage(Booking booking) throws DBException {
        if (booking.isDamage()) {
            booking.setDamagePaid(true);
            booking.setStatus(Booking.Status.RETURNED);
        }
        try (BookingDao dao = daoFactory.createBookingDao()) {
            dao.update(booking);
            return Optional.ofNullable(booking);
        } catch (DBException e) {
            throw e;
        }
    }

    public Optional<Booking> returnBooking(Booking booking) throws DBException {
        if (!booking.isDamage() || (booking.isDamage() && booking.isDamagePaid())) {
            booking.setStatus(Booking.Status.RETURNED);
        }
        try (BookingDao dao = daoFactory.createBookingDao()) {
            dao.update(booking);
            return Optional.ofNullable(booking);
        } catch (DBException e) {
            throw e;
        }
    }
}
