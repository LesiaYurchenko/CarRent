package com.gmail.lesiiayurchenko.model.dao.mapper;

import com.gmail.lesiiayurchenko.model.dao.SQLConstants;
import com.gmail.lesiiayurchenko.model.entity.Car;
import junit.framework.TestCase;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarMapperTest extends TestCase {
    @Mock
    ResultSet mockRS;

    Car car = new Car(100, "Porshe panamera", "AA4672IB", Car.QualityClass.LUXURY,
            new BigDecimal("50"), true);
    Map<Integer, Car> cache = new HashMap<>();
    CarMapper instance = new CarMapper();

    @Before
    public void setUp() throws SQLException {
        when(mockRS.getInt(SQLConstants.CAR_ID)).thenReturn(100);
        when(mockRS.getString(SQLConstants.MODEL)).thenReturn("Porshe panamera");
        when(mockRS.getString(SQLConstants.LICENSE_PLATE)).thenReturn("AA4672IB");
        when(mockRS.getInt(SQLConstants.QUALITY_CLASS_ID)).thenReturn(4);
        when(mockRS.getBigDecimal(SQLConstants.PRICE)).thenReturn(new BigDecimal("50"));
        when(mockRS.getInt(SQLConstants.AVAILABLE)).thenReturn(1);
        cache.put(car.getId(), car);
    }

    @Test
    public void testExtractFromResultSet() throws SQLException {
        Car actual = instance.extractFromResultSet(mockRS);

        verify(mockRS, times(1)).getInt(SQLConstants.CAR_ID);
        verify(mockRS, times(1)).getString(SQLConstants.MODEL);
        verify(mockRS, times(1)).getString(SQLConstants.LICENSE_PLATE);
        verify(mockRS, times(1)).getInt(SQLConstants.QUALITY_CLASS_ID);
        verify(mockRS, times(1)).getBigDecimal(SQLConstants.PRICE);
        verify(mockRS, times(1)).getInt(SQLConstants.AVAILABLE);

        Assert.assertEquals(car, actual);
    }

    @Test(expected = SQLException.class)
    public void testExtractFromResultSetException() throws SQLException {
        when(mockRS.getInt(SQLConstants.CAR_ID)).thenThrow(new SQLException());
        Car actual = instance.extractFromResultSet(mockRS);

        verify(mockRS, times(1)).getInt(SQLConstants.CAR_ID);
        verify(mockRS, times(1)).getString(SQLConstants.MODEL);
        verify(mockRS, times(1)).getString(SQLConstants.LICENSE_PLATE);
        verify(mockRS, times(1)).getInt(SQLConstants.QUALITY_CLASS_ID);
        verify(mockRS, times(1)).getBigDecimal(SQLConstants.PRICE);
        verify(mockRS, times(1)).getInt(SQLConstants.AVAILABLE);

        Assert.assertEquals(car, actual);
    }

    @Test
    public void testMakeUnique() {
        Car actual = instance.makeUnique(cache, car);
        Assert.assertEquals(car, actual);
        Assert.assertEquals(cache.size(), 1);
    }
}