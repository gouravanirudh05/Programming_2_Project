package com.operatoroverloaded.hotel.indexcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // @GetMapping("")
    // public ModelAndView home() {
    //     ModelAndView mav=new ModelAndView("index");
    //     return mav;
    // }
    @GetMapping({"/", "/**"})
    public String home() {
        return "forward:/index.html";
    }
}