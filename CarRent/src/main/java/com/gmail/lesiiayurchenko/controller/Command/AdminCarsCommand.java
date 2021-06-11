package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Car;
import com.gmail.lesiiayurchenko.model.service.CarService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class AdminCarsCommand implements Command {
    private static final Logger log = Logger.getLogger(AdminCarsCommand.class);
    CarService carService;

    public AdminCarsCommand(CarService carService) {
        this.carService = carService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Optional<String> page = Optional.ofNullable(request.getParameter("currentPage"));
        int currentPage = Integer.parseInt(page.orElse("1"));
        Optional<List<Car>> cars;
        try {
            cars = carService.getAllCarsPagination(currentPage, 2);
        } catch (DBException e) {
            log.error("Cannot get cars for pagination", e);
            return "/WEB-INF/error.jsp";
        }
        cars.ifPresent(carList -> request.setAttribute("cars", carList));
        int numberOfRows;
        try {
            numberOfRows = carService.getNumberOfRowsAll();
        } catch (DBException e) {
            log.error("Cannot get number of rows", e);
            return "/WEB-INF/error.jsp";
        }
        int numberOfPages = numberOfRows / 2;
        if (numberOfRows % 2 > 0) {
            numberOfPages++;
        }
        request.setAttribute("noOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);
        return "/WEB-INF/admin/admincars.jsp";
    }
}
