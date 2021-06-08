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
                page = "redirect:/customerbasis";
                break;
        }
        return page;
    }

    private String addCar(HttpServletRequest request) {
        Car car = null;
        try {
            car = getCar(request);
        } catch (DBException e) {
            log.error ("Cannot get car", e);
            return "/WEB-INF/error.jsp";
        }
        CommandUtility.putCarIntoSession(request, car);
        return "redirect:/customerbasis";
    }

    private String deleteCar(HttpServletRequest request) {
        Car car = null;
        try {
            car = getCar(request);
        } catch (DBException e) {
            log.error ("Cannot get car", e);
            return "/WEB-INF/error.jsp";
        }
        CommandUtility.removeCarFromSession(request, car);
        return "redirect:/customerbasis";
    }

    private String book(HttpServletRequest request) {
        List<Car> cars = (ArrayList<Car>) request.getSession().getAttribute("cars");
        request.setAttribute("bookedCars" , cars);
        return "/WEB-INF/customer/customerbook.jsp";
    }

    private String order(HttpServletRequest request) {
        List<Car> cars = (ArrayList<Car>) request.getSession().getAttribute("cars");
        Account customer = null;
        try {
            customer = accountService.getAccountById((Integer)request.getSession().getAttribute("id"));
        } catch (DBException e) {
            log.error("Cannot get account by id", e);
            return "/WEB-INF/error.jsp";
        }
        String passport = request.getParameter("passport");
        int leaseTerm = Integer.parseInt(request.getParameter("leaseTerm"));
        boolean driver = "yes".equals(request.getParameter("driver"));
        try {
            bookingService.createBooking(customer, cars, passport, leaseTerm,driver);
        } catch (DBException e) {
            log.error("Cannot create booking", e);
            return "/WEB-INF/error.jsp";
        }
        return "/WEB-INF/customer/customerready.jsp";
    }

    private String payForNew(HttpServletRequest request) {
        try {
            Booking booking = getBooking(request);
            bookingService.payBooking(booking);
        } catch (DBException e) {
            log.error("Cannot pay booking", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/customerbookings";
    }

    private String payForDamage(HttpServletRequest request) {
        try {
            Booking booking = getBooking(request);
            bookingService.payDamage(booking);
        } catch (DBException e) {
            log.error("Cannot pay damage", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/customerbookings";
    }

    private Car getCar (HttpServletRequest request) throws DBException {
        int id = Integer.valueOf(request.getParameter("id"));
        Car car = null;
        try {
            car = carService.getCarById(id);
        } catch (DBException e) {
            log.error("Cannot get car by id", e);
            throw e;
        }
        return car;
    }

    private Booking getBooking (HttpServletRequest request) throws DBException {
        int id = Integer.valueOf(request.getParameter("id"));
        Booking booking = null;
        try {
            booking = bookingService.getBookingById(id);
        } catch (DBException e) {
            log.error("Cannot get booking by id", e);
            throw e;
        }
        return booking;
    }
}