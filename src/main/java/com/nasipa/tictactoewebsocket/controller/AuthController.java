package com.nasipa.tictactoewebsocket.controller;

import com.nasipa.tictactoewebsocket.model.dto.AuthResponseDto;
import com.nasipa.tictactoewebsocket.model.dto.UserLoginDto;
import com.nasipa.tictactoewebsocket.model.dto.UserRegistrationDto;
import com.nasipa.tictactoewebsocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody UserRegistrationDto registrationDto) {
        AuthResponseDto response = userService.registerUser(registrationDto);
        
        HttpStatus status = response.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserLoginDto loginDto) {
        AuthResponseDto response = userService.loginUser(loginDto);
        
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return new ResponseEntity<>(response, status);
    }
} 