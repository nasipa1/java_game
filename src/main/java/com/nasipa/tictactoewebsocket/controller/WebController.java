package com.nasipa.tictactoewebsocket.controller;

import com.nasipa.tictactoewebsocket.model.dto.AuthResponseDto;
import com.nasipa.tictactoewebsocket.model.dto.UserLoginDto;
import com.nasipa.tictactoewebsocket.model.dto.UserRegistrationDto;
import com.nasipa.tictactoewebsocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

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
    
    @PostMapping("/auth/login")
    public String processLogin(@ModelAttribute UserLoginDto loginDto, HttpSession session) {
        AuthResponseDto response = userService.loginUser(loginDto);
        
        if (response.isSuccess()) {
            // Store username in session for server-side tracking
            session.setAttribute("username", loginDto.getUsername());
            return "redirect:/";  // Redirect to the menu/index page
        } else {
            // Login failed
            return "redirect:/auth/login?error=" + response.getMessage();
        }
    }
    
    @GetMapping("/auth/register")
    public String register() {
        return "register";
    }
    
    @PostMapping("/auth/register")
    public String processRegistration(@ModelAttribute UserRegistrationDto registrationDto) {
        AuthResponseDto response = userService.registerUser(registrationDto);
        
        if (response.isSuccess()) {
            return "redirect:/auth/login?registered=true";  // Redirect to the login page with success parameter
        } else {
            // Registration failed
            return "redirect:/auth/register?error=" + response.getMessage();
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Clear the session
        session.invalidate();
        return "redirect:/";
    }
} 