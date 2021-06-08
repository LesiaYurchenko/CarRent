package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Car;
import com.gmail.lesiiayurchenko.model.service.CarService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


public class CustomerBasisCommand implements Command {
    private static final Logger log = Logger.getLogger(CustomerBasisCommand.class);
    CarService carService;

    public CustomerBasisCommand(CarService carService) {
        this.carService = carService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String filter = checkFilter(request);
        String sorter = checkSorter(request);
        Car.QualityClass qualityClass = (Car.QualityClass) request.getSession().getAttribute("carQualityClass");

        Optional<String> page = Optional.ofNullable(request.getParameter("currentPage"));
        int currentPage = Integer.parseInt(page.orElse("1"));

        List<Car> cars = null;
        int numberOfRows = 0;
        try {
            switch (filter) {
                case "qualityClass":
                    if (sorter.equals("price")) {
                        cars = getCarsByQualityClassByPrice(request, currentPage, qualityClass);
                    } else {
                        cars = getCarsByQualityClass(request, currentPage, qualityClass);
                    }
                    numberOfRows = getNumberOfRowsByQualityClass(qualityClass);
                    break;
                default:
                    if (sorter.equals("price")) {
                        cars = getCarsByPrice(request, currentPage);
                    } else {
                        cars = getCars(request, currentPage);
                    }
                    numberOfRows = getNumberOfRows();
                    break;
            }
        } catch (DBException e) {
            log.error("Cannot get cars", e);
            return "/WEB-INF/error.jsp";
        }
        request.setAttribute("cars", cars);
        int numberOfPages = numberOfRows / 3;
        if (numberOfRows % 3 > 0) {
            numberOfPages++;
        }
        request.setAttribute("noOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);

        return "/WEB-INF/customer/customerbasis.jsp";
    }

    private String checkFilter(HttpServletRequest request) {
        String filter = (String) request.getSession().getAttribute("carFilter");
        if (Optional.ofNullable(request.getParameter("filter")).isPresent()) {
            filter = request.getParameter("id");//qualityClass
            Car.QualityClass qualityClass = Car.QualityClass.valueOf(request.getParameter("qualityClass"));
            request.getSession().setAttribute("carFilter", filter);
            request.getSession().setAttribute("carQualityClass", qualityClass);
        }
        return filter;
    }

    private String checkSorter(HttpServletRequest request) {
        String sorter = (String) request.getSession().getAttribute("carSorter");
        if (Optional.ofNullable(request.getParameter("sorter")).isPresent()) {
            sorter = request.getParameter("id");//price
            request.getSession().setAttribute("carSorter", sorter);
        }
        return sorter;
    }

    private List<Car> getCarsByQualityClassByPrice(HttpServletRequest request, int currentPage, Car.QualityClass qualityClass) throws DBException {
        List<Car> cars = null;
        try {
            cars = carService.getAvailableCarsPaginationByQualityClassByPrice(currentPage, 3, qualityClass);
        } catch (DBException e) {
            log.error("Cannot get available cars pagination by quality class by price", e);
            throw e;
        }
        return cars;
    }

    private List<Car> getCarsByQualityClass(HttpServletRequest request, int currentPage, Car.QualityClass qualityClass) throws DBException {
        List<Car> cars = null;
        try {
            cars = carService.getAvailableCarsPaginationByQualityClass(currentPage, 3, qualityClass);
        } catch (DBException e) {
            log.error("Cannot get available cars pagination by quality class", e);
            throw e;
        }
        return cars;
    }

    private List<Car> getCarsByPrice(HttpServletRequest request, int currentPage) throws DBException {
        List<Car> cars = null;
        try {
            cars = carService.getAvailableCarsPaginationByPrice(currentPage, 3);
        } catch (DBException e) {
            log.error("Cannot get available cars pagination by price", e);
            throw e;
        }
        return cars;
    }

    private List<Car> getCars(HttpServletRequest request, int currentPage) throws DBException {
        List<Car> cars = null;
        try {
            cars = carService.getAvailableCarsPagination(currentPage, 3);
        } catch (DBException e) {
            log.error("Cannot get available cars pagination", e);
            throw e;
        }
        return cars;
    }

    private int getNumberOfRowsByQualityClass(Car.QualityClass qualityClass) throws DBException {
        int numberOfRows = 0;
        try {
            numberOfRows = carService.getNumberOfRowsAvailableByQualityClass(qualityClass);
        } catch (DBException e) {
            log.error("Cannot get number of rows for cars", e);
            throw e;
        }
        return numberOfRows;
    }

    private int getNumberOfRows() throws DBException {
        int numberOfRows = 0;
        try {
            numberOfRows = carService.getNumberOfRowsAvailable();
        } catch (DBException e) {
            log.error("Cannot get number of rows for cars", e);
            throw e;
        }
        return numberOfRows;
    }


}