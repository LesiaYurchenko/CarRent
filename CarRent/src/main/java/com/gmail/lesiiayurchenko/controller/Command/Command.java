package com.gmail.lesiiayurchenko.controller.Command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request);
}
