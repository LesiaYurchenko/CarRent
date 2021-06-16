package com.gmail.lesiiayurchenko.controller.filters;


import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class LocalizationFilter implements Filter {
    private static final Logger log = Logger.getLogger(EncodingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        log.debug("Filter initialization starts");
        log.debug("Filter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        log.debug("Filter starts");
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        if (Optional.ofNullable(req.getParameter("lang")).isPresent()) {
            req.getSession().setAttribute("lang", req.getParameter("lang"));
        }
        log.debug("Filter finished");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.debug("Filter destruction starts");
        log.debug("Filter destruction finished");
    }
}

