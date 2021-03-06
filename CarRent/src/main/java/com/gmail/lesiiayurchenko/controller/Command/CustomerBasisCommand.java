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

        List<Car> cars;
        int numberOfRows;
        try {
            switch (filter) {
                case "qualityClass":
                    if (sorter.equals("price")) {
                        cars = getCarsByQualityClassByPrice(currentPage, qualityClass);
                    } else {
                        cars = getCarsByQualityClass(currentPage, qualityClass);
                    }
                    numberOfRows = getNumberOfRowsByQualityClass(qualityClass);
                    break;
                default:
                    if (sorter.equals("price")) {
                        cars = getCarsByPrice(currentPage);
                    } else {
                        cars = getCars(currentPage);
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

        return "/WEB-INF/customer/customerBasis.jsp";
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

    private List<Car> getCarsByQualityClassByPrice(int currentPage, Car.QualityClass qualityClass) throws DBException {
        try {
            Optional<List<Car>> carsOpt = carService.getAvailableCarsPaginationByQualityClassByPrice(currentPage,
                    3, qualityClass);
            List<Car> cars = null;
            if (carsOpt.isPresent()) {
                cars = carsOpt.get();
            }
            return cars;
        } catch (DBException e) {
            log.error("Cannot get available cars pagination by quality class by price", e);
            throw e;
        }
    }

    private List<Car> getCarsByQualityClass(int currentPage, Car.QualityClass qualityClass) throws DBException {
        try {
            Optional<List<Car>> carsOpt = carService.getAvailableCarsPaginationByQualityClass(currentPage,
                    3, qualityClass);
            List<Car> cars = null;
            if (carsOpt.isPresent()) {
                cars = carsOpt.get();
            }
            return cars;
        } catch (DBException e) {
            log.error("Cannot get available cars pagination by quality class", e);
            throw e;
        }
    }

    private List<Car> getCarsByPrice(int currentPage) throws DBException {
        try {
            Optional<List<Car>> carsOpt = carService.getAvailableCarsPaginationByPrice(currentPage, 3);
            List<Car> cars = null;
            if (carsOpt.isPresent()) {
                cars = carsOpt.get();
            }
            return cars;
        } catch (DBException e) {
            log.error("Cannot get available cars pagination by price", e);
            throw e;
        }
    }

    private List<Car> getCars(int currentPage) throws DBException {
        try {
            Optional<List<Car>> carsOpt = carService.getAvailableCarsPagination(currentPage, 3);
            List<Car> cars = null;
            if (carsOpt.isPresent()) {
                cars = carsOpt.get();
            }
            return cars;
        } catch (DBException e) {
            log.error("Cannot get available cars pagination", e);
            throw e;
        }
    }

    private int getNumberOfRowsByQualityClass(Car.QualityClass qualityClass) throws DBException {
        int numberOfRows;
        try {
            numberOfRows = carService.getNumberOfRowsAvailableByQualityClass(qualityClass);
        } catch (DBException e) {
            log.error("Cannot get number of rows for cars", e);
            throw e;
        }
        return numberOfRows;
    }

    private int getNumberOfRows() throws DBException {
        int numberOfRows;
        try {
            numberOfRows = carService.getNumberOfRowsAvailable();
        } catch (DBException e) {
            log.error("Cannot get number of rows for cars", e);
            throw e;
        }
        return numberOfRows;
    }


}