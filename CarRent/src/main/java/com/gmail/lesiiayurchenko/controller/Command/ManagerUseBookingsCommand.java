package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Booking;
import com.gmail.lesiiayurchenko.model.service.BookingService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ManagerUseBookingsCommand implements Command {
    private static final Logger log = Logger.getLogger(ManagerUseBookingsCommand.class);
    BookingService bookingService = new BookingService();

    public ManagerUseBookingsCommand(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Booking> useBookings = null;
        try {
            useBookings = bookingService.getBookingsInUse();
        } catch (DBException e) {
            log.error("Cannot get bookings in use", e);
            return "/WEB-INF/error.jsp";
        }
        request.setAttribute("useBookings" , useBookings);

        return "/WEB-INF/manager/managerusebookings.jsp";
    }
}
