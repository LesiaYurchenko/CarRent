package com.gmail.lesiiayurchenko.EventTicketBooking.controller;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.UserAccount;
import com.gmail.lesiiayurchenko.EventTicketBooking.facade.BookingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping(path = "/accounts")
public class UserAccountController {

    private BookingFacade bookingFacade;

    @Autowired
    public UserAccountController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @PutMapping("/edit/{id}")
    public
    @ResponseBody
    UserAccount refillUserAccount(@PathVariable long id,
                                  @RequestParam(required = true) BigDecimal money) {
        return bookingFacade.refillUserAccount(money, id);
    }
}
