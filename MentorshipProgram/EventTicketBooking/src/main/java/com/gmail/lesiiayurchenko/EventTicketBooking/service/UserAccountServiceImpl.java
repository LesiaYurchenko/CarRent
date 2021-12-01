package com.gmail.lesiiayurchenko.EventTicketBooking.service;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.UserAccount;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.repository.UserAccountRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService{
    static Log log = LogFactory.getLog(UserAccountServiceImpl.class.getName());

    private UserAccountRepository userAccountRepo;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepo) {
        this.userAccountRepo = userAccountRepo;
    }

    @Override
    public UserAccount getUserAccountById(long id) {
        log.info("get UserAccount by id: " + id);
        return userAccountRepo.findById(id).get();
    }

    @Override
    public UserAccount updateUserAccount(UserAccount userAccount) {
        log.info("update UserAccount: " + userAccount);
        return userAccountRepo.save(userAccount);
    }
}
