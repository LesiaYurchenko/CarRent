package com.gmail.lesiiayurchenko.EventTicketBooking.data.repository;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail (String email);
    Page<User> findByName(String name, Pageable pageable);
}
