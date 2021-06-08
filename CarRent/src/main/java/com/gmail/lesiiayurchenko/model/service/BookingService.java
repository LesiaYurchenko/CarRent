package com.gmail.lesiiayurchenko.model.service;

import com.gmail.lesiiayurchenko.model.dao.BookingDao;
import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.dao.DaoFactory;
import com.gmail.lesiiayurchenko.model.entity.Account;
import com.gmail.lesiiayurchenko.model.entity.Booking;
import com.gmail.lesiiayurchenko.model.entity.Car;

import java.util.ArrayList;
import java.util.List;

public class BookingService {

    DaoFactory daoFactory = DaoFactory.getInstance();

    public void createBooking(Account account, List<Car> cars, String passport, int leaseTerm, boolean driver) throws DBException {
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
        } catch (DBException e) {
            throw e;
        }
    }

    public Booking getBookingById(int id) throws DBException {
        try (BookingDao dao = daoFactory.createBookingDao()) {
            return dao.findById(id);
        } catch (DBException e) {
            throw e;
        }
    }

    public List<Booking> getNewBookings() throws DBException {
        List<Booking> bookings = new ArrayList<>();
        try (BookingDao dao = daoFactory.createBookingDao()) {
            bookings.addAll(dao.findAllNew());
            bookings.addAll(dao.findAllApproved());
            bookings.addAll(dao.findAllRejected());
            return bookings;
        } catch (DBException e) {
            throw e;
        }
    }

    public List<Booking> getBookingsInUse() throws DBException {
        try (BookingDao dao = daoFactory.createBookingDao()) {
            return dao.findAllPaid();
        } catch (DBException e) {
            throw e;
        }
    }

    public List<Booking> getReturnedBookings() throws DBException {
        try (BookingDao dao = daoFactory.createBookingDao()) {
            return dao.findAllReturned();
        } catch (DBException e) {
            throw e;
        }
    }

    public List<Booking> getBookingsByCustomer(int customerID) throws DBException {
        try (BookingDao dao = daoFactory.createBookingDao()) {
            return dao.findAllByCustomer(customerID);
        } catch (DBException e) {
            throw e;
        }
    }

    public void approveBooking(Booking booking) throws DBException {
        booking.setStatus(Booking.Status.APPROVED);
        try (BookingDao dao = daoFactory.createBookingDao()) {
            dao.update(booking);
        } catch (DBException e) {
            throw e;
        }
    }

    public void rejectBooking(Booking booking) throws DBException {
        booking.setStatus(Booking.Status.REJECTED);
        try (BookingDao dao = daoFactory.createBookingDao()) {
            dao.update(booking);
        } catch (DBException e) {
            throw e;
        }
    }

    public void payBooking(Booking booking) throws DBException {
        booking.setStatus(Booking.Status.PAID);
        try (BookingDao dao = daoFactory.createBookingDao()) {
            dao.update(booking);
        } catch (DBException e) {
            throw e;
        }
    }

    public void registerDamage(Booking booking) throws DBException {
        booking.setDamage(true);
        try (BookingDao dao = daoFactory.createBookingDao()) {
            dao.update(booking);
        } catch (DBException e) {
            throw e;
        }
    }

    public void payDamage(Booking booking) throws DBException {
        if (booking.isDamage()) {
            booking.setDamagePaid(true);
            booking.setStatus(Booking.Status.RETURNED);
        }
        try (BookingDao dao = daoFactory.createBookingDao()) {
            dao.update(booking);
        } catch (DBException e) {
            throw e;
        }
    }

    public void returnBooking(Booking booking) throws DBException {
        if (!booking.isDamage() || (booking.isDamage() && booking.isDamagePaid())) {
            booking.setStatus(Booking.Status.RETURNED);
        }
        try (BookingDao dao = daoFactory.createBookingDao()) {
            dao.update(booking);
        } catch (DBException e) {
            throw e;
        }
    }
}
