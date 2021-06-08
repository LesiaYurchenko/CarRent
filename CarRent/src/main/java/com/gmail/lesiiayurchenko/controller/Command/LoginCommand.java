package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Account;
import com.gmail.lesiiayurchenko.model.service.AccountService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class LoginCommand implements Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("Command starts");
        String login = request.getParameter("login");
        log.trace("Request parameter: loging --> " + login);
        String pass = request.getParameter("pass");

        if (login == null || login.equals("") || pass == null || pass.equals("")) {
            log.trace("Login or password is empty");
            log.debug("Command finished");
            return "/login.jsp";
        }

        try {
            if (!accountService.login(login).isPresent()) {
                log.trace("Login or password is wrong");
                log.debug("Command finished");
                return "/login.jsp";
            }
        } catch (DBException e) {
            log.error("Cannot check account with DB", e);
            return "/WEB-INF/error.jsp";
        }

        Account account;
        try {
            account = accountService.login(login).get();
        } catch (DBException e) {
            log.error("Cannot get account by login", e);
            return "/WEB-INF/error.jsp";
        }

        if (!pass.equals(account.getPassword())) {
            log.trace("Login or password is wrong");
            log.debug("Command finished");
            return "/login.jsp";
        }

        if (CommandUtility.checkAccountIsLogged(request, login)) {
            log.trace("Error: account is already logged");
            log.debug("Command finished");
            return "/WEB-INF/error.jsp";
        }

        if (Account.Role.ADMIN.equals(account.getRole())) {
            CommandUtility.putInfoIntoSession(request, Account.Role.ADMIN, login, account.getId());
            log.trace("Admin " + login + " logged successfully");
            log.debug("Command finished");
            return "redirect:/admincars";
        } else if (Account.Role.MANAGER.equals(account.getRole())) {
            CommandUtility.putInfoIntoSession(request, Account.Role.MANAGER, login, account.getId());
            log.trace("Manager" + login + " logged successfully");
            log.debug("Command finished");
            return "redirect:/managernewbookings";
        } else if (Account.Role.CUSTOMER.equals(account.getRole())) {
            CommandUtility.putInfoIntoSession(request, Account.Role.CUSTOMER, login, account.getId());
            log.trace("Customer" + login + " logged successfully");
            log.debug("Command finished");
            return "redirect:/customerbasis";
        } else {
            log.trace("Role is guest");
            log.debug("Command finished");
            return "redirect:/main";
        }
    }

}
