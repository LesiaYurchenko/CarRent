package com.gmail.lesiiayurchenko.model.dao.mapper;

import com.gmail.lesiiayurchenko.model.entity.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class BookingMapper implements ObjectMapper<Booking> {

    @Override
    public Booking extractFromResultSet(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setId(rs.getInt("id_booking"));
        booking.setAccount(null);
        booking.setPassport(rs.getString("passport"));
        booking.setCars(new ArrayList<>());
        booking.setLeaseTerm(rs.getInt("lease_term"));
        booking.setDriver(rs.getInt("driver")!=0);
        booking.setStatus(Booking.Status.values()[rs.getInt("status_id")-1]);
        booking.setDamage(rs.getInt("damage")!=0);
        booking.setDamagePaid(rs.getInt("damage_paid")!=0);
        return booking;
    }

    @Override
    public Booking makeUnique(Map<Integer, Booking> cache, Booking booking) {
        cache.putIfAbsent(booking.getId(), booking);
        return cache.get(booking.getId());
    }
}
