package com.gmail.lesiiayurchenko.model.dao.mapper;

import com.gmail.lesiiayurchenko.model.dao.SQLConstants;
import com.gmail.lesiiayurchenko.model.entity.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CarMapper implements ObjectMapper<Car> {

    @Override
    public Car extractFromResultSet(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setId(rs.getInt(SQLConstants.CAR_ID));
        car.setModel(rs.getString(SQLConstants.MODEL));
        car.setLicensePlate(rs.getString(SQLConstants.LICENSE_PLATE));
        car.setQualityClass(Car.QualityClass.values()[rs.getInt(SQLConstants.QUALITY_CLASS_ID)-1]);
        car.setPrice(rs.getBigDecimal(SQLConstants.PRICE));
        car.setAvailable(rs.getInt(SQLConstants.AVAILABLE) != 0);
        return car;
    }

    @Override
    public Car makeUnique(Map<Integer, Car> cache,
                              Car car) {
        cache.putIfAbsent(car.getId(), car);
        return cache.get(car.getId());
    }
}
