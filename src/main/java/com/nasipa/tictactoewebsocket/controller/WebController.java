package com.nasipa.tictactoewebsocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/auth/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/auth/register")
    public String register() {
        return "register";
    }
} 