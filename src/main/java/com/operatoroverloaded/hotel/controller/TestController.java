package com.operatoroverloaded.hotel.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@Profile("gui")
@RequestMapping("/api")
public class TestController {

    // @GetMapping("")
    // public ModelAndView home() {
    //     ModelAndView mav=new ModelAndView("index");
    //     return mav;
    // }
    @GetMapping({"/ping"})
    public String home() {
        return "Ping Succesfful - Current Date and Time is " + System.currentTimeMillis();
    }
}