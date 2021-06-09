package com.gmail.lesiiayurchenko.model.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Car {
    private int id;
    private String model;
    private String licensePlate;

    public enum QualityClass {
        CITY_CAR, FAMILY_CAR, SPORTS_CAR, LUXURY
    }
    private QualityClass qualityClass;
    private BigDecimal price;
    private boolean available;

    public Car(int id, String model, String licensePlate, QualityClass qualityClass,
               BigDecimal price, boolean available) {
        this.id = id;
        this.model = model;
        this.licensePlate = licensePlate;
        this.qualityClass = qualityClass;
        this.price = price;
        this.available = available;
    }

    public Car() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public QualityClass getQualityClass() {
        return qualityClass;
    }

    public void setQualityClass(QualityClass qualityClass) {
        this.qualityClass = qualityClass;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id && available == car.available && model.equals(car.model) &&
                licensePlate.equals(car.licensePlate) && qualityClass == car.qualityClass && price.equals(car.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, licensePlate, qualityClass, price, available);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", qualityClass=" + qualityClass +
                ", price=" + price +
                ", available=" + available +
                '}';
    }
}
