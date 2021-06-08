package com.gmail.lesiiayurchenko.model.dao;

import com.gmail.lesiiayurchenko.model.entity.Car;

import java.util.List;

public interface CarDao extends GenericDao<Car> {
    List<Car> findAllAvailable() throws DBException;
    List<Car> findAllPagination(int currentPage, int recordsPerPage) throws DBException;
    List<Car> findAllAvailablePagination(int currentPage, int recordsPerPage) throws DBException;
    List<Car> findAllAvailableByQualityClassByPricePagination(int currentPage, int recordsPerPage,
                                                              Car.QualityClass qualityClass) throws DBException;
    List<Car> findAllAvailableByQualityClassPagination(int currentPage, int recordsPerPage,
                                                       Car.QualityClass qualityClass) throws DBException;
    List<Car> findAllAvailableByPricePagination(int currentPage, int recordsPerPage) throws DBException;
    int getNumberOfRowsAll() throws DBException;
    int getNumberOfRowsAvailable() throws DBException;
    int getNumberOfRowsAvailableByQualityClass(Car.QualityClass qualityClass) throws DBException;
}
