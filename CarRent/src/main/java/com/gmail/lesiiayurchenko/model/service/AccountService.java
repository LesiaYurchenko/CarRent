package com.gmail.lesiiayurchenko.model.service;

import com.gmail.lesiiayurchenko.model.dao.AccountDao;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.dao.DaoFactory;
import com.gmail.lesiiayurchenko.model.entity.Account;

import java.util.List;
import java.util.Optional;

public class AccountService {

    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Account> getCustomers(int currentPage, int recordsPerPage) throws DBException {
        try (AccountDao dao = daoFactory.createAccountDao()) {
            return dao.findAllCustomers(currentPage, recordsPerPage);
        } catch (DBException e) {
            throw e;
        }
    }

    public List<Account> getManagers(int currentPage, int recordsPerPage) throws DBException {
        try (AccountDao dao = daoFactory.createAccountDao()) {
            return dao.findAllManagers(currentPage, recordsPerPage);
        } catch (DBException e) {
            throw e;
        }
    }

    public int getNumberOfRowsCustomers() throws DBException {
        try (AccountDao dao = daoFactory.createAccountDao()) {
            return dao.getNumberOfRowsCustomers();
        } catch (DBException e) {
            throw e;
        }
    }

    public int getNumberOfRowsManagers() throws DBException {
        try (AccountDao dao = daoFactory.createAccountDao()) {
            return dao.getNumberOfRowsManagers();
        } catch (DBException e) {
            throw e;
        }
    }

    public Optional<Account> login(String login) throws DBException {
        Optional<Account> result; //= Optional.empty();
        try (AccountDao dao = daoFactory.createAccountDao()) {
            result = Optional.ofNullable(dao.findByLogin(login));
        } catch (DBException e) {
            throw e;
        }
        return result;
    }

    public Account getAccountById(int id) throws DBException {
        try (AccountDao dao = daoFactory.createAccountDao()) {
            return dao.findById(id);
        } catch (DBException e){
            throw e;
        }
    }

    public void blockAccount(Account account) throws DBException {
        account.setBlocked(true);
        try (AccountDao dao = daoFactory.createAccountDao()) {
            dao.update(account);
        } catch (DBException e){
            throw e;
        }
    }

    public void unblockAccount(Account account) throws DBException {
        account.setBlocked(false);
        try (AccountDao dao = daoFactory.createAccountDao()) {
            dao.update(account);
        } catch (DBException e){
            throw e;
        }
    }

    public void registerNewManager(String login, String password, String email) throws DBException {
        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        account.setEmail(email);
        account.setRole(Account.Role.MANAGER);
        account.setBlocked(false);
        try (AccountDao dao = daoFactory.createAccountDao()) {
            dao.create(account);
        } catch (DBException e){
            throw e;
        }
    }

    public void registerNewCustomer(String login, String password, String email) throws DBException {
        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        account.setEmail(email);
        account.setRole(Account.Role.CUSTOMER);
        account.setBlocked(false);
        try (AccountDao dao = daoFactory.createAccountDao()) {
            dao.create(account);
        } catch (DBException e){
            throw e;
        }
    }
}

