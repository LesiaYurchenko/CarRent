package com.gmail.lesiiayurchenko.model.dao;

import com.gmail.lesiiayurchenko.model.entity.Account;

import java.util.List;

public interface AccountDao extends GenericDao<Account> {
    Account findByLogin(String login) throws DBException;
    List<Account> findAllCustomers(int currentPage, int recordsPerPage) throws DBException;
    List<Account> findAllManagers(int currentPage, int recordsPerPage) throws DBException;
    int getNumberOfRowsCustomers() throws DBException;
    int getNumberOfRowsManagers() throws DBException;
}
