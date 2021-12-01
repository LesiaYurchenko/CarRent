package com.gmail.lesiiayurchenko.EventTicketBooking.service;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.User;
import org.springframework.data.domain.Page;

public interface UserService  {
    User getUserByEmail(String email);
    User getUserById(long id);
    Page<User> getUsersByName(String name, int pageSize, int pageNum);
    User createUser(User user);
    User updateUser(User user);
    boolean deleteUser(long id);
}
