package com.nasipa.tictactoewebsocket.service;

import com.nasipa.tictactoewebsocket.model.dto.AuthResponseDto;
import com.nasipa.tictactoewebsocket.model.dto.UserLoginDto;
import com.nasipa.tictactoewebsocket.model.dto.UserRegistrationDto;
import com.nasipa.tictactoewebsocket.model.entity.User;
import com.nasipa.tictactoewebsocket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDto registerUser(UserRegistrationDto registrationDto) {
        // Check if username already exists
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            return new AuthResponseDto(registrationDto.getUsername(), "Username already exists", false);
        }
        
        // Check if email already exists
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            return new AuthResponseDto(registrationDto.getUsername(), "Email already exists", false);
        }
        
        // Create new user
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEmail(registrationDto.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        
        userRepository.save(user);
        
        return new AuthResponseDto(user.getUsername(), "User registered successfully", true);
    }
    
    public AuthResponseDto loginUser(UserLoginDto loginDto) {
        Optional<User> userOptional = userRepository.findByUsername(loginDto.getUsername());
        
        if (userOptional.isEmpty()) {
            return new AuthResponseDto(loginDto.getUsername(), "User not found", false);
        }
        
        User user = userOptional.get();
        
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return new AuthResponseDto(loginDto.getUsername(), "Invalid password", false);
        }
        
        // Update last login time
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        
        return new AuthResponseDto(user.getUsername(), "Login successful", true);
    }
} 