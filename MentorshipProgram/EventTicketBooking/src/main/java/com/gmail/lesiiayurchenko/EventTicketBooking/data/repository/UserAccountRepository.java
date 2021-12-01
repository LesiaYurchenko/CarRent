package com.gmail.lesiiayurchenko.EventTicketBooking.data.repository;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

}
