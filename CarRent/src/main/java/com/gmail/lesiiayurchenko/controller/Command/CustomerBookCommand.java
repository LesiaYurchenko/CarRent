package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Account;
import com.gmail.lesiiayurchenko.model.entity.Booking;
import com.gmail.lesiiayurchenko.model.entity.Car;
import com.gmail.lesiiayurchenko.model.service.AccountService;
import com.gmail.lesiiayurchenko.model.service.BookingService;
import com.gmail.lesiiayurchenko.model.service.CarService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class CustomerBookCommand implements Command {
    private static final Logger log = Logger.getLogger(CustomerBookCommand.class);
    CarService carService;
    AccountService accountService;
    BookingService bookingService;

    public CustomerBookCommand(CarService carService, BookingService bookingService, AccountService accountService) {
        this.carService = carService;
        this.accountService = accountService;
        this.bookingService = bookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String act = request.getParameter("act");
        String page;
        switch (act) {
            case "Add Car":
                page = addCar(request);
                break;
            case "Delete Car":
                page = deleteCar(request);
                break;
            case "Book":
                page = book(request);
                break;
            case "Order":
                page = order(request);
                break;
            case "Pay":
                page = payForNew(request);
                break;
            case "Pay Damage":
                page = payForDamage(request);
                break;
            default:
                page = "redirect:/customerBasis";
                break;
        }
        return page;
    }

    private String addCar(HttpServletRequest request) {
        Car car;
        try {
            car = getCar(request);
        } catch (DBException e) {
            log.error ("Cannot get car", e);
            return "/WEB-INF/error.jsp";
        }
        CommandUtility.putCarIntoSession(request, car);
        return "redirect:/customerBasis";
    }

    private String deleteCar(HttpServletRequest request) {
        Car car;
        try {
            car = getCar(request);
        } catch (DBException e) {
            log.error ("Cannot get car", e);
            return "/WEB-INF/error.jsp";
        }
        CommandUtility.removeCarFromSession(request, car);
        return "redirect:/customerBasis";
    }

    private String book(HttpServletRequest request) {
        List<Car> cars = (ArrayList<Car>) request.getSession().getAttribute("cars");
        request.setAttribute("bookedCars" , cars);
        return "/WEB-INF/customer/customerBook.jsp";
    }

    private String order(HttpServletRequest request) {
        List<Car> cars = (ArrayList<Car>) request.getSession().getAttribute("cars");
        Optional<Account> customer;
        try {
            customer = accountService.getAccountById((Integer)request.getSession().getAttribute("id"));
        } catch (DBException e) {
            log.error("Cannot get account by id", e);
            return "/WEB-INF/error.jsp";
        }
        String passport = request.getParameter("passport");
        int leaseTerm = Integer.parseInt(request.getParameter("leaseTerm"));
        boolean driver = "yes".equals(request.getParameter("driver"));
        Account acc = null;
        if (customer.isPresent()){
            acc = customer.get();
        }
        try {
            bookingService.createBooking(acc, cars, passport, leaseTerm,driver);
        } catch (DBException e) {
            log.error("Cannot create booking", e);
            return "/WEB-INF/error.jsp";
        }
        return "/WEB-INF/customer/customerReady.jsp";
    }

    private String payForNew(HttpServletRequest request) {
        try {
            Booking booking = getBooking(request);
            bookingService.payBooking(booking);
        } catch (DBException e) {
            log.error("Cannot pay booking", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/customerBookings";
    }

    private String payForDamage(HttpServletRequest request) {
        try {
            Booking booking = getBooking(request);
            bookingService.payDamage(booking);
        } catch (DBException e) {
            log.error("Cannot pay damage", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/customerBookings";
    }

    private Car getCar (HttpServletRequest request) throws DBException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Optional<Car> carOpt = carService.getCarById(id);
            Car car = null;
            if (carOpt.isPresent()) {
                car = carOpt.get();
            }
            return car;
        } catch (DBException e) {
            log.error("Cannot get car by id", e);
            throw e;
        }
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