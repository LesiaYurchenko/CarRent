package com.gmail.lesiiayurchenko.model.dao;

public class SQLConstants {

    public static final String CAR_ID = "id_car";
    public static final String MODEL = "model";
    public static final String LICENSE_PLATE = "license_plate";
    public static final String QUALITY_CLASS_ID = "quality_class_id";
    public static final String PRICE = "price";
    public static final String AVAILABLE ="available";
    public static final String BOOKING_ID ="id_booking";
    public static final String PASSPORT = "passport";
    public static final String LEASE_TERM ="lease_term";
    public static final String DRIVER ="driver";
    public static final String STATUS_ID ="status_id";
    public static final String DAMAGE ="damage";
    public static final String DAMAGE_PAID ="damage_paid";
    public static final String ACCOUNT_ID ="id_account";
    public static final String LOGIN ="login";
    public static final String PASSWORD ="password";
    public static final String EMAIL ="email";
    public static final String ROLE_ID ="role_id";
    public static final String BLOCKED ="blocked";
    public static final String ID = "id";
    public static final String NUMBER = "number";




    public static final String CREATE_CAR = "insert into car (id, model, license_plate, quality_class_id, " +
            "price, available) values (?,?,?,?,?,?)";
    public static final String FIND_CAR_BY_ID = "select id as id_car, model, license_plate, quality_class_id, price," +
            " available from car where id = ?";
    public static final String FIND_ALL_CARS = "select id as id_car, model, license_plate, quality_class_id, price," +
            " available from car";
    public static final String FIND_ALL_AVAILABLE_CARS = "select id as id_car, model, license_plate, " +
            "quality_class_id, price, available from car where available = ?";
    public static final String FIND_ALL_CARS_PAGINATION = "select id as id_car, model, license_plate, " +
            "quality_class_id, price, available from car LIMIT ?, ?";
    public static final String FIND_ALL_AVAILABLE_CARS_PAGINATION = "select id as id_car, model, license_plate, " +
            "quality_class_id, price, available from car where available = ? LIMIT ?, ?";
    public static final String FIND_ALL_AVAILABLE_CARS_BY_QUALITY_BY_PRICE_PAGINATION = "select id as id_car, model, " +
            "license_plate, quality_class_id, price, available " +
            "from car where available = ? and quality_class_id = ? order by price LIMIT ?, ?";
    public static final String FIND_ALL_AVAILABLE_CARS_BY_QUALITY_PAGINATION = "select id as id_car, model, " +
            "license_plate, quality_class_id, price, available " +
            "from car where available = ? and quality_class_id = ? LIMIT ?, ?";
    public static final String FIND_ALL_AVAILABLE_CARS_BY_PRICE_PAGINATION = "select id as id_car, model, " +
            "license_plate, quality_class_id, price, available " +
            "from car where available = ? order by price LIMIT ?, ?";
    public static final String GET_NUMBER_OF_ROWS_ALL_CARS = "select count(id) as number from car";
    public static final String GET_NUMBER_OF_ROWS_AVAILABLE_CARS = "select count(id) as number from car where " +
            "available = ?";
    public static final String GET_NUMBER_OF_ROWS_AVAILABLE_CARS_BY_QUALITY = "select count(id) as number from car " +
            "where available = ? and quality_class_id = ?";
    public static final String UPDATE_CAR = "UPDATE car SET model = ?, license_plate = ?, " +
            "quality_class_id = ?, price = ?, available = ? WHERE id = ?";
    public static final String DELETE_CAR = "delete from car where id = ?";
    public static final String ADD_BOOKING = "insert into booking (id, account_id, passport, lease_term, " +
            "driver, status_id, damage, damage_paid) values (?,?,?,?,?,?,?,?)";
    public static final String GET_LAST_INSERT_BOOKING_ID = "SELECT LAST_INSERT_ID() as id";
    public static final String ADD_BOOKING_HAS_CAR = "insert into booking_has_car (booking_id, car_id) values (?,?)";
    public static final String FIND_BOOKING_BY_ID = " select b.id as id_booking, b.passport, b.lease_term, b.driver," +
            " b.status_id, b.damage, b.damage_paid, " +
            " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
            " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
            " from booking b" +
            " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
            " left join car c ON (booking_has_car.car_id = c.id)" +
            " left join account a ON (b.account_id = a.id)" +
            "where b.id = ?";
    public static final String FIND_ALL_BOOKINGS = " select b.id as id_booking, b.passport, b.lease_term, b.driver," +
            " b.status_id, b.damage, b.damage_paid, " +
            " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
            " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
            " from booking b" +
            " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
            " left join car c ON (booking_has_car.car_id = c.id)" +
            " left join account a ON (b.account_id = a.id)";
    public static final String FIND_ALL_NEW_BOOKINGS = " select b.id as id_booking, b.passport, b.lease_term," +
            " b.driver, b.status_id, b.damage, b.damage_paid, " +
            " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
            " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
            " from booking b" +
            " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
            " left join car c ON (booking_has_car.car_id = c.id)" +
            " left join account a ON (b.account_id = a.id)" +
            "where b.status_id = ?";
    public static final String FIND_ALL_APPROVED_BOOKINGS = " select b.id as id_booking, b.passport, b.lease_term," +
            " b.driver, b.status_id, b.damage, b.damage_paid, " +
            " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
            " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
            " from booking b" +
            " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
            " left join car c ON (booking_has_car.car_id = c.id)" +
            " left join account a ON (b.account_id = a.id)" +
            "where b.status_id = ?";
    public static final String FIND_ALL_REJECTED_BOOKINGS = " select b.id as id_booking, b.passport, b.lease_term," +
            " b.driver, b.status_id, b.damage, b.damage_paid, " +
            " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
            " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
            " from booking b" +
            " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
            " left join car c ON (booking_has_car.car_id = c.id)" +
            " left join account a ON (b.account_id = a.id)" +
            "where b.status_id = ?";
    public static final String FIND_ALL_PAID_BOOKINGS = " select b.id as id_booking, b.passport, b.lease_term," +
            " b.driver, b.status_id, b.damage, b.damage_paid, " +
            " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
            " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
            " from booking b" +
            " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
            " left join car c ON (booking_has_car.car_id = c.id)" +
            " left join account a ON (b.account_id = a.id)" +
            "where b.status_id = ?";
    public static final String FIND_ALL_RETURNED_BOOKINGS = " select b.id as id_booking, b.passport, b.lease_term," +
            " b.driver, b.status_id, b.damage, b.damage_paid, " +
            " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
            " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
            " from booking b" +
            " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
            " left join car c ON (booking_has_car.car_id = c.id)" +
            " left join account a ON (b.account_id = a.id)" +
            "where b.status_id = ?";
    public static final String FIND_ALL_BOOKINGS_BY_CUSTOMER = " select b.id as id_booking, b.passport," +
            " b.lease_term, b.driver, b.status_id, b.damage, b.damage_paid, " +
            " c.id as id_car, c.model, c.license_plate, c.quality_class_id, c.price, c.available," +
            " a.id as id_account, a.login, a.password, a.email, a.role_id, a.blocked" +
            " from booking b" +
            " left join booking_has_car ON (b.id = booking_has_car.booking_id)" +
            " left join car c ON (booking_has_car.car_id = c.id)" +
            " left join account a ON (b.account_id = a.id)" +
            "where a.id = ?";
    public static final String UPDATE_BOOKING = "UPDATE booking SET account_id = ?, passport = ?, " +
            "lease_term = ?, driver = ?, status_id = ?, damage = ?, damage_paid = ? WHERE id = ?";
    public static final String DELETE_BOOKING_HAS_CAR = "delete from booking_has_car where booking_id = ?";
    public static final String DELETE_BOOKING = "delete from booking where id = ?";
    public static final String CREATE_ACCOUNT = "insert into account (id, login, password, email, " +
            "role_id, blocked) values (?,?,?,?,?,?)";
    public static final String FIND_ACCOUNT_BY_ID = "select id as id_account, login, password, email, role_id," +
            " blocked from account where id = ?";
    public static final String FIND_ACCOUNT_BY_LOGIN= "select id as id_account, login, password, email, role_id, " +
            "blocked from account where login = ?";
    public static final String FIND_ALL_ACCOUNTS = "select id as id_account, login, password, email, role_id, " +
            "blocked from account";
    public static final String FIND_ALL_MANAGERS = "select id as id_account, login, password, email, role_id," +
            " blocked from account where role_id = ? LIMIT ?, ?";
    public static final String FIND_ALL_CUSTOMERS = "select id as id_account, login, password, email, role_id, " +
            "blocked from account where role_id = ? LIMIT ?, ?";
    public static final String GET_NUMBER_OF_ROWS_CUSTOMERS = "select count(id) as number from account " +
            "where role_id = ?";
    public static final String GET_NUMBER_OF_ROWS_MANAGERS = "select count(id) as number from account " +
            "where role_id = ?";
    public static final String UPDATE_ACCOUNT = "UPDATE account SET login = ?, password = ?, " +
            "email = ?, role_id = ?, blocked = ? WHERE id = ?";
    public static final String DELETE_ACCOUNT = "delete from account where id = ?";

}
