package com.example.devSync.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devSync.dto.UserDTO;

import com.example.devSync.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/devSync/users")


public class UserController {
    @Autowired
    private UserService userService;

    // //create user
    // @Operation(
    // summary = "Register a new user",
    // description = "Registers a new user with the provided details."
    // )
    // @PostMapping("/register")
    // public UserDTO registerUser(@RequestBody UserRegisterDTO userRegisterDTO){
    //     return userService.createUser(userRegisterDTO);
    // }


    //get all users
    @Operation(
    summary = "Get all users",
    description = "Retrieves a list of all registered users."
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    //get user by id
    @Operation(
    summary = "Get user by ID",
    description = "Retrieves the details of a user using their ID."
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id ){
        return userService.getUserById(id);
    }
    
    //delete user
    @Operation(
    summary = "Delete a user",
    description = "Deletes a user permanently using their user ID."
    )

    //Now I am just adding isAutheticated but to ensure that the only user can delete himself we will add the otp matcher logic in future when we will implement frontend
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
