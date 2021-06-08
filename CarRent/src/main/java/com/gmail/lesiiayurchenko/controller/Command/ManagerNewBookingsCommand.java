package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Booking;
import com.gmail.lesiiayurchenko.model.service.BookingService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ManagerNewBookingsCommand implements Command {
    private static final Logger log = Logger.getLogger(ManagerNewBookingsCommand.class);
    BookingService bookingService = new BookingService();

    public ManagerNewBookingsCommand(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Booking> newBookings = null;
        try {
            newBookings = bookingService.getNewBookings();
        } catch (DBException e) {
            log.error("Cannot get new bookings", e);
            return "/WEB-INF/error.jsp";
        }
        request.setAttribute("newBookings" , newBookings);

        return "/WEB-INF/manager/managernewbookings.jsp";
    }
}