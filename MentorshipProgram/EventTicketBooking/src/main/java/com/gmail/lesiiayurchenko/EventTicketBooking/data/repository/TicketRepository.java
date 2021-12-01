package com.gmail.lesiiayurchenko.EventTicketBooking.data.repository;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.Event;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.Ticket;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    Page<Ticket> findByUser(User user, Pageable pageable);
    Page<Ticket> findByEvent(Event event, Pageable pageable);
}
