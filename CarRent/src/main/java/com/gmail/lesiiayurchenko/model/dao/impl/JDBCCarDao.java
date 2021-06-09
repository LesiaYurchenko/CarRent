package com.gmail.lesiiayurchenko.model.dao.impl;

import com.gmail.lesiiayurchenko.model.dao.CarDao;
import com.gmail.lesiiayurchenko.model.dao.DBException;
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
            pstmt = connection.prepareStatement("insert into car (id, model, license_plate, quality_class_id, " +
                    "price, available) values (?,?,?,?,?,?)");
            int k = 1;
            pstmt.setInt(k++, entity.getId());
            pstmt.setString(k++, entity.getModel());
            pstmt.setString(k++, entity.getLicensePlate());
            pstmt.setInt(k++, entity.getQualityClass().ordinal()+1);
            pstmt.setBigDecimal(k++, entity.getPrice());
            pstmt.setInt(k++, entity.isAvailable()?1:0);
            pstmt.executeUpdate();
        } catch (SQLException e){
            throw new DBException("DB exception", e);
        } finally {
            close(pstmt);
        }
    }

    @Override
    public Car findById(int id) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "select id as id_car, model, license_plate, quality_class_id, price, available " +
                "from car where id = ?";
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
            throw new DBException("DB exception", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Car> findAll() throws DBException {
        List< Car> cars = new ArrayList<>();

        String query = "select id as id_car, model, license_plate, quality_class_id, price, available from car";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            CarMapper carMapper = new CarMapper();
            while (rs.next()) {
                Car car = carMapper.extractFromResultSet(rs);
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            throw new DBException("DB exception", e);
        }
    }

    @Override
    public List<Car> findAllAvailable() throws DBException {
        List< Car> cars = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "select id as id_car, model, license_plate, quality_class_id, price, available " +
                "from car where available = ?";
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
            throw new DBException("DB exception", e);
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
        String query = "select id as id_car, model, license_plate, quality_class_id, price, available " +
                "from car LIMIT ?, ?";
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
            throw new DBException("DB exception", e);
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
        String query = "select id as id_car, model, license_plate, quality_class_id, price, available " +
                "from car where available = ? LIMIT ?, ?";
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
            throw new DBException("DB exception", e);
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
        String query = "select id as id_car, model, license_plate, quality_class_id, price, available " +
                "from car where available = ? and quality_class_id = ? order by price LIMIT ?, ?";
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
            throw new DBException("DB exception", e);
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
        String query = "select id as id_car, model, license_plate, quality_class_id, price, available " +
                "from car where available = ? and quality_class_id = ? LIMIT ?, ?";
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
            throw new DBException("DB exception", e);
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
        String query = "select id as id_car, model, license_plate, quality_class_id, price, available " +
                "from car where available = ? order by price LIMIT ?, ?";
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
            throw new DBException("DB exception", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public int getNumberOfRowsAll() throws DBException {
        String query = "select count(id) as number from car";
        int numberOfRows = 0;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                numberOfRows = rs.getInt("number");
                            }
            return numberOfRows;
        } catch (SQLException e) {
            throw new DBException("DB exception", e);
        }
    }

    @Override
    public int getNumberOfRowsAvailable() throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int numberOfRows = 0;
        String query = "select count(id) as number from car where available = ?";
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                numberOfRows = rs.getInt("number");
            }
            return numberOfRows;
        } catch (SQLException e) {
            throw new DBException("DB exception", e);
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
        String query = "select count(id) as number from car where available = ? and quality_class_id = ?";
        try {
            pstmt = connection.prepareStatement(query);
            int k =1;
            pstmt.setBoolean(k++, true);
            pstmt.setInt(k++, qualityClass.ordinal()+1);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                numberOfRows = rs.getInt("number");
            }
            return numberOfRows;
        } catch (SQLException e) {
            throw new DBException("DB exception", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public void update(Car entity) throws DBException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("UPDATE car SET model = ?, license_plate = ?, " +
                    "quality_class_id = ?, price = ?, available = ?" +
                    "	WHERE id = ?");

            int k = 1;
            pstmt.setString(k++, entity.getModel());
            pstmt.setString(k++, entity.getLicensePlate());
            pstmt.setInt(k++, entity.getQualityClass().ordinal()+1);
            pstmt.setBigDecimal(k++, entity.getPrice());
            pstmt.setInt(k++, entity.isAvailable()?1:0);
            pstmt.setInt(k, entity.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("DB exception", e);
        } finally {
            close(pstmt);
        }
    }

    @Override
    public void delete(int id) throws DBException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("delete from car where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("DB exception", e);
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
