package com.gmail.lesiiayurchenko.EventTicketBooking.service;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.Category;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.Event;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.Ticket;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.User;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.repository.TicketRepository;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.repository.CategoryRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class TicketServiceImpl implements TicketService {
    static Log log = LogFactory.getLog(TicketServiceImpl.class.getName());

    private TicketRepository ticketRepo;
    private CategoryRepository categoryRepo;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepo, CategoryRepository categoryRepo) {
        this.ticketRepo = ticketRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Ticket getTicketById(long id) {
        log.info("get Ticket by id: " + id);
        return ticketRepo.findById(id).get();
    }

    @Override
    @Transactional
    public Ticket bookTicket(User user, Event event, int place, Category category) {
        log.info("book Ticket for user: " + user + " for event " + event + ", place: " + place +
                ", category: " + category);
        Ticket ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setUser(user);
        ticket.setCategory(category);
        ticket.setPlace(place);
        return ticketRepo.save(ticket);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        log.info("get Booked Tickets by user: " + user);
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Ticket> page = ticketRepo.findByUser(user, pageable);
        List<Ticket> tickets = page.getContent();
        return tickets;
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        log.info("get Booked Tickets by event: " + event);
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Ticket> page = ticketRepo.findByEvent(event, pageable);
        List<Ticket> tickets = page.getContent();
        return tickets;
    }

    @Override
    @Transactional
    public boolean cancelTicket(long ticketId) {
        log.info("cancel Ticket by Id: " + ticketId);
        boolean deleted = false;
        try {
            ticketRepo.deleteById(ticketId);
            deleted = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public Category getCategoryById(long categoryId) {
        return categoryRepo.findById(categoryId).get();
    }
}
