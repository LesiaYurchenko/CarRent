package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Booking;
import com.gmail.lesiiayurchenko.model.service.BookingService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class ManagerUseBookingsCommand implements Command {
    private static final Logger log = Logger.getLogger(ManagerUseBookingsCommand.class);
    BookingService bookingService;

    public ManagerUseBookingsCommand(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Optional<List<Booking>> useBookings;
        try {
            useBookings = bookingService.getBookingsInUse();
        } catch (DBException e) {
            log.error("Cannot get bookings in use", e);
            return "/WEB-INF/error.jsp";
        }
        useBookings.ifPresent(bookingList -> request.setAttribute("useBookings" , bookingList));

        return "/WEB-INF/manager/managerUseBookings.jsp";
    }
}
