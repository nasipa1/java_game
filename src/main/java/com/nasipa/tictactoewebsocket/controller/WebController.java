package com.nasipa.tictactoewebsocket.controller;

import com.nasipa.tictactoewebsocket.model.dto.AuthResponseDto;
import com.nasipa.tictactoewebsocket.model.dto.UserRegistrationDto;
import com.nasipa.tictactoewebsocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {
    
    private final UserService userService;
    
    @Autowired
    public WebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/auth/register")
    public String register() {
        return "register";
    }
    
    @PostMapping("/auth/register")
    public String processRegistration(@ModelAttribute UserRegistrationDto registrationDto) {
        AuthResponseDto response = userService.registerUser(registrationDto);
        
        if (response.isSuccess()) {
            return "redirect:/";  // Redirect to the menu/index page
        } else {
            // Registration failed
            return "redirect:/auth/register?error=" + response.getMessage();
        }
    }
} 