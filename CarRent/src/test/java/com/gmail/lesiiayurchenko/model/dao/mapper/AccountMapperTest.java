package com.gmail.lesiiayurchenko.model.dao.mapper;

import com.gmail.lesiiayurchenko.model.dao.SQLConstants;
import com.gmail.lesiiayurchenko.model.entity.Account;
import junit.framework.TestCase;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountMapperTest extends TestCase {

    @Mock
    ResultSet mockRS;

    Account customer = new Account (100, "customer", "customer", "customer@gmail.com",
            Account.Role.CUSTOMER, false);
    Map<Integer, Account> cache = new HashMap<>();
    AccountMapper instance = new AccountMapper();

    @Before
    public void setUp() throws SQLException {
        when(mockRS.getInt(SQLConstants.ACCOUNT_ID)).thenReturn(100);
        when(mockRS.getString(SQLConstants.LOGIN)).thenReturn("customer");
        when(mockRS.getString(SQLConstants.PASSWORD)).thenReturn("customer");
        when(mockRS.getString(SQLConstants.EMAIL)).thenReturn("customer@gmail.com");
        when(mockRS.getInt(SQLConstants.ROLE_ID)).thenReturn(3);
        when(mockRS.getInt(SQLConstants.BLOCKED)).thenReturn(0);
        cache.put(customer.getId(), customer);
    }

    @Test
    public void testExtractFromResultSet() throws SQLException {
        Account actual = instance.extractFromResultSet(mockRS);

        verify(mockRS, times(1)).getInt(SQLConstants.ACCOUNT_ID);
        verify(mockRS, times(1)).getString(SQLConstants.LOGIN);
        verify(mockRS, times(1)).getString(SQLConstants.PASSWORD);
        verify(mockRS, times(1)).getString(SQLConstants.EMAIL);
        verify(mockRS, times(1)).getInt(SQLConstants.ROLE_ID);
        verify(mockRS, times(1)).getInt(SQLConstants.BLOCKED);

        Assert.assertEquals(customer, actual);
    }

    @Test(expected = SQLException.class)
    public void testExtractFromResultSetException() throws SQLException {
        when(mockRS.getInt(SQLConstants.ACCOUNT_ID)).thenThrow(new SQLException());
        Account actual = instance.extractFromResultSet(mockRS);

        verify(mockRS, times(1)).getInt(SQLConstants.ACCOUNT_ID);
        verify(mockRS, times(1)).getString(SQLConstants.LOGIN);
        verify(mockRS, times(1)).getString(SQLConstants.PASSWORD);
        verify(mockRS, times(1)).getString(SQLConstants.EMAIL);
        verify(mockRS, times(1)).getInt(SQLConstants.ROLE_ID);
        verify(mockRS, times(1)).getInt(SQLConstants.BLOCKED);

        Assert.assertEquals(customer, actual);
    }

    @Test
    public void testMakeUnique() {
        Account actual = instance.makeUnique(cache, customer);
        Assert.assertEquals(customer, actual);
        Assert.assertEquals(cache.size(),1);
    }
}