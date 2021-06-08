package com.gmail.lesiiayurchenko.controller.listener;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;


public class SessionListener implements HttpSessionListener {
    private static final Logger log = Logger.getLogger(SessionListener.class);
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        log.debug("Session started");
        httpSessionEvent.getSession().setAttribute("lang", "en");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HashSet<String> loggedAccounts = (HashSet<String>) httpSessionEvent
                .getSession().getServletContext()
                .getAttribute("loggedAccounts");
        String accountName = (String) httpSessionEvent.getSession()
                .getAttribute("accountLogin");
        loggedAccounts.remove(accountName);
        log.trace(accountName + "logout");
        httpSessionEvent.getSession().setAttribute("loggedAccounts", loggedAccounts);
        log.debug("Session destroyed");
    }
}