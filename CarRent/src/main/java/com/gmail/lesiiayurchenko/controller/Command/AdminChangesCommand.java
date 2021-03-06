package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Account;
import com.gmail.lesiiayurchenko.model.entity.Car;
import com.gmail.lesiiayurchenko.model.service.AccountService;
import com.gmail.lesiiayurchenko.model.service.CarService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

public class AdminChangesCommand implements Command {
    private static final Logger log = Logger.getLogger(AdminChangesCommand.class);
    CarService carService;
    AccountService accountService;

    public AdminChangesCommand(CarService carService, AccountService accountService) {
        this.carService = carService;
        this.accountService = accountService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String act = request.getParameter("act");
        String page;
        switch (act) {
            case "Update Car":
                page = updateCar(request);
                break;
            case "Delete Car":
                page = deleteCar(request);
                break;
            case "Add New Car":
                page = createCar(request);
                break;
            case "Block Customer":
                page = blockCustomer(request);
                break;
            case "Unblock Customer":
                page = unblockCustomer(request);
                break;
            case "Block Manager":
                page = blockManager(request);
                break;
            case "Unblock Manager":
                page = unblockManager(request);
                break;
            case "Register New Manager":
                page = registerNewManager(request);
                break;
            default:
                page = "redirect:/adminCars";
                break;
        }
        return page;
    }

    private String updateCar(HttpServletRequest request) {
        Car car;
        try {
            car = getCar(request);
        } catch (DBException e) {
            log.error("Cannot get car", e);
            return "/WEB-INF/error.jsp";
        }
        String model = request.getParameter("model");
        String licensePlate = request.getParameter("licensePlate");
        Car.QualityClass qualityClass = Car.QualityClass.valueOf(request.getParameter("qualityClass"));
        BigDecimal price = new BigDecimal(request.getParameter("price").replace(",", "."));
        boolean available = "yes".equals(request.getParameter("available"));

        car.setModel(model);
        car.setLicensePlate(licensePlate);
        car.setQualityClass(qualityClass);
        car.setPrice(price);
        car.setAvailable(available);

        try {
            carService.updateCar(car);
        } catch (DBException e) {
            log.error("Cannot update car", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/adminCars";
    }

    private String deleteCar(HttpServletRequest request) {
        try {
            Car car = getCar(request);
            carService.makeCarUnavailable(car);
        } catch (DBException e) {
            log.error("Cannot make car available", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/adminCars";
    }

    private String createCar(HttpServletRequest request) {
        String model = request.getParameter("model");
        String licensePlate = request.getParameter("licensePlate");
        Car.QualityClass qualityClass = Car.QualityClass.valueOf(request.getParameter("qualityClass"));
        BigDecimal price = new BigDecimal(request.getParameter("price").replace(",", "."));

        try {
            carService.addCar(model, licensePlate, qualityClass, price);
        } catch (DBException e) {
            log.error("Cannot add car", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/adminCars";
    }

    private String blockCustomer(HttpServletRequest request) {
        try {
            Account account = getAccount(request);
            accountService.blockAccount(account);
        } catch (DBException e) {
            log.error("Cannot block account", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/adminCustomers";
    }

    private String unblockCustomer(HttpServletRequest request) {
        try {
            Account account = getAccount(request);
            accountService.unblockAccount(account);
        } catch (DBException e) {
            log.error("Cannot unblock account", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/adminCustomers";
    }

    private String blockManager(HttpServletRequest request) {
        try {
            Account account = getAccount(request);
            accountService.blockAccount(account);
        } catch (DBException e) {
            log.error("Cannot block account", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/adminManagers";
    }

    private String unblockManager(HttpServletRequest request) {
        try {
            Account account = getAccount(request);
            accountService.unblockAccount(account);
        } catch (DBException e) {
            log.error("Cannot unblock account", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/adminManagers";
    }

    private String registerNewManager(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        try {
            accountService.registerNewManager(login, password, email);
        } catch (DBException e) {
            log.error("Cannot register manager", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/adminManagers";
    }

    private Car getCar(HttpServletRequest request) throws DBException {
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

    private Account getAccount(HttpServletRequest request) throws DBException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Optional<Account> accOpt = accountService.getAccountById(id);
            Account account = null;
            if (accOpt.isPresent()) {
                account = accOpt.get();
            }
            return account;
        } catch (DBException e) {
            log.error("Cannot get account by id", e);
            throw e;
        }
    }
}