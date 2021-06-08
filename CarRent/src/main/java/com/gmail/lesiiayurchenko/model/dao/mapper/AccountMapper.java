package com.gmail.lesiiayurchenko.model.dao.mapper;

import com.gmail.lesiiayurchenko.model.entity.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AccountMapper implements ObjectMapper<Account> {

    @Override
    public Account extractFromResultSet(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt("id_account"));
        account.setLogin(rs.getString("login"));
        account.setPassword(rs.getString("password"));
        account.setEmail(rs.getString("email"));
        account.setRole(Account.Role.values()[rs.getInt("role_id")-1]);
        account.setBlocked(rs.getInt("blocked")!=0);
        return account;
    }

    public Account makeUnique(Map<Integer, Account> cache,
                              Account account) {
        cache.putIfAbsent(account.getId(), account);
        return cache.get(account.getId());
    }
}
