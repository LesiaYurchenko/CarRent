package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Booking;
import com.gmail.lesiiayurchenko.model.service.BookingService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class ManagerNewBookingsCommand implements Command {
    private static final Logger log = Logger.getLogger(ManagerNewBookingsCommand.class);
    BookingService bookingService;

    public ManagerNewBookingsCommand(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Optional<List<Booking>> newBookings;
        try {
            newBookings = bookingService.getNewBookings();
        } catch (DBException e) {
            log.error("Cannot get new bookings", e);
            return "/WEB-INF/error.jsp";
        }
        newBookings.ifPresent(bookingList -> request.setAttribute("newBookings" , bookingList));

        return "/WEB-INF/manager/managerNewBookings.jsp";
    }
}