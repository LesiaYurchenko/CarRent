package com.gmail.lesiiayurchenko.EventTicketBooking.controller;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.Category;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.Event;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.Ticket;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.User;
import com.gmail.lesiiayurchenko.EventTicketBooking.facade.BookingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tickets")
public class TicketController {

    private BookingFacade bookingFacade;

    @Autowired
    public TicketController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @PostMapping("/new")
    public
    @ResponseBody
    Ticket bookTicket(@RequestParam(required = true) long userId,
                      @RequestParam(required = true) long eventId,
                      @RequestParam(required = true) int place,
                      @RequestParam(required = true) long categoryId) {
        Category category = bookingFacade.getCategoryById(categoryId);
        return bookingFacade.bookTicket(userId, eventId, place, category);
    }

    @GetMapping("/users/{userId}")
    public
    @ResponseBody
    List<Ticket> getBookedTicketsByUser(@PathVariable Long userId,
                                        @RequestParam(required = true) int pageSize,
                                        @RequestParam(required = true) int pageNum) {
        User user = bookingFacade.getUserById(userId);
        return bookingFacade.getBookedTickets(user, pageSize, pageNum);
    }

    @GetMapping("/events/{eventId}")
    public
    @ResponseBody
    List<Ticket> getBookedTicketsByEvent(@PathVariable Long eventId,
                                         @RequestParam(required = true) int pageSize,
                                         @RequestParam(required = true) int pageNum) {
        Event event = bookingFacade.getEventById(eventId);
        return bookingFacade.getBookedTickets(event, pageSize, pageNum);
    }

    @DeleteMapping("/{id}")
    public
    @ResponseBody
    boolean cancelTicket(@PathVariable Long id) {
        return bookingFacade.cancelTicket(id);
    }
}
