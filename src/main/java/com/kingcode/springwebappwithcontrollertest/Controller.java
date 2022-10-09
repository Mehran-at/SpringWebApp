package com.kingcode.springwebappwithcontrollertest;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/")
    String getHome() {
        return "home";
    }
}
