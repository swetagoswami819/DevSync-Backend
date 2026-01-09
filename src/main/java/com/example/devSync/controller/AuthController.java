package com.example.devSync.controller;

import com.example.devSync.dto.UserDTO;
import com.example.devSync.dto.UserRegisterDTO;
import com.example.devSync.entity.User;
import com.example.devSync.security.JWTUtil;

import com.example.devSync.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devSync/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService UserService;

    // REGISTER
    @Operation(summary = "Register a new user", description = "Registers a new user with the provided details.")
    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        return UserService.createUser(userRegisterDTO);
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        System.out.println(auth);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        // Extract roles from authorities
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
                
        String token = jwtUtil.generateToken(
                userDetails.getUsername(),
                roles);

        return token;
    }
}
