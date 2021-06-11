package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Car;
import com.gmail.lesiiayurchenko.model.service.CarService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class MainCommand implements Command {
    private static final Logger log = Logger.getLogger(MainCommand.class);
    CarService carService;

    public MainCommand(CarService carService) {
        this.carService = carService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Optional<String> page = Optional.ofNullable(request.getParameter("currentPage"));
        int currentPage = Integer.parseInt(page.orElse("1"));
        Optional<List<Car>> cars;
        try {
            cars = carService.getAvailableCarsPagination(currentPage, 3);
        } catch (DBException e) {
            log.error("Cannot get available cars pagination", e);
            return "/WEB-INF/error.jsp";
        }
        cars.ifPresent(carList -> request.setAttribute("cars", carList));
        int numberOfRows;
        try {
            numberOfRows = carService.getNumberOfRowsAvailable();
        } catch (DBException e) {
            log.error("Cannot get number of rows for cars", e);
            return "/WEB-INF/error.jsp";
        }
        int numberOfPages = numberOfRows / 3;
        if (numberOfRows % 3 > 0) {
            numberOfPages++;
        }
        request.setAttribute("noOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);
        return "/main.jsp";
    }
}