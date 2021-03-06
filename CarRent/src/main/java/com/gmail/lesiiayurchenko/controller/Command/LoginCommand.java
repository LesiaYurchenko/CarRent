package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.entity.Account;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class LoginCommand implements Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("Command starts");
        String login = request.getParameter("login");
        log.trace("Request parameter: login --> " + login);
        Account.Role role = (Account.Role)(request.getAttribute("role"));
        log.trace("Request parameter: role --> " + role);
        int id = (Integer)(request.getAttribute("id"));
        log.trace("Request parameter: id --> " + id);

        if (CommandUtility.checkAccountIsLogged(request, login)) {
            log.trace("Error: account is already logged");
            log.debug("Command finished");
            return "/WEB-INF/error.jsp";
        }

        if (Account.Role.ADMIN.equals(role)) {
            CommandUtility.putInfoIntoSession(request, Account.Role.ADMIN, login, id);
            log.trace("Admin " + login + " logged successfully");
            log.debug("Command finished");
            return "redirect:/adminCars";
        } else if (Account.Role.MANAGER.equals(role)) {
            CommandUtility.putInfoIntoSession(request, Account.Role.MANAGER, login, id);
            log.trace("Manager" + login + " logged successfully");
            log.debug("Command finished");
            return "redirect:/managerNewBookings";
        } else if (Account.Role.CUSTOMER.equals(role)) {
            CommandUtility.putInfoIntoSession(request, Account.Role.CUSTOMER, login, id);
            log.trace("Customer" + login + " logged successfully");
            log.debug("Command finished");
            return "redirect:/customerBasis";
        } else {
            log.trace("Role is guest");
            log.debug("Command finished");
            return "redirect:/main";
        }
    }

}
