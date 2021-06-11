package com.gmail.lesiiayurchenko.model.dao.mapper;

import com.gmail.lesiiayurchenko.model.dao.SQLConstants;
import com.gmail.lesiiayurchenko.model.entity.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class BookingMapper implements ObjectMapper<Booking> {

    @Override
    public Booking extractFromResultSet(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setId(rs.getInt(SQLConstants.BOOKING_ID));
        booking.setAccount(null);
        booking.setPassport(rs.getString(SQLConstants.PASSPORT));
        booking.setCars(new ArrayList<>());
        booking.setLeaseTerm(rs.getInt(SQLConstants.LEASE_TERM));
        booking.setDriver(rs.getInt(SQLConstants.DRIVER)!=0);
        booking.setStatus(Booking.Status.values()[rs.getInt(SQLConstants.STATUS_ID)-1]);
        booking.setDamage(rs.getInt(SQLConstants.DAMAGE)!=0);
        booking.setDamagePaid(rs.getInt(SQLConstants.DAMAGE_PAID)!=0);
        return booking;
    }

    @Override
    public Booking makeUnique(Map<Integer, Booking> cache, Booking booking) {
        cache.putIfAbsent(booking.getId(), booking);
        return cache.get(booking.getId());
    }
}
