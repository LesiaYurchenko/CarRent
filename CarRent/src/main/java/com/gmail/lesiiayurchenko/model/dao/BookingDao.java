package com.gmail.lesiiayurchenko.model.dao;

import com.gmail.lesiiayurchenko.model.entity.Booking;

import java.util.List;

public interface BookingDao extends GenericDao<Booking>{
    List<Booking> findAllNew() throws DBException;
    List<Booking> findAllApproved() throws DBException;
    List<Booking> findAllRejected() throws DBException;
    List<Booking> findAllPaid() throws DBException;
    List<Booking> findAllReturned() throws DBException;
    List<Booking> findAllByCustomer(int customerID) throws DBException;
}
