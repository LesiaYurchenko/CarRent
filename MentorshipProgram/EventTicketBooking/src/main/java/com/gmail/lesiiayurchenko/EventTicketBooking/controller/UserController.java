package com.gmail.lesiiayurchenko.EventTicketBooking.controller;

import com.gmail.lesiiayurchenko.EventTicketBooking.data.entity.User;
import com.gmail.lesiiayurchenko.EventTicketBooking.facade.BookingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private BookingFacade bookingFacade;

    @Autowired
    public UserController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @PostMapping("/new")
    public
    @ResponseBody
    User createUser(@RequestParam(required = true) String name,
                    @RequestParam(required = true) String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return bookingFacade.createUser(user);
    }

    @PutMapping("/edit/{id}")
    public
    @ResponseBody
    User updateUser(@PathVariable long id,
                    @RequestParam(required = true) String name,
                    @RequestParam(required = true) String email) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        return bookingFacade.updateUser(user);
    }

    @GetMapping("/{userId}")
    public
    @ResponseBody
    User getUserById(@PathVariable long userId) {
        return bookingFacade.getUserById(userId);
    }

    @GetMapping("/email/{email}")
    public
    @ResponseBody
    User getUserByEmail(@PathVariable String email) {
        return bookingFacade.getUserByEmail(email);
    }

    @GetMapping("/name/{name}")
    public
    @ResponseBody
    List<User> getUsersByName(@PathVariable String name,
                              @RequestParam(required = true) int pageSize,
                              @RequestParam(required = true) int pageNum) {
        return bookingFacade.getUsersByName(name, pageSize, pageNum);
    }

    @DeleteMapping("/{id}")
    public
    @ResponseBody
    boolean deleteUser(@PathVariable Long id) {
        return bookingFacade.deleteUser(id);
    }
}


