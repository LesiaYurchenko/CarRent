package com.gmail.lesiiayurchenko.model.service;

import com.gmail.lesiiayurchenko.model.dao.CarDao;
import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.dao.DaoFactory;
import com.gmail.lesiiayurchenko.model.entity.Car;

import java.math.BigDecimal;
import java.util.List;

public class CarService {

    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Car> getAllCars() throws DBException {
        try (CarDao dao = daoFactory.createCarDao()) {
            return dao.findAll();
        } catch (DBException e){
            throw e;
        }
    }

    public List<Car> getAllAvailableCars() throws DBException {
        try (CarDao dao = daoFactory.createCarDao()) {
            return dao.findAllAvailable();
        } catch (DBException e){
            throw e;
        }
    }

    public Car getCarById(int id) throws DBException {
        try (CarDao dao = daoFactory.createCarDao()) {
            return dao.findById(id);
        } catch (DBException e){
            throw e;
        }
    }

    public void addCar(String model, String licensePlate, Car.QualityClass qualityClass, BigDecimal price) throws DBException {
        Car car = new Car();
        car.setModel(model);
        car.setLicensePlate(licensePlate);
        car.setQualityClass(qualityClass);
        car.setPrice(price);
        car.setAvailable(true);
        try (CarDao dao = daoFactory.createCarDao()) {
            dao.create(car);
        } catch (DBException e){
            throw e;
        }
    }

    public void makeCarUnavailable(Car car) throws DBException {
        car.setAvailable(false);
        try (CarDao dao = daoFactory.createCarDao()) {
            dao.update(car);
        } catch (DBException e){
            throw e;
        }
    }

    public void updateCar(Car car) throws DBException {
        try (CarDao dao = daoFactory.createCarDao()) {
            dao.update(car);
        } catch (DBException e){
            throw e;
        }
    }

    public List<Car> getAvailableCarsPagination(int currentPage, int recordsPerPage) throws DBException {
        try (CarDao dao = daoFactory.createCarDao()) {
            return dao.findAllAvailablePagination(currentPage, recordsPerPage);
        } catch (DBException e){
            throw e;
        }
    }

    public List<Car> getAvailableCarsPaginationByPrice(int currentPage, int recordsPerPage) throws DBException {
        try (CarDao dao = daoFactory.createCarDao()) {
            return dao.findAllAvailableByPricePagination(currentPage, recordsPerPage);
        } catch (DBException e){
            throw e;
        }
    }

    public List<Car> getAvailableCarsPaginationByQualityClassByPrice(int currentPage, int recordsPerPage,
                                                                     Car.QualityClass qualityClass) throws DBException {
        try (CarDao dao = daoFactory.createCarDao()) {
            return dao.findAllAvailableByQualityClassByPricePagination(currentPage, recordsPerPage, qualityClass);
        } catch (DBException e){
            throw e;
        }
    }

    public List<Car> getAvailableCarsPaginationByQualityClass(int currentPage, int recordsPerPage,
                                                                     Car.QualityClass qualityClass) throws DBException {
        try (CarDao dao = daoFactory.createCarDao()) {
            return dao.findAllAvailableByQualityClassPagination(currentPage, recordsPerPage, qualityClass);
        } catch (DBException e){
            throw e;
        }
    }

    public int getNumberOfRowsAvailable() throws DBException {
        try (CarDao dao = daoFactory.createCarDao()) {
            return dao.getNumberOfRowsAvailable();
        } catch (DBException e){
            throw e;
        }
    }

    public int getNumberOfRowsAvailableByQualityClass(Car.QualityClass qualityClass) throws DBException {
        try (CarDao dao = daoFactory.createCarDao()) {
            return dao.getNumberOfRowsAvailableByQualityClass(qualityClass);
        } catch (DBException e){
            throw e;
        }
    }

    public List<Car> getAllCarsPagination(int currentPage, int recordsPerPage) throws DBException {
        try (CarDao dao = daoFactory.createCarDao()) {
            return dao.findAllPagination(currentPage, recordsPerPage);
        } catch (DBException e){
            throw e;
        }
    }

    public int getNumberOfRowsAll() throws DBException {
        try (CarDao dao = daoFactory.createCarDao()) {
            return dao.getNumberOfRowsAll();
        } catch (DBException e){
            throw e;
        }
    }
}
