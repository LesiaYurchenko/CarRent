package com.gmail.lesiiayurchenko.EventTicketBooking.service;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.Category;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.Event;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.Ticket;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.User;

import java.util.List;

public interface TicketService {
    Ticket getTicketById(long id);
    Ticket bookTicket(User user, Event event, int place, Category category);
    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum);
    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);
    boolean cancelTicket(long ticketId);
    Category getCategoryById(long categoryId);
}
