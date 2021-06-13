package com.gmail.lesiiayurchenko.model.service;

import com.gmail.lesiiayurchenko.model.dao.AccountDao;
import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.dao.DaoFactory;
import com.gmail.lesiiayurchenko.model.entity.Account;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @Mock
    DaoFactory mockDaoFactory;
    @Mock
    AccountDao mockAccountDao;

    @InjectMocks
    AccountService instance;

    Account customer = new Account (100, "customer", "customer", "customer@gmail.com",
            Account.Role.CUSTOMER, false);
    Account manager = new Account (101, "manager", "manager", "manager@gmail.com",
            Account.Role.MANAGER, false);
    List<Account> customers = new ArrayList<>();
    List<Account> managers = new ArrayList<>();
    int numberOfRows = 1;

    public AccountServiceTest() {
    }


    @Before
    public void setUp() throws DBException {
        customers.add(customer);
        managers.add(manager);
        when(mockDaoFactory.createAccountDao()).thenReturn(mockAccountDao);
        when(mockAccountDao.findAllCustomers(anyInt(), anyInt())).thenReturn(customers);
        when(mockAccountDao.findAllManagers(anyInt(), anyInt())).thenReturn(managers);
        when(mockAccountDao.getNumberOfRowsCustomers()).thenReturn(numberOfRows);
        when(mockAccountDao.getNumberOfRowsManagers()).thenReturn(numberOfRows);
        when(mockAccountDao.findByLogin("customer")).thenReturn(customer);
        when(mockAccountDao.findByLogin("manager")).thenReturn(manager);
        when(mockAccountDao.findById(100)).thenReturn(customer);
        when(mockAccountDao.findById(101)).thenReturn(manager);
        doNothing().when(mockAccountDao).update(any(Account.class));
        doNothing().when(mockAccountDao).create(any(Account.class));
        instance = new AccountService(mockDaoFactory);
    }

    @Test
    public void testGetCustomers() throws DBException {
        instance.getCustomers(1,3);

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).findAllCustomers(anyInt(), anyInt());
    }

    @Test(expected = DBException.class)
    public void testGetCustomersException() throws DBException {
        when(mockAccountDao.findAllCustomers(anyInt(), anyInt())).thenThrow(new DBException());
        instance.getCustomers(1,3);

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).findAllCustomers(anyInt(), anyInt());
    }

    @Test
    public void testGetManagers() throws DBException {
        instance.getManagers(1,3);

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).findAllManagers(anyInt(), anyInt());
    }

    @Test(expected = DBException.class)
    public void testGetManagersException() throws DBException {
        when(mockAccountDao.findAllManagers(anyInt(), anyInt())).thenThrow(new DBException());
        instance.getManagers(1,3);

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).findAllManagers(anyInt(), anyInt());
    }

    @Test
    public void testGetNumberOfRowsCustomers() throws DBException {
        instance.getNumberOfRowsCustomers();

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).getNumberOfRowsCustomers();
    }

    @Test(expected = DBException.class)
    public void testGetNumberOfRowsCustomersException() throws DBException {
        when(mockAccountDao.getNumberOfRowsCustomers()).thenThrow(new DBException());
        instance.getNumberOfRowsCustomers();

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).getNumberOfRowsCustomers();
    }

    @Test
    public void testGetNumberOfRowsManagers() throws DBException {
        instance.getNumberOfRowsManagers();

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).getNumberOfRowsManagers();
    }

    @Test(expected = DBException.class)
    public void testGetNumberOfRowsManagersException() throws DBException {
        when(mockAccountDao.getNumberOfRowsManagers()).thenThrow(new DBException());
        instance.getNumberOfRowsManagers();

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).getNumberOfRowsManagers();
    }

    @Test
    public void testLogin() throws DBException {
        instance.login("customer");

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).findByLogin("customer");
    }

    @Test(expected = DBException.class)
    public void testLoginException() throws DBException {
        when(mockAccountDao.findByLogin(anyString())).thenThrow(new DBException());
        instance.login("customer");

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).findByLogin("customer");
    }

    @Test
    public void testGetAccountById() throws DBException {
        instance.getAccountById(101);

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).findById(101);
    }

    @Test(expected = DBException.class)
    public void testGetAccountByIdException() throws DBException {
        when(mockAccountDao.findById(anyInt())).thenThrow(new DBException());
        instance.getAccountById(101);

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).findById(101);
    }

    @Test
    public void testBlockAccount() throws DBException {
        Account actual = customer;
        Optional<Account> c = instance.blockAccount(customer);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).update(customer);
        Assert.assertTrue(actual.isBlocked());
    }

    @Test(expected = DBException.class)
    public void testBlockAccountException() throws DBException {
        doThrow(new DBException()).when(mockAccountDao).update(any(Account.class));
        Account actual = customer;
        Optional<Account> c = instance.blockAccount(customer);

        if (c.isPresent()){
            actual = c.get();
        }

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).update(customer);
        Assert.assertFalse(actual.isBlocked());
    }

    @Test
    public void testUnblockAccount() throws DBException {
        Account actual = manager;
        Optional<Account> m = instance.unblockAccount(manager);

        if (m.isPresent()){
            actual = m.get();
        }

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).update(manager);
        Assert.assertFalse(actual.isBlocked());
    }

    @Test(expected = DBException.class)
    public void testUnblockAccountException() throws DBException {
        doThrow(new DBException()).when(mockAccountDao).update(any(Account.class));
        Account actual = manager;
        Optional<Account> m = instance.unblockAccount(manager);

        if (m.isPresent()){
            actual = m.get();
        }

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).update(manager);
        Assert.assertFalse(actual.isBlocked());
    }

    @Test
    public void testRegisterNewManager() throws DBException {
        Account actual = manager;
        Optional<Account> m = instance.registerNewManager("manager2", "manager2", "manager2@gmail.com");

        if (m.isPresent()){
            actual = m.get();
        }

        Account predicted = new Account(0, "manager2", "manager2", "manager2@gmail.com",
                Account.Role.MANAGER, false);

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).create(actual);
        Assert.assertEquals(actual, predicted);
    }

    @Test(expected = DBException.class)
    public void testRegisterNewManagerException() throws DBException {
        doThrow(new DBException()).when(mockAccountDao).create(any(Account.class));
        Account actual = manager;
        Optional<Account> m = instance.registerNewManager("manager2", "manager2", "manager2@gmail.com");

        if (m.isPresent()){
            actual = m.get();
        }

        Account predicted = new Account(0, "manager2", "manager2", "manager2@gmail.com",
                Account.Role.MANAGER, false);

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).create(actual);
        Assert.assertNotEquals(actual, predicted);
    }

    @Test
    public void testRegisterNewCustomer() throws DBException {
        Account actual = customer;
        Optional<Account> c = instance.registerNewCustomer("customer2", "customer2",
                "customer2@gmail.com");

        if (c.isPresent()){
            actual = c.get();
        }

        Account predicted = new Account(0, "customer2", "customer2", "customer2@gmail.com",
                Account.Role.CUSTOMER, false);

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).create(actual);
        Assert.assertEquals(actual, predicted);
    }

    @Test(expected = DBException.class)
    public void testRegisterNewCustomerException() throws DBException {
        doThrow(new DBException()).when(mockAccountDao).create(any(Account.class));
        Account actual = customer;
        Optional<Account> c = instance.registerNewCustomer("customer2", "customer2",
                "customer2@gmail.com");

        if (c.isPresent()){
            actual = c.get();
        }

        Account predicted = new Account(0, "customer2", "customer2", "customer2@gmail.com",
                Account.Role.CUSTOMER, false);

        verify(mockDaoFactory, times(1)).createAccountDao();
        verify(mockAccountDao, times(1)).create(actual);
        Assert.assertNotEquals(actual, predicted);
    }
}