package com.gmail.lesiiayurchenko.model.dao.mapper;

import com.gmail.lesiiayurchenko.model.dao.SQLConstants;
import com.gmail.lesiiayurchenko.model.entity.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AccountMapper implements ObjectMapper<Account> {

    @Override
    public Account extractFromResultSet(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt(SQLConstants.ACCOUNT_ID));
        account.setLogin(rs.getString(SQLConstants.LOGIN));
        account.setPassword(rs.getString(SQLConstants.PASSWORD));
        account.setEmail(rs.getString(SQLConstants.EMAIL));
        account.setRole(Account.Role.values()[rs.getInt(SQLConstants.ROLE_ID)-1]);
        account.setBlocked(rs.getInt(SQLConstants.BLOCKED)!=0);
        return account;
    }

    public Account makeUnique(Map<Integer, Account> cache,
                              Account account) {
        cache.putIfAbsent(account.getId(), account);
        return cache.get(account.getId());
    }
}
