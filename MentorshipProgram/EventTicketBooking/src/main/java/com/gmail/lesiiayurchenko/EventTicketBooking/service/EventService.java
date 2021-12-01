package com.gmail.lesiiayurchenko.EventTicketBooking.service;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.Event;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface EventService  {
    Event getEventById(long id);
    Page<Event> getEventsByTitle(String title, int pageSize, int pageNum);
    Page<Event> getEventsForDay(Date day, int pageSize, int pageNum);
    Event createEvent(Event event);
    Event updateEvent(Event event);
    boolean deleteEvent(long id);
}

