package com.gmail.lesiiayurchenko.model.entity;

import java.util.List;
import java.util.Objects;

public class Booking {
    private int id;
    private Account account;
    private String passport;
    private List<Car> cars;
    private int leaseTerm;
    private boolean driver;
    public enum Status {
        NEW, APPROVED, REJECTED, PAID, RETURNED
    }
    private Status status;
    private boolean damage;
    private boolean damagePaid;

    public Booking(int id, Account account, String passport, List<Car> cars, int leaseTerm, boolean driver,
                    Status status, boolean damage, boolean damagePaid) {
        this.id = id;
        this.account = account;
        this.passport = passport;
        this.cars = cars;
        this.leaseTerm = leaseTerm;
        this.driver = driver;
        this.status = status;
        this.damage = damage;
        this.damagePaid = damagePaid;
    }

    public Booking() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public int getLeaseTerm() {
        return leaseTerm;
    }

    public void setLeaseTerm(int leaseTerm) {
        this.leaseTerm = leaseTerm;
    }

    public boolean isDriver() {
        return driver;
    }

    public void setDriver(boolean driver) {
        this.driver = driver;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isDamage() {
        return damage;
    }

    public void setDamage(boolean damage) {
        this.damage = damage;
    }

    public boolean isDamagePaid() {
        return damagePaid;
    }

    public void setDamagePaid(boolean damagePaid) {
        this.damagePaid = damagePaid;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id == booking.id && leaseTerm == booking.leaseTerm && driver == booking.driver
                && damage == booking.damage && damagePaid == booking.damagePaid && account.equals(booking.account)
                && passport.equals(booking.passport) && cars.equals(booking.cars) && status == booking.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, passport, cars, leaseTerm, driver, status, damage, damagePaid);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", account=" + account +
                ", passport='" + passport + '\'' +
                ", cars=" + cars +
                ", leaseTerm=" + leaseTerm +
                ", driver=" + driver +
                ", status=" + status +
                ", damage=" + damage +
                ", damagePaid=" + damagePaid +
                '}';
    }
}
