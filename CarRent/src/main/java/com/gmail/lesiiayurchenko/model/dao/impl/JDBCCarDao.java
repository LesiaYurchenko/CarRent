package com.gmail.lesiiayurchenko.model.dao.impl;

import com.gmail.lesiiayurchenko.model.dao.CarDao;
import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.dao.SQLConstants;
import com.gmail.lesiiayurchenko.model.dao.mapper.CarMapper;
import com.gmail.lesiiayurchenko.model.entity.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCCarDao implements CarDao {
    private Connection connection;


    public JDBCCarDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Car entity) throws DBException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQLConstants.CREATE_CAR);
            int k = 1;
            pstmt.setInt(k++, entity.getId());
            pstmt.setString(k++, entity.getModel());
            pstmt.setString(k++, entity.getLicensePlate());
            pstmt.setInt(k++, entity.getQualityClass().ordinal()+1);
            pstmt.setBigDecimal(k++, entity.getPrice());
            pstmt.setInt(k, entity.isAvailable()?1:0);
            pstmt.executeUpdate();
        } catch (SQLException e){
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(pstmt);
        }
    }

    @Override
    public Car findById(int id) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = SQLConstants.FIND_CAR_BY_ID;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            CarMapper carMapper = new CarMapper();
            Car car = null;
            while(rs.next()) {
                car = carMapper.extractFromResultSet(rs);
            }
            return car;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Car> findAll() throws DBException {
        List< Car> cars = new ArrayList<>();

        String query = SQLConstants.FIND_ALL_CARS;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            CarMapper carMapper = new CarMapper();
            while (rs.next()) {
                Car car = carMapper.extractFromResultSet(rs);
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        }
    }

    @Override
    public List<Car> findAllAvailable() throws DBException {
        List< Car> cars = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = SQLConstants.FIND_ALL_AVAILABLE_CARS;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();

            CarMapper carMapper = new CarMapper();
            while (rs.next()) {
                Car car = carMapper.extractFromResultSet(rs);
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Car> findAllPagination(int currentPage, int recordsPerPage)throws DBException  {
        List< Car> cars = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = SQLConstants.FIND_ALL_CARS_PAGINATION;
        try {
            pstmt = connection.prepareStatement(query);
            int k =1;
            pstmt.setInt(k++, (currentPage-1) * recordsPerPage);
            pstmt.setInt(k, recordsPerPage);
            rs = pstmt.executeQuery();

            CarMapper carMapper = new CarMapper();
            while (rs.next()) {
                Car car = carMapper.extractFromResultSet(rs);
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Car> findAllAvailablePagination(int currentPage, int recordsPerPage) throws DBException {
        List< Car> cars = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = SQLConstants.FIND_ALL_AVAILABLE_CARS_PAGINATION;
        try {
            pstmt = connection.prepareStatement(query);
            int k =1;
            pstmt.setBoolean(k++, true);
            pstmt.setInt(k++, (currentPage-1) * recordsPerPage);
            pstmt.setInt(k, recordsPerPage);
            rs = pstmt.executeQuery();

            CarMapper carMapper = new CarMapper();
            while (rs.next()) {
                Car car = carMapper.extractFromResultSet(rs);
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Car> findAllAvailableByQualityClassByPricePagination(int currentPage, int recordsPerPage,
                                                                     Car.QualityClass qualityClass) throws DBException {
        List< Car> cars = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = SQLConstants.FIND_ALL_AVAILABLE_CARS_BY_QUALITY_BY_PRICE_PAGINATION;
        try {
            pstmt = connection.prepareStatement(query);
            int k =1;
            pstmt.setBoolean(k++, true);
            pstmt.setInt(k++, qualityClass.ordinal()+1);
            pstmt.setInt(k++, (currentPage-1) * recordsPerPage);
            pstmt.setInt(k, recordsPerPage);
            rs = pstmt.executeQuery();

            CarMapper carMapper = new CarMapper();
            while (rs.next()) {
                Car car = carMapper.extractFromResultSet(rs);
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Car> findAllAvailableByQualityClassPagination(int currentPage, int recordsPerPage,
                                                                     Car.QualityClass qualityClass) throws DBException {
        List< Car> cars = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = SQLConstants.FIND_ALL_AVAILABLE_CARS_BY_QUALITY_PAGINATION;
        try {
            pstmt = connection.prepareStatement(query);
            int k =1;
            pstmt.setBoolean(k++, true);
            pstmt.setInt(k++, qualityClass.ordinal()+1);
            pstmt.setInt(k++, (currentPage-1) * recordsPerPage);
            pstmt.setInt(k, recordsPerPage);
            rs = pstmt.executeQuery();

            CarMapper carMapper = new CarMapper();
            while (rs.next()) {
                Car car = carMapper.extractFromResultSet(rs);
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Car> findAllAvailableByPricePagination(int currentPage, int recordsPerPage)throws DBException  {
        List< Car> cars = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = SQLConstants.FIND_ALL_AVAILABLE_CARS_BY_PRICE_PAGINATION;
        try {
            pstmt = connection.prepareStatement(query);
            int k =1;
            pstmt.setBoolean(k++, true);
            pstmt.setInt(k++, (currentPage-1) * recordsPerPage);
            pstmt.setInt(k, recordsPerPage);
            rs = pstmt.executeQuery();

            CarMapper carMapper = new CarMapper();
            while (rs.next()) {
                Car car = carMapper.extractFromResultSet(rs);
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public int getNumberOfRowsAll() throws DBException {
        String query = SQLConstants.GET_NUMBER_OF_ROWS_ALL_CARS;
        int numberOfRows = 0;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                numberOfRows = rs.getInt(SQLConstants.NUMBER);
                            }
            return numberOfRows;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        }
    }

    @Override
    public int getNumberOfRowsAvailable() throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int numberOfRows = 0;
        String query = SQLConstants.GET_NUMBER_OF_ROWS_AVAILABLE_CARS;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                numberOfRows = rs.getInt(SQLConstants.NUMBER);
            }
            return numberOfRows;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public int getNumberOfRowsAvailableByQualityClass(Car.QualityClass qualityClass) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int numberOfRows = 0;
        String query = SQLConstants.GET_NUMBER_OF_ROWS_AVAILABLE_CARS_BY_QUALITY;
        try {
            pstmt = connection.prepareStatement(query);
            int k =1;
            pstmt.setBoolean(k++, true);
            pstmt.setInt(k, qualityClass.ordinal()+1);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                numberOfRows = rs.getInt(SQLConstants.NUMBER);
            }
            return numberOfRows;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public void update(Car entity) throws DBException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQLConstants.UPDATE_CAR);

            int k = 1;
            pstmt.setString(k++, entity.getModel());
            pstmt.setString(k++, entity.getLicensePlate());
            pstmt.setInt(k++, entity.getQualityClass().ordinal()+1);
            pstmt.setBigDecimal(k++, entity.getPrice());
            pstmt.setInt(k++, entity.isAvailable()?1:0);
            pstmt.setInt(k, entity.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(pstmt);
        }
    }

    @Override
    public void delete(int id) throws DBException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQLConstants.DELETE_CAR);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(pstmt);
        }
    }

    @Override
    public void close()  {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
