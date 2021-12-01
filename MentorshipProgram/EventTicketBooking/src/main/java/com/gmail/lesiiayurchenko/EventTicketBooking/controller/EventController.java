package com.gmail.lesiiayurchenko.EventTicketBooking.controller;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.Event;
import com.gmail.lesiiayurchenko.EventTicketBooking.facade.BookingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
public class EventController {

    private BookingFacade bookingFacade;

    @Autowired
    public EventController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @PostMapping("/new")
    public
    @ResponseBody
    Event createEvent(@RequestParam(required = true) int year,
                      @RequestParam(required = true) int month,
                      @RequestParam(required = true) int day,
                      @RequestParam(required = true) String title,
                      @RequestParam(required = true) BigDecimal ticketPrice ) {
        Event event = new Event();
        event.setTitle(title);
        event.setDate(new Date(year-1900, month-1, day));
        event.setTicketPrice(ticketPrice);
        return bookingFacade.createEvent(event);
    }

    @PutMapping("/edit/{id}")
    public
    @ResponseBody
    Event updateEvent(@PathVariable long id,
                      @RequestParam(required = true) int year,
                      @RequestParam(required = true) int month,
                      @RequestParam(required = true) int day,
                      @RequestParam(required = true) String title,
                      @RequestParam(required = true) BigDecimal ticketPrice ) {
        Event event = new Event();
        event.setId(id);
        event.setTitle(title);
        event.setDate(new Date(year-1900, month-1, day));
        event.setTicketPrice(ticketPrice);
        return bookingFacade.updateEvent(event);
    }

    @GetMapping("/{eventId}")
    public
    @ResponseBody
    Event getEventById(@PathVariable long eventId) {
        return bookingFacade.getEventById(eventId);
    }


    @GetMapping("/title/{title}")
    public
    @ResponseBody
    List<Event> getEventsByTitle(@PathVariable String title,
                                 @RequestParam(required = true) int pageSize,
                                 @RequestParam(required = true) int pageNum) {
        return bookingFacade.getEventsByTitle(title, pageSize, pageNum);
    }

    @GetMapping("/date/{year}/{month}/{day}")
    public
    @ResponseBody
    List<Event> getEventsForDay(@PathVariable int year,
                                @PathVariable int month,
                                @PathVariable int day,
                                @RequestParam(required = true) int pageSize,
                                @RequestParam(required = true) int pageNum) {
        Date date = new Date(year-1900, month-1, day);
        return bookingFacade.getEventsForDay(date, pageSize, pageNum);
    }

    @DeleteMapping("/{id}")
    public
    @ResponseBody
    boolean deleteEvent(@PathVariable Long id) {
        return bookingFacade.deleteEvent(id);
    }
}











