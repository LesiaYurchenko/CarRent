package com.gmail.lesiiayurchenko.EventTicketBooking.facade;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.*;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.repository.UserRepository;
import com.gmail.lesiiayurchenko.EventTicketBooking.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class BookingFacadeImpl implements BookingFacade{

    static Log log = LogFactory.getLog(BookingFacadeImpl.class.getName());

    private UserService userService;
    private EventService eventService;
    private TicketService ticketService;
    private UserAccountService userAccountService;

    @Autowired
    public BookingFacadeImpl(UserService userService, EventService eventService,
                             TicketService ticketService, UserAccountService userAccountService) {
        this.userService = userService;
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userAccountService = userAccountService;
    }


    @Override
    public Event getEventById(long eventId) {
        return eventService.getEventById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        Page<Event> page = eventService.getEventsByTitle(title, pageSize, pageNum);
        List<Event> events = page.getContent();
        return events;
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        Page<Event> page = eventService.getEventsForDay(day, pageSize, pageNum);
        List<Event> events = page.getContent();
        return events;
    }

    @Override
    public Event createEvent(Event event) {
        return eventService.createEvent(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventService.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return eventService.deleteEvent(eventId);
    }

    @Override
    public User getUserById(long userId) {
        return userService.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        Page<User> page = userService.getUsersByName(name, pageSize, pageNum);
        List<User> users = page.getContent();
        return users;
    }

    @Override
    public User createUser(User user) {
        return userService.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userService.updateUser(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        return userService.deleteUser(userId);
    }

    @Override
    @Transactional
    public Ticket bookTicket(long userId, long eventId, int place, Category category) {
        Ticket ticket = null;
        User user = userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);
        UserAccount userAccount = userAccountService.getUserAccountById(userId);
        BigDecimal balance = userAccount.getBalance();
        BigDecimal ticketPrice = event.getTicketPrice();
        if (balance.compareTo(ticketPrice)>=0){
            withdrawMoneyFromUserAccount(ticketPrice, userId);
            ticket = ticketService.bookTicket(user, event,  place, category);
            log.info("book Ticket for user: " + user + " for event " + event + ", place: " + place +
                    ", category: " + category);
        } else {
            log.info("NOT ENOUGH MONEY to book Ticket for user: " + user + " for event " + event +
                    ", place: " + place + ", category: " + category);
        }
        return ticket;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    @Transactional
    public boolean cancelTicket(long ticketId) {
        log.info("cancel Ticket by ticketId: " + ticketId);
        Ticket ticket = ticketService.getTicketById(ticketId);
        refillUserAccount(ticket.getEvent().getTicketPrice(), ticket.getUser().getId());
        return ticketService.cancelTicket(ticketId);
    }

    @Override
    public Category getCategoryById(long categoryId) {
        return ticketService.getCategoryById(categoryId);
    }

    @Override
    public UserAccount refillUserAccount(BigDecimal money, Long id) {
        UserAccount userAccount = userAccountService.getUserAccountById(id);
        BigDecimal balance = userAccount.getBalance().add(money);
        userAccount.setBalance(balance);
        log.info("refill UserAccount by ID: " + id + " with the amount of money: " + money);
        return userAccountService.updateUserAccount(userAccount);
    }

    @Override
    public UserAccount withdrawMoneyFromUserAccount(BigDecimal money, Long id) {
        UserAccount userAccount = userAccountService.getUserAccountById(id);
        BigDecimal balance = userAccount.getBalance().subtract(money);
        userAccount.setBalance(balance);
        log.info("withdraw the amount of money: " + money + " from UserAccount by ID: " + id);
        return userAccountService.updateUserAccount(userAccount);
    }
}
