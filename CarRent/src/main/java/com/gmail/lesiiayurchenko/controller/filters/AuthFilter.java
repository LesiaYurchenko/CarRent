package com.gmail.lesiiayurchenko.controller.filters;

import com.gmail.lesiiayurchenko.model.dao.DBException;
import com.gmail.lesiiayurchenko.model.entity.Account;
import com.gmail.lesiiayurchenko.model.service.AccountService;
import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;
import java.util.Optional;

public class AuthFilter implements Filter {
    private static final Logger log = Logger.getLogger(AuthFilter.class);
    AccountService accountService = new AccountService();

    @Override
    public void init(FilterConfig filterConfig) {
        log.debug("Filter initialization starts");
        log.debug("Filter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        log.debug("Filter starts");

        String login = request.getParameter("login");
        log.trace("Request parameter: loging --> " + login);
        String password = request.getParameter("pass");


        try {
            Optional<Account> acc = accountService.login(login);
            if (acc.isPresent()
                    && password.equals(acc.get().getPassword())) {
                request.setAttribute("role", acc.get().getRole());
                request.setAttribute("id", acc.get().getId());
                log.debug("Filter finished");
                filterChain.doFilter(request, response);
            } else {
                    log.trace("Login or password is wrong");
                    request.setAttribute("wrong", true);
                    log.debug("Filter finished");
                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                    rd.include(request, response);
                }
            } catch (DBException e) {
            log.error("Cannot get account by login", e);
            request.setAttribute("wrong", true);
            log.debug("Filter finished");
            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
            rd.include(request, response);
        }
    }

        @Override
        public void destroy () {
            log.debug("Filter destruction starts");
            log.debug("Filter destruction finished");
        }
    }