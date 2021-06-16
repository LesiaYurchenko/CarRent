package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Account;
import com.gmail.lesiiayurchenko.model.service.AccountService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class AdminManagersCommand implements Command {
    private static final Logger log = Logger.getLogger(AdminManagersCommand.class);
    AccountService accountService;

    public AdminManagersCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Optional<String> page = Optional.ofNullable(request.getParameter("currentPage"));
        int currentPage = Integer.parseInt(page.orElse("1"));
        Optional<List<Account>> managers;
        try {
            managers = accountService.getManagers(currentPage, 3);
        } catch (DBException e) {
            log.error("Cannot get managers", e);
            return "/WEB-INF/error.jsp";
        }
        managers.ifPresent(managerList -> request.setAttribute("managers", managerList));
        int numberOfRows;
        try {
            numberOfRows = accountService.getNumberOfRowsManagers();
        } catch (DBException e) {
            log.error("Cannot get rows for managers", e);
            return "/WEB-INF/error.jsp";
        }
        int numberOfPages = numberOfRows / 3;
        if (numberOfRows % 3 > 0) {
            numberOfPages++;
        }
        request.setAttribute("noOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);

        return "/WEB-INF/admin/adminManagers.jsp";
    }
}
