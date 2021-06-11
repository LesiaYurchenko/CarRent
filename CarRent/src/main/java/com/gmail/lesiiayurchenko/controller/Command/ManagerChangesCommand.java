package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Booking;
import com.gmail.lesiiayurchenko.model.service.BookingService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ManagerChangesCommand implements Command {
    private static final Logger log = Logger.getLogger(ManagerChangesCommand.class);
    BookingService bookingService;

    public ManagerChangesCommand(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String act = request.getParameter("act");
        String page;
        switch (act) {
            case "Send Damage Bill":
                page = sendDamageBill(request);
            break;
            case "Register Damage Bill Payment":
                page = registerDamageBillPayment(request);
            break;
            case "Return Booking":
                page = returnBooking(request);
            break;
            case "Approve Booking":
                page = approveBooking(request);
            break;
            case "Reject Booking":
                page = rejectBooking(request);
            break;
            default:
                page = "redirect:/managerbasis";
            break;
        }
        return page;
    }

    private String sendDamageBill(HttpServletRequest request) {
        try {
            Booking booking = getBooking(request);
            bookingService.registerDamage(booking);
        } catch (DBException e) {
            log.error("Cannot register damage", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/managerusebookings";
    }

    private String registerDamageBillPayment(HttpServletRequest request) {
        try {
            Booking booking = getBooking(request);
            bookingService.payDamage(booking);
        } catch (DBException e) {
            log.error("Cannot pay damage", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/managerusebookings";
    }

    private String returnBooking(HttpServletRequest request) {
        try {
            Booking booking = getBooking(request);
            bookingService.returnBooking(booking);
        } catch (DBException e) {
            log.error("Cannot return booking", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/managerusebookings";
    }

    private String approveBooking(HttpServletRequest request) {
        try {
            Booking booking = getBooking(request);
            bookingService.approveBooking(booking);
        } catch (DBException e) {
            log.error("Cannot approve booking", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/managernewbookings";
    }

    private String rejectBooking(HttpServletRequest request) {
        try {
            Booking booking = getBooking(request);
            bookingService.rejectBooking(booking);
        } catch (DBException e) {
            log.error("Cannot reject booking", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/managernewbookings";
    }

    private Booking getBooking (HttpServletRequest request) throws DBException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Optional<Booking> bookingOpt = bookingService.getBookingById(id);
            Booking booking = null;
            if (bookingOpt.isPresent()) {
                booking = bookingOpt.get();
            }
            return booking;
        } catch (DBException e) {
            log.error("Cannot get booking by id", e);
            throw e;
        }
    }
}