package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Booking;
import com.gmail.lesiiayurchenko.model.service.BookingService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class ManagerReturnedBookingsCommand implements Command {
    private static final Logger log = Logger.getLogger(ManagerReturnedBookingsCommand.class);
    BookingService bookingService;

    public ManagerReturnedBookingsCommand(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Optional<List<Booking>> returnedBookings;
        try {
            returnedBookings = bookingService.getReturnedBookings();
        } catch (DBException e) {
            log.error("Cannot get returned bookings", e);
            return "/WEB-INF/error.jsp";
        }
        returnedBookings.ifPresent(bookingList -> request.setAttribute("returnedBookings" , bookingList));
        return "/WEB-INF/manager/managerReturnedBookings.jsp";
    }
}
