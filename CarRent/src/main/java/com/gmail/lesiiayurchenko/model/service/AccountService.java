package com.gmail.lesiiayurchenko.model.service;

import com.gmail.lesiiayurchenko.model.dao.AccountDao;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.dao.DaoFactory;
import com.gmail.lesiiayurchenko.model.entity.Account;

import java.util.List;
import java.util.Optional;

public class AccountService {

    DaoFactory daoFactory;

    public AccountService() {
       this.daoFactory = DaoFactory.getInstance();
    }

    public AccountService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Optional<List<Account>> getCustomers(int currentPage, int recordsPerPage) throws DBException {
        try (AccountDao dao = daoFactory.createAccountDao()) {
            return Optional.ofNullable(dao.findAllCustomers(currentPage, recordsPerPage));
        } catch (DBException e) {
            throw e;
        }
    }


    public Optional<List<Account>>  getManagers(int currentPage, int recordsPerPage) throws DBException {
        try (AccountDao dao = daoFactory.createAccountDao()) {
            return Optional.ofNullable(dao.findAllManagers(currentPage, recordsPerPage));
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
        Optional<Account> result;
        try (AccountDao dao = daoFactory.createAccountDao()) {
            result = Optional.ofNullable(dao.findByLogin(login));
        } catch (DBException e) {
            throw e;
        }
        return result;
    }

    public Optional<Account>  getAccountById(int id) throws DBException {
        try (AccountDao dao = daoFactory.createAccountDao()) {
            return Optional.ofNullable(dao.findById(id));
        } catch (DBException e){
            throw e;
        }
    }

    public Optional<Account> blockAccount(Account account) throws DBException {
        account.setBlocked(true);
        try (AccountDao dao = daoFactory.createAccountDao()) {
            dao.update(account);
            return Optional.ofNullable(account);
        } catch (DBException e){
            throw e;
        }
    }

    public Optional<Account>  unblockAccount(Account account) throws DBException {
        account.setBlocked(false);
        try (AccountDao dao = daoFactory.createAccountDao()) {
            dao.update(account);
            return Optional.ofNullable(account);
        } catch (DBException e){
            throw e;
        }
    }

    public Optional<Account>  registerNewManager(String login, String password, String email) throws DBException {
        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        account.setEmail(email);
        account.setRole(Account.Role.MANAGER);
        account.setBlocked(false);
        try (AccountDao dao = daoFactory.createAccountDao()) {
            dao.create(account);
            return Optional.ofNullable(account);
        } catch (DBException e){
            throw e;
        }
    }

    public Optional<Account>  registerNewCustomer(String login, String password, String email) throws DBException {
        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        account.setEmail(email);
        account.setRole(Account.Role.CUSTOMER);
        account.setBlocked(false);
        try (AccountDao dao = daoFactory.createAccountDao()) {
            dao.create(account);
            return Optional.ofNullable(account);
        } catch (DBException e){
            throw e;
        }
    }
}

