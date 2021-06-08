package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Booking;
import com.gmail.lesiiayurchenko.model.service.BookingService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ManagerReturnedBookingsCommand implements Command {
    private static final Logger log = Logger.getLogger(ManagerReturnedBookingsCommand.class);
    BookingService bookingService = new BookingService();

    public ManagerReturnedBookingsCommand(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Booking> returnedBookings = null;
        try {
            returnedBookings = bookingService.getReturnedBookings();
        } catch (DBException e) {
            log.error("Cannot get returned bookings", e);
            return "/WEB-INF/error.jsp";
        }
        request.setAttribute("returnedBookings" , returnedBookings);
        return "/WEB-INF/manager/managerreturnedbookings.jsp";
    }
}
