package com.example.qcm.qcm.Controller;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/")
public class controller {

    @GetMapping
    public String getHome() {
        return "home";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
