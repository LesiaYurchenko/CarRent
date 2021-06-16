package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Account;
import com.gmail.lesiiayurchenko.model.service.AccountService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class AdminCustomersCommand implements Command {
    private static final Logger log = Logger.getLogger(AdminCustomersCommand.class);
    AccountService accountService;

    public AdminCustomersCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Optional<String> page = Optional.ofNullable(request.getParameter("currentPage"));
        int currentPage = Integer.parseInt(page.orElse("1"));
        Optional<List<Account>> customers;
        try {
            customers = accountService.getCustomers(currentPage, 3);
        } catch (DBException e) {
            log.error("Cannot get customers", e);
            return "/WEB-INF/error.jsp";
        }
        customers.ifPresent(customerList -> request.setAttribute("customers",  customerList));
        int numberOfRows;
        try {
            numberOfRows = accountService.getNumberOfRowsCustomers();
        } catch (DBException e) {
            log.error("Cannot get number of rows customers", e);
            return "/WEB-INF/error.jsp";
        }
        int numberOfPages = numberOfRows / 3;
        if (numberOfRows % 3 > 0) {
            numberOfPages++;
        }
        request.setAttribute("noOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);

        return "/WEB-INF/admin/adminCustomers.jsp";
    }
}

