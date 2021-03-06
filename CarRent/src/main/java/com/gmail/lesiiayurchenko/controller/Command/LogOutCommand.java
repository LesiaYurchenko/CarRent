package com.gmail.lesiiayurchenko.controller.Command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class LogOutCommand implements Command {
    private static final Logger log = Logger.getLogger(LogOutCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        log.debug("Command starts");
        CommandUtility.deleteAccountFromContext(request);
        CommandUtility.deleteAccountFromSession(request);
        log.debug("Command finished");
        return "redirect:/main";
    }
}
