package com.gmail.lesiiayurchenko.model.service;

import com.gmail.lesiiayurchenko.model.dao.CarDao;
import com.gmail.lesiiayurchenko.model.entity.Car;
import junit.framework.TestCase;
import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.dao.DaoFactory;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest extends TestCase {
    @Mock
    DaoFactory mockDaoFactory;
    @Mock
    CarDao mockCarDao;
    @InjectMocks
    CarService instance;

    Car car = new Car(100, "Porshe panamera", "AA4672IB", Car.QualityClass.LUXURY,
            new BigDecimal("50"), true);
    List<Car> cars = new ArrayList<>();
    int numberOfRows = 1;

    @Before
    public void setUp() throws DBException {
        cars.add(car);

        when(mockDaoFactory.createCarDao()).thenReturn(mockCarDao);
        when(mockCarDao.findAll()).thenReturn(cars);
        when(mockCarDao.findAllAvailable()).thenReturn(cars);
        when(mockCarDao.findById(100)).thenReturn(car);
        doNothing().when(mockCarDao).create(any(Car.class));
        doNothing().when(mockCarDao).update(any(Car.class));
        when(mockCarDao.findAllAvailablePagination(anyInt(), anyInt())).thenReturn(cars);
        when(mockCarDao.findAllAvailableByPricePagination(anyInt(), anyInt())).thenReturn(cars);
        when(mockCarDao.findAllAvailableByQualityClassByPricePagination(anyInt(), anyInt(), any(Car.QualityClass.class)))
                .thenReturn(cars);
        when(mockCarDao.findAllAvailableByQualityClassPagination(anyInt(), anyInt(), any(Car.QualityClass.class)))
                .thenReturn(cars);
        when(mockCarDao.findAllPagination(anyInt(), anyInt())).thenReturn(cars);
        when(mockCarDao.getNumberOfRowsAvailable()).thenReturn(numberOfRows);
        when(mockCarDao.getNumberOfRowsAvailableByQualityClass(any(Car.QualityClass.class))).thenReturn(numberOfRows);
        when(mockCarDao.getNumberOfRowsAll()).thenReturn(numberOfRows);

        instance = new CarService(mockDaoFactory);
    }

    public CarServiceTest() {
    }

    @Test
    public void testGetAllCars() throws DBException {
        instance.getAllCars();

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findAll();
    }

    @Test(expected = DBException.class)
    public void testGetAllCarsException() throws DBException {
        when(mockCarDao.findAll()).thenThrow(new DBException());

        instance.getAllCars();

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findAll();
        }

    @Test
    public void testGetAllAvailableCars() throws DBException {
        instance.getAllAvailableCars();

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findAllAvailable();
    }

    @Test(expected = DBException.class)
    public void testGetAllAvailableCarsException() throws DBException {
        when(mockCarDao.findAllAvailable()).thenThrow(new DBException());
        instance.getAllAvailableCars();

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findAllAvailable();
    }

    @Test
    public void testGetCarById() throws DBException {
        instance.getCarById(100);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findById(100);
    }

    @Test(expected = DBException.class)
    public void testGetCarByIdException() throws DBException {
        when(mockCarDao.findById(anyInt())).thenThrow(new DBException());
        instance.getCarById(100);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findById(100);
    }

    @Test
    public void testAddCar() throws DBException {
        Car actual = car;
        Optional<Car> c = instance.addCar("Tesla X", "AA3452HI",
                Car.QualityClass.LUXURY, new BigDecimal("45"));

        if (c.isPresent()){
            actual = c.get();
        }

        Car predicted = new Car (0, "Tesla X", "AA3452HI",
                Car.QualityClass.LUXURY, new BigDecimal("45"), true);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).create(actual);
        Assert.assertEquals(actual, predicted);
    }

    @Test(expected = DBException.class)
    public void testAddCarException() throws DBException {
        doThrow(new DBException()).when(mockCarDao).create(any(Car.class));
        Car actual = car;
        Optional<Car> c = instance.addCar("Tesla X", "AA3452HI",
                Car.QualityClass.LUXURY, new BigDecimal("45"));

        if (c.isPresent()){
            actual = c.get();
        }

        Car predicted = new Car (0, "Tesla X", "AA3452HI",
                Car.QualityClass.LUXURY, new BigDecimal("45"), true);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).create(actual);
        Assert.assertNotEquals(actual, predicted);
    }

    @Test
    public void testMakeCarUnavailable() throws DBException {
        Car actual = car;
        Optional<Car> c = instance.makeCarUnavailable(car);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).update(actual);
        Assert.assertFalse(actual.isAvailable());
    }

    @Test(expected = DBException.class)
    public void testMakeCarUnavailableException() throws DBException {
        doThrow(new DBException()).when(mockCarDao).update(any(Car.class));
        Car actual = car;
        Optional<Car> c = instance.makeCarUnavailable(car);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).update(actual);
        Assert.assertTrue(actual.isAvailable());
    }

    @Test
    public void testUpdateCar() throws DBException {
        instance.updateCar(car);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).update(car);
    }

    @Test(expected = DBException.class)
    public void testUpdateCarException() throws DBException {
        doThrow(new DBException()).when(mockCarDao).update(any(Car.class));
        instance.updateCar(car);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).update(car);
    }

    @Test
    public void testGetAvailableCarsPagination() throws DBException {
        instance.getAvailableCarsPagination(1, 3);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findAllAvailablePagination(1,3);
    }

    @Test(expected = DBException.class)
    public void testGetAvailableCarsPaginationException() throws DBException {
        when(mockCarDao.findAllAvailablePagination(anyInt(), anyInt())).thenThrow(new DBException());
        instance.getAvailableCarsPagination(1, 3);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findAllAvailablePagination(1,3);
    }

    @Test
    public void testGetAvailableCarsPaginationByPrice() throws DBException {
        instance.getAvailableCarsPaginationByPrice(1, 3);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findAllAvailableByPricePagination(1,3);
    }

    @Test(expected = DBException.class)
    public void testGetAvailableCarsPaginationByPriceException() throws DBException {
        when(mockCarDao.findAllAvailableByPricePagination(anyInt(), anyInt())).thenThrow(new DBException());
        instance.getAvailableCarsPaginationByPrice(1, 3);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findAllAvailableByPricePagination(1,3);
    }

    @Test
    public void testGetAvailableCarsPaginationByQualityClassByPrice() throws DBException {
        instance.getAvailableCarsPaginationByQualityClassByPrice(1, 3, Car.QualityClass.LUXURY);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findAllAvailableByQualityClassByPricePagination(1,
                3, Car.QualityClass.LUXURY);
    }

    @Test(expected = DBException.class)
    public void testGetAvailableCarsPaginationByQualityClassByPriceException() throws DBException {
        when(mockCarDao.findAllAvailableByQualityClassByPricePagination(anyInt(), anyInt(),
                any(Car.QualityClass.class))).thenThrow(new DBException());
        instance.getAvailableCarsPaginationByQualityClassByPrice(1, 3, Car.QualityClass.LUXURY);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findAllAvailableByQualityClassByPricePagination(1,
                3, Car.QualityClass.LUXURY);
    }

    @Test
    public void testGetAvailableCarsPaginationByQualityClass() throws DBException {
        instance.getAvailableCarsPaginationByQualityClass(1, 3, Car.QualityClass.LUXURY);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findAllAvailableByQualityClassPagination(1,
                3, Car.QualityClass.LUXURY);
    }

    @Test(expected = DBException.class)
    public void testGetAvailableCarsPaginationByQualityClassException() throws DBException {
        when(mockCarDao.findAllAvailableByQualityClassPagination(anyInt(), anyInt(),
                any(Car.QualityClass.class))).thenThrow(new DBException());
        instance.getAvailableCarsPaginationByQualityClass(1, 3, Car.QualityClass.LUXURY);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findAllAvailableByQualityClassPagination(1,
                3, Car.QualityClass.LUXURY);
    }

    @Test
    public void testGetNumberOfRowsAvailable() throws DBException {
        instance.getNumberOfRowsAvailable();

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).getNumberOfRowsAvailable();
    }

    @Test(expected = DBException.class)
    public void testGetNumberOfRowsAvailableException() throws DBException {
        when(mockCarDao.getNumberOfRowsAvailable()).thenThrow(new DBException());
        instance.getNumberOfRowsAvailable();

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).getNumberOfRowsAvailable();
    }

    @Test
    public void testGetNumberOfRowsAvailableByQualityClass() throws DBException {
        instance.getNumberOfRowsAvailableByQualityClass(Car.QualityClass.LUXURY);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1))
                .getNumberOfRowsAvailableByQualityClass(Car.QualityClass.LUXURY);
    }

    @Test(expected = DBException.class)
    public void testGetNumberOfRowsAvailableByQualityClassException() throws DBException {
        when(mockCarDao.getNumberOfRowsAvailableByQualityClass(any(Car.QualityClass.class)))
                .thenThrow(new DBException());
        instance.getNumberOfRowsAvailableByQualityClass(Car.QualityClass.LUXURY);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1))
                .getNumberOfRowsAvailableByQualityClass(Car.QualityClass.LUXURY);
    }

    @Test
    public void testGetAllCarsPagination() throws DBException {
        instance.getAllCarsPagination(1, 3);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findAllPagination(1,3);
    }

    @Test(expected = DBException.class)
    public void testGetAllCarsPaginationException() throws DBException {
        when(mockCarDao.findAllPagination(anyInt(), anyInt())).thenThrow(new DBException());
        instance.getAllCarsPagination(1, 3);

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).findAllPagination(1,3);
    }

    @Test
    public void testGetNumberOfRowsAll() throws DBException {
        instance.getNumberOfRowsAll();

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).getNumberOfRowsAll();
    }

    @Test(expected = DBException.class)
    public void testGetNumberOfRowsAllException() throws DBException {
        when(mockCarDao.getNumberOfRowsAll()).thenThrow(new DBException());
        instance.getNumberOfRowsAll();

        verify(mockDaoFactory, times(1)).createCarDao();
        verify(mockCarDao, times(1)).getNumberOfRowsAll();
    }

}