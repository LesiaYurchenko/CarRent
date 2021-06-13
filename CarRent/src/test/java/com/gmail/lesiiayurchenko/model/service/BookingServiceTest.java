package com.gmail.lesiiayurchenko.model.service;

import com.gmail.lesiiayurchenko.model.dao.BookingDao;
import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.dao.DaoFactory;
import com.gmail.lesiiayurchenko.model.entity.Account;
import com.gmail.lesiiayurchenko.model.entity.Booking;
import com.gmail.lesiiayurchenko.model.entity.Car;
import junit.framework.TestCase;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTest extends TestCase {

    @Mock
    DaoFactory mockDaoFactory;
    @Mock
    BookingDao mockBookingDao;

    @InjectMocks
    BookingService instance;

    Account customer = new Account (100, "customer", "customer", "customer@gmail.com",
            Account.Role.CUSTOMER, false);
    Car car = new Car(100, "Porshe panamera", "AA4672IB", Car.QualityClass.LUXURY,
            new BigDecimal("50"), true);
    List<Car> cars = new ArrayList<>();
    Booking bookingAppr = new Booking (101, customer, "123456789", cars, 2, true,
            Booking.Status.APPROVED, false, false);
    Booking bookingPaid = new Booking (102, customer, "123456789", cars, 2, true,
            Booking.Status.PAID, false, false);
    Booking bookingNew = new Booking (1001, customer, "123456789", cars, 2, true,
                 Booking.Status.NEW, false, false);
    List<Booking> bookingsAppr= new ArrayList<>();
    List<Booking> bookingsPaid= new ArrayList<>();
    List<Booking> bookingsNew= new ArrayList<>();
    List<Booking> bookingsEmpty= new ArrayList<>();

    public BookingServiceTest() {
    }

    @Before
    public void setUp() throws DBException {
        cars.add(car);
        bookingsAppr.add(bookingAppr);
        bookingsPaid.add(bookingPaid);
        bookingsNew.add(bookingNew);
        when(mockDaoFactory.createBookingDao()).thenReturn(mockBookingDao);
        doNothing().when(mockBookingDao).create(any(Booking.class));
        doNothing().when(mockBookingDao).update(any(Booking.class));
        when(mockBookingDao.findById(anyInt())).thenReturn(bookingAppr);
        when(mockBookingDao.findAllNew()).thenReturn(bookingsNew);
        when(mockBookingDao.findAllApproved()).thenReturn(bookingsAppr);
        when(mockBookingDao.findAllRejected()).thenReturn(bookingsEmpty);
        when(mockBookingDao.findAllPaid()).thenReturn(bookingsPaid);
        when(mockBookingDao.findAllReturned()).thenReturn(bookingsEmpty);
        when(mockBookingDao.findAllByCustomer(anyInt())).thenReturn(bookingsAppr);

        instance = new BookingService(mockDaoFactory);
    }

    @Test
    public void testCreateBooking() throws DBException {
        Booking actual = bookingAppr;
        Optional<Booking> b = instance.createBooking(customer, cars, "987654321", 10, false);

        if (b.isPresent()){
            actual = b.get();
        }

        Booking predicted = new Booking (0, customer, "987654321",cars,  10, false,
                Booking.Status.NEW, false,false);

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).create(actual);
        Assert.assertEquals(actual, predicted);
    }

    @Test(expected = DBException.class)
    public void testCreateBookingException() throws DBException {
        doThrow(new DBException()).when(mockBookingDao).create(any(Booking.class));
        Booking actual = bookingAppr;
        Optional<Booking> b = instance.createBooking(customer, cars, "987654321", 10, false);

        if (b.isPresent()){
            actual = b.get();
        }

        Booking predicted = new Booking (0, customer, "987654321",cars,  10, false,
                Booking.Status.NEW, false,false);

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).create(actual);
        Assert.assertNotEquals(actual, predicted);
    }

    @Test
    public void testGetBookingById() throws DBException {
        instance.getBookingById(101);

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).findById(101);
    }

    @Test(expected = DBException.class)
    public void testGetBookingByIdException() throws DBException {
        when(mockBookingDao.findById(anyInt())).thenThrow(new DBException());
        instance.getBookingById(101);

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).findById(101);
    }

    @Test
    public void testGetNewBookings() throws DBException {
        instance.getNewBookings();

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).findAllNew();
        verify(mockBookingDao, times(1)).findAllApproved();
        verify(mockBookingDao, times(1)).findAllRejected();
    }

    @Test(expected = DBException.class)
    public void testGetNewBookingsException() throws DBException {
        when(mockBookingDao.findAllNew()).thenThrow(new DBException());
        when(mockBookingDao.findAllApproved()).thenThrow(new DBException());
        when(mockBookingDao.findAllRejected()).thenThrow(new DBException());
        instance.getNewBookings();

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).findAllNew();
        verify(mockBookingDao, times(1)).findAllApproved();
        verify(mockBookingDao, times(1)).findAllRejected();
    }

    @Test
    public void testGetBookingsInUse() throws DBException {
        instance.getBookingsInUse();

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).findAllPaid();
    }

    @Test(expected = DBException.class)
    public void testGetBookingsInUseException() throws DBException {
        when(mockBookingDao.findAllPaid()).thenThrow(new DBException());
        instance.getBookingsInUse();

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).findAllPaid();
    }

    @Test
    public void testGetReturnedBookings() throws DBException {
        instance.getReturnedBookings();

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).findAllReturned();
    }

    @Test(expected = DBException.class)
    public void testGetReturnedBookingsException() throws DBException {
        when(mockBookingDao.findAllReturned()).thenThrow(new DBException());
        instance.getReturnedBookings();

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).findAllReturned();
    }

    @Test
    public void testGetBookingsByCustomer() throws DBException {
        instance.getBookingsByCustomer(100);

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).findAllByCustomer(100);
    }

    @Test(expected = DBException.class)
    public void testGetBookingsByCustomerException() throws DBException {
        when(mockBookingDao.findAllByCustomer(anyInt())).thenThrow(new DBException());
        instance.getBookingsByCustomer(100);

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).findAllByCustomer(100);
    }

    @Test
    public void testApproveBooking() throws DBException {
        Booking actual = bookingNew;
        Optional<Booking> c = instance.approveBooking(actual);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).update(actual);
        Assert.assertEquals(Booking.Status.APPROVED, actual.getStatus());
    }

    @Test(expected = DBException.class)
    public void testApproveBookingException() throws DBException {
        doThrow(new DBException()).when(mockBookingDao).update(any(Booking.class));
        Booking actual =  bookingNew;
        Optional<Booking> c = instance.approveBooking(actual);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).update(actual);
        Assert.assertNotEquals(Booking.Status.APPROVED, actual.getStatus());
    }

    @Test
    public void testRejectBooking() throws DBException {
        Booking actual =  bookingNew;
        Optional<Booking> c = instance.rejectBooking(actual);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).update(actual);
        Assert.assertEquals(Booking.Status.REJECTED, actual.getStatus());
    }

    @Test(expected = DBException.class)
    public void testRejectBookingException() throws DBException {
        doThrow(new DBException()).when(mockBookingDao).update(any(Booking.class));
        Booking actual =  bookingNew;
        Optional<Booking> c = instance.rejectBooking(actual);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).update(actual);
        Assert.assertNotEquals(Booking.Status.REJECTED, actual.getStatus());
    }

    @Test
    public void testPayBooking() throws DBException {
        Booking actual =  bookingNew;
        Optional<Booking> c = instance.payBooking(actual);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).update(actual);
        Assert.assertEquals(Booking.Status.PAID, actual.getStatus());
    }

    @Test(expected = DBException.class)
    public void testPayBookingException() throws DBException {
        doThrow(new DBException()).when(mockBookingDao).update(any(Booking.class));
        Booking actual =  bookingNew;
        Optional<Booking> c = instance.payBooking(actual);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).update(actual);
        Assert.assertNotEquals(Booking.Status.PAID, actual.getStatus());
    }

    @Test
    public void testRegisterDamage() throws DBException {
        Booking actual =  bookingPaid;
        Optional<Booking> c = instance.registerDamage(actual);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).update(actual);
        Assert.assertTrue(actual.isDamage());
    }

    @Test(expected = DBException.class)
    public void testRegisterDamageException() throws DBException {
        doThrow(new DBException()).when(mockBookingDao).update(any(Booking.class));
        Booking actual =  bookingPaid;
        Optional<Booking> c = instance.registerDamage(actual);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).update(actual);
        Assert.assertFalse(actual.isDamage());
    }

    @Test
    public void testPayDamage() throws DBException {
        Booking actual =  bookingPaid;
        actual.setDamage(true);
        Optional<Booking> c = instance.payDamage(actual);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).update(actual);
        Assert.assertTrue(actual.isDamagePaid());
    }

    @Test(expected = DBException.class)
    public void testPayDamageException() throws DBException {
        doThrow(new DBException()).when(mockBookingDao).update(any(Booking.class));
        Booking actual =  bookingPaid;
        Optional<Booking> c = instance.payDamage(actual);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).update(actual);
        Assert.assertFalse(actual.isDamagePaid());
    }

    @Test
    public void testReturnBooking() throws DBException {
        Booking actual =  bookingPaid;
        Optional<Booking> c = instance.returnBooking(actual);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).update(actual);
        Assert.assertEquals(Booking.Status.RETURNED, actual.getStatus());
    }

    @Test(expected = DBException.class)
    public void testReturnBookingException() throws DBException {
        doThrow(new DBException()).when(mockBookingDao).update(any(Booking.class));
        Booking actual =  bookingPaid;
        Optional<Booking> c = instance.returnBooking(actual);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createBookingDao();
        verify(mockBookingDao, times(1)).update(actual);
        Assert.assertNotEquals(Booking.Status.RETURNED, actual.getStatus());
    }
}