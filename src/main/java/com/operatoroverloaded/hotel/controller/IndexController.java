package com.operatoroverloaded.hotel.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Profile("gui")
public class IndexController {

    // @GetMapping("")
    // public ModelAndView home() {
    //     ModelAndView mav=new ModelAndView("index");
    //     return mav;
    // }
    @GetMapping({"/", "/{path:^(?!api).*}/**"})
    public String home() {
        return "forward:/index.html";
    }
}