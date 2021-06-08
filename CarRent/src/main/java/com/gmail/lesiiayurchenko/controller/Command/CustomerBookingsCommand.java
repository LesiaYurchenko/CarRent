package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Booking;
import com.gmail.lesiiayurchenko.model.service.BookingService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerBookingsCommand implements Command {
    private static final Logger log = Logger.getLogger(CustomerBookingsCommand.class);
    BookingService bookingService;

    public CustomerBookingsCommand(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int customerID = (Integer) request.getSession().getAttribute("id");
        List<Booking> bookings = null;
        try {
            bookings = bookingService.getBookingsByCustomer(customerID);
        } catch (DBException e) {
            log.error("Cannot get booking by customer", e);
            return "/WEB-INF/error.jsp";
        }

        request.setAttribute("payForNew" , bookings.stream().filter(b -> Booking.Status.APPROVED.equals(b.getStatus()))
                .collect(Collectors.toList()));

        request.setAttribute("payForDamaged" , bookings.stream().filter(b -> b.isDamage()&& !b.isDamagePaid())
                .collect(Collectors.toList()));

        request.setAttribute("myBookings" , bookings);

        return "/WEB-INF/customer/customerbookings.jsp";
    }
}