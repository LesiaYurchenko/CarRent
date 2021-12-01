package com.gmail.lesiiayurchenko.EventTicketBooking.data.repository;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.Event;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    Page<Event> findByTitle(String title, Pageable pageable);
    Page<Event> findByDate(Date day, Pageable pageable);
}