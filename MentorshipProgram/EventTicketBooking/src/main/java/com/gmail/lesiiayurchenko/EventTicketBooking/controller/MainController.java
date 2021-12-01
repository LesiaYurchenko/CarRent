package com.gmail.lesiiayurchenko.EventTicketBooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping ("/home")
    public String home(){
        return "index";
    }

    @GetMapping ("/error")
    public String error(){
        return "error";
    }
}