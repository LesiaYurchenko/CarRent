package com.gmail.lesiiayurchenko.EventTicketBooking.service;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.User;
import com.gmail.lesiiayurchenko.EventTicketBooking.data.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class UserServiceImpl implements UserService {
    static Log log = LogFactory.getLog(UserServiceImpl.class.getName());

    private UserRepository userRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User getUserByEmail(String email) {
        log.info("get User by email: " + email);
        return userRepo.findByEmail(email).get();
    }

    @Override
    public User getUserById(long id) {
        log.info("get User by id: " + id);
        return userRepo.findById(id).get();
    }

    @Override
    public Page<User> getUsersByName(String name, int pageSize, int pageNum) {
        log.info("get Users by name: " + name);
        Pageable pageable = PageRequest.of (pageNum-1, pageSize);
        return userRepo.findByName(name, pageable);
    }

    @Override
    public User createUser(User user) {
        log.info("create User: " + user);
        return userRepo.save(user);
    }

    @Override
    public User updateUser(User user) {
        log.info("update User: " + user);
        User myUser = userRepo.findById(user.getId()).get();
        myUser.setName(user.getName());
        myUser.setEmail(user.getEmail());
        return userRepo.save(myUser);
    }

    @Override
    public boolean deleteUser(long id) {
        log.info("delete User by Id: " + id);
        boolean deleted = false;
        try{
            userRepo.deleteById(id);
            deleted = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return deleted;
    }
}