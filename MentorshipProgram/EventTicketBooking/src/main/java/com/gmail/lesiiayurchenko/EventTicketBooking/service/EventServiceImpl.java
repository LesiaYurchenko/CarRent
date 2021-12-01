package com.gmail.lesiiayurchenko.EventTicketBooking.service;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.Event;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.User;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.repository.EventRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;


@Service
public class EventServiceImpl implements EventService {
    static Log log = LogFactory.getLog(EventServiceImpl.class.getName());

    private EventRepository eventRepo;

    @Autowired
    public EventServiceImpl(EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    @Override
    public Event getEventById(long id) {
        log.info("get Event by id: " + id);
        return eventRepo.findById(id).get();
    }

    @Override
    public Page<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        log.info("get Events by title: " + title);
        Pageable pageable = PageRequest.of (pageNum-1, pageSize);
        return eventRepo.findByTitle(title, pageable);
    }

    @Override
    public Page<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        log.info("get Events by day: " + day);
        Pageable pageable = PageRequest.of (pageNum-1, pageSize);
        return eventRepo.findByDate(day, pageable);
    }

    @Override
    public Event createEvent(Event event) {
        log.info("create Event: " + event);
        return eventRepo.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        log.info("update Event: " + event);
        Event myEvent = eventRepo.findById(event.getId()).get();
        myEvent.setTitle(event.getTitle());
        myEvent.setDate(event.getDate());
        myEvent.setTicketPrice(event.getTicketPrice());
        return eventRepo.save(myEvent);
    }

    @Override
    public boolean deleteEvent(long id) {
        log.info("delete Event by Id: " + id);
        boolean deleted = false;
        try{
            eventRepo.deleteById(id);
            deleted = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return deleted;
    }
}