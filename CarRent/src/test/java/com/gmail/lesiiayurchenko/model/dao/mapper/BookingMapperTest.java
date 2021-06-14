package com.gmail.lesiiayurchenko.model.dao.mapper;

import com.gmail.lesiiayurchenko.model.dao.SQLConstants;
import com.gmail.lesiiayurchenko.model.entity.Booking;
import com.gmail.lesiiayurchenko.model.entity.Car;
import junit.framework.TestCase;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookingMapperTest extends TestCase {
    @Mock
    ResultSet mockRS;

    Booking booking = new Booking (100, null, "123456789", new ArrayList<Car>(), 2,
            true, Booking.Status.APPROVED, false, false);
    Map<Integer, Booking> cache = new HashMap<>();
    BookingMapper instance = new BookingMapper();

    @Before
    public void setUp() throws SQLException {
        when(mockRS.getInt(SQLConstants.BOOKING_ID)).thenReturn(100);
        when(mockRS.getString(SQLConstants.PASSPORT)).thenReturn("123456789");
        when(mockRS.getInt(SQLConstants.LEASE_TERM)).thenReturn(2);
        when(mockRS.getInt(SQLConstants.DRIVER)).thenReturn(1);
        when(mockRS.getInt(SQLConstants.STATUS_ID)).thenReturn(3);
        when(mockRS.getInt(SQLConstants.DAMAGE)).thenReturn(0);
        when(mockRS.getInt(SQLConstants.DAMAGE_PAID)).thenReturn(0);
        cache.put(booking.getId(), booking);
    }

    @Test
    public void testExtractFromResultSet() throws SQLException {
        Booking actual = instance.extractFromResultSet(mockRS);

        verify(mockRS, times(1)).getInt(SQLConstants.BOOKING_ID);
        verify(mockRS, times(1)).getString(SQLConstants.PASSPORT);
        verify(mockRS, times(1)).getInt(SQLConstants.LEASE_TERM);
        verify(mockRS, times(1)).getInt(SQLConstants.DRIVER);
        verify(mockRS, times(1)).getInt(SQLConstants.STATUS_ID);
        verify(mockRS, times(1)).getInt(SQLConstants.DAMAGE);
        verify(mockRS, times(1)).getInt(SQLConstants.DAMAGE_PAID);

        Assert.assertEquals(booking.getId(), actual.getId());
        Assert.assertEquals(booking.getPassport(), actual.getPassport());
        Assert.assertEquals(booking.getLeaseTerm(), actual.getLeaseTerm());
        Assert.assertEquals(booking.isDriver(), actual.isDriver());
        Assert.assertEquals(Booking.Status.APPROVED, actual.getStatus());
        Assert.assertEquals(booking.isDamage(), actual.isDamage());
        Assert.assertEquals(booking.isDamagePaid(), actual.isDamagePaid());
    }

    @Test(expected = SQLException.class)
    public void testExtractFromResultSetException() throws SQLException {
        when(mockRS.getInt(SQLConstants.BOOKING_ID)).thenThrow(new SQLException());
        Booking actual = instance.extractFromResultSet(mockRS);

        verify(mockRS, times(1)).getInt(SQLConstants.BOOKING_ID);
        verify(mockRS, times(1)).getString(SQLConstants.PASSPORT);
        verify(mockRS, times(1)).getInt(SQLConstants.LEASE_TERM);
        verify(mockRS, times(1)).getInt(SQLConstants.DRIVER);
        verify(mockRS, times(1)).getInt(SQLConstants.STATUS_ID);
        verify(mockRS, times(1)).getInt(SQLConstants.DAMAGE);
        verify(mockRS, times(1)).getInt(SQLConstants.DAMAGE_PAID);

        Assert.assertEquals(booking.getId(), actual.getId());
        Assert.assertEquals(booking.getPassport(), actual.getPassport());
        Assert.assertEquals(booking.getLeaseTerm(), actual.getLeaseTerm());
        Assert.assertEquals(booking.isDriver(), actual.isDriver());
        Assert.assertEquals(Booking.Status.APPROVED, actual.getStatus());
        Assert.assertEquals(booking.isDamage(), actual.isDamage());
        Assert.assertEquals(booking.isDamagePaid(), actual.isDamagePaid());
    }

    @Test
    public void testMakeUnique() {
        Booking actual = instance.makeUnique(cache, booking);
        Assert.assertEquals(booking, actual);
        Assert.assertEquals(cache.size(), 1);
    }
}