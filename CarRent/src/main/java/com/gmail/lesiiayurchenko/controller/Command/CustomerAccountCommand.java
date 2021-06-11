package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Account;
import com.gmail.lesiiayurchenko.model.service.AccountService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CustomerAccountCommand  implements Command {
    private static final Logger log = Logger.getLogger(CustomerAccountCommand.class);
    AccountService accountService;

    public CustomerAccountCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int customerID = (Integer) request.getSession().getAttribute("id");
        Optional<Account> customer;
        try {
            customer = accountService.getAccountById(customerID);
        } catch (DBException e) {
            log.error("Cannot get account by id", e);
            return "/WEB-INF/error.jsp";
        }
        customer.ifPresent(acc -> request.setAttribute("account", acc));
        return "/WEB-INF/customer/customeraccount.jsp";
    }
}