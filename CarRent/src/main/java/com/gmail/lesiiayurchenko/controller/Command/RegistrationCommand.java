package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.service.AccountService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private static final Logger log = Logger.getLogger(RegistrationCommand.class);
    AccountService accountService;

    public RegistrationCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String execute(HttpServletRequest request) {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
        try {
            accountService.registerNewCustomer(login, password, email);
        } catch (DBException e) {
            log.error("Cannot register new customer", e);
            return "/WEB-INF/error.jsp";
        }
        return "redirect:/login";
        }
}