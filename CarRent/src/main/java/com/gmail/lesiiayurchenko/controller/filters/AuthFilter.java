package com.gmail.lesiiayurchenko.controller.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    private static final Logger log = Logger.getLogger(AuthFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("Filter initialization starts");
        log.debug("Filter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        log.debug("Filter starts");

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        log.trace("Request uri --> " + req.getRequestURI());
        HttpSession session = req.getSession();
        ServletContext context = req.getSession().getServletContext();
        log.trace("Role --> " + session.getAttribute("role"));
        log.trace("Role --> " + context.getAttribute("loggedAccounts"));

        log.debug("Filter finished");

        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        log.debug("Filter destruction starts");
        log.debug("Filter destruction finished");
    }
}