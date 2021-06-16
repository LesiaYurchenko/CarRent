package com.gmail.lesiiayurchenko.model.dao.impl;

import com.gmail.lesiiayurchenko.model.dao.AccountDao;
import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.dao.SQLConstants;
import com.gmail.lesiiayurchenko.model.dao.mapper.AccountMapper;
import com.gmail.lesiiayurchenko.model.entity.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCAccountDao implements AccountDao {
    private Connection connection;


    public JDBCAccountDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Account entity) throws DBException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQLConstants.CREATE_ACCOUNT);
            int k = 1;
            pstmt.setInt(k++, entity.getId());
            pstmt.setString(k++, entity.getLogin());
            pstmt.setString(k++, entity.getPassword());
            pstmt.setString(k++, entity.getEmail());
            pstmt.setInt(k++, entity.getRole().ordinal()+1);
            pstmt.setInt(k, entity.isBlocked()?1:0);
            pstmt.executeUpdate();
        } catch (SQLException e){
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(pstmt);
        }
    }

    @Override
    public Account findById(int id) throws DBException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = SQLConstants.FIND_ACCOUNT_BY_ID;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            AccountMapper accountMapper = new AccountMapper();
            Account account = null;
            while(rs.next()) {
                account = accountMapper.extractFromResultSet(rs);
            }
            return account;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public Account findByLogin(String login) throws DBException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = SQLConstants.FIND_ACCOUNT_BY_LOGIN;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            AccountMapper accountMapper = new AccountMapper();
            Account account = null;
            while(rs.next()) {
                account = accountMapper.extractFromResultSet(rs);
            }
            return account;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Account> findAll() throws DBException{
        List <Account> accounts = new ArrayList<>();

        String query = SQLConstants.FIND_ALL_ACCOUNTS;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            AccountMapper accountMapper = new AccountMapper();
            while (rs.next()) {
                Account account = accountMapper.extractFromResultSet(rs);
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        }
    }

    @Override
    public List<Account> findAllManagers(int currentPage, int recordsPerPage) throws DBException{
        List <Account> accounts = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = SQLConstants.FIND_ALL_MANAGERS;
        try {
            pstmt = connection.prepareStatement(query);
            int k =1;
            pstmt.setInt(k++, Account.Role.MANAGER.ordinal()+1);
            pstmt.setInt(k++, (currentPage-1) * recordsPerPage);
            pstmt.setInt(k, recordsPerPage);
            rs = pstmt.executeQuery();
            AccountMapper accountMapper = new AccountMapper();
            while (rs.next()) {
                Account account = accountMapper.extractFromResultSet(rs);
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        }finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public List<Account> findAllCustomers(int currentPage, int recordsPerPage) throws DBException{
        List <Account> accounts = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = SQLConstants.FIND_ALL_CUSTOMERS;
        try {
            pstmt = connection.prepareStatement(query);
            int k =1;
            pstmt.setInt(k++, Account.Role.CUSTOMER.ordinal()+1);
            pstmt.setInt(k++, (currentPage-1) * recordsPerPage);
            pstmt.setInt(k, recordsPerPage);
            rs = pstmt.executeQuery();
            AccountMapper accountMapper = new AccountMapper();
            while (rs.next()) {
                Account account = accountMapper.extractFromResultSet(rs);
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        }finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public int getNumberOfRowsCustomers() throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int numberOfRows = 0;
        String query = SQLConstants.GET_NUMBER_OF_ROWS_CUSTOMERS;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, Account.Role.CUSTOMER.ordinal() + 1);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                numberOfRows = rs.getInt(SQLConstants.NUMBER);
            }
            return numberOfRows;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        }finally {
            close(rs);
            close(pstmt);
        }
    }


    @Override
    public int getNumberOfRowsManagers() throws DBException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int numberOfRows = 0;
        String query = SQLConstants.GET_NUMBER_OF_ROWS_MANAGERS;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, Account.Role.MANAGER.ordinal()+1);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                numberOfRows = rs.getInt(SQLConstants.NUMBER);
            }
            return numberOfRows;
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        }finally {
            close(rs);
            close(pstmt);
        }
    }

    @Override
    public void update(Account entity) throws DBException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQLConstants.UPDATE_ACCOUNT);
            int k = 1;
            pstmt.setString(k++, entity.getLogin());
            pstmt.setString(k++, entity.getPassword());
            pstmt.setString(k++, entity.getEmail());
            pstmt.setInt(k++, entity.getRole().ordinal()+1);
            pstmt.setInt(k++, entity.isBlocked()?1:0);
            pstmt.setInt(k, entity.getId());
            pstmt.executeUpdate();
        } catch (SQLException e){
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(pstmt);
        }
    }

    @Override
    public void delete(int id) throws DBException{
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQLConstants.DELETE_ACCOUNT);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        } finally {
            close(pstmt);
        }
    }

    @Override
    public void close() throws DBException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DBException(DBException.DB_EXCEPTION, e);
        }
    }
}
