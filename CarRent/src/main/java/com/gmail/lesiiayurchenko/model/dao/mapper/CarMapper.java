package com.gmail.lesiiayurchenko.model.dao.mapper;

import com.gmail.lesiiayurchenko.model.entity.Car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CarMapper implements ObjectMapper<Car> {

    @Override
    public Car extractFromResultSet(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setId(rs.getInt("id_car"));
        car.setModel(rs.getString("model"));
        car.setLicensePlate(rs.getString("license_plate"));
        car.setQualityClass(Car.QualityClass.values()[rs.getInt("quality_class_id")-1]);
        car.setPrice(rs.getBigDecimal("price"));
        car.setAvailable(rs.getInt("available") != 0);
        return car;
    }

    @Override
    public Car makeUnique(Map<Integer, Car> cache,
                              Car car) {
        cache.putIfAbsent(car.getId(), car);
        return cache.get(car.getId());
    }
}
