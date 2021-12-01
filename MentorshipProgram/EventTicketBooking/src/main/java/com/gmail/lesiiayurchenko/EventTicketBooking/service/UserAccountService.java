package com.gmail.lesiiayurchenko.EventTicketBooking.service;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.UserAccount;

public interface UserAccountService {
    UserAccount getUserAccountById(long id);
    UserAccount updateUserAccount(UserAccount userAccount);
}

