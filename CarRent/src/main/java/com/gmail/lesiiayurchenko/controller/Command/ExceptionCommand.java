package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.entity.Account;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ExceptionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        Account.Role role = Account.Role.GUEST;
        if (Optional.ofNullable(request.getSession().getAttribute("role")).isPresent()) {
            role = (Account.Role) request.getSession().getAttribute("role");
        }
        String page;
        switch (role){
            case CUSTOMER:
                page =  "redirect:/customerBasis";
                break;
            case ADMIN:
                page =  "redirect:/adminCars";
                break;
            case MANAGER:
                page =  "redirect:/managerNewBookings";
                break;
            default:
                page = "redirect:/main";
                break;
        }
        return page;
    }
}
