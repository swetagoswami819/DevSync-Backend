package com.example.devSync.service;

import com.example.devSync.dto.UserDTO;
import com.example.devSync.dto.UserRegisterDTO;
import com.example.devSync.entity.User;
import com.example.devSync.mapper.UserMapper;
import com.example.devSync.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // CREATE USER
    public UserDTO createUser(UserRegisterDTO registerDTO) {

        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        // user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        user.setRole(registerDTO.getRole());

        User savedUser = userRepository.save(user);

        String email = savedUser.getEmail();

        mailService.sendEmail(
                email,
                "User Registered ",
                "user registered successfully now you can create projects and task and can use our system. Thankyou from devSync Team");
        return UserMapper.toUserDTO(savedUser);
    }

    // GET ALL USERS
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    // GET USER BY ID
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return UserMapper.toUserDTO(user);
    }

    // DELETE USER
    public String deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        String email = user.getEmail();
        userRepository.deleteById(id);

        mailService.sendEmail(
                email,
                "User deleted permanently",
                "User with id " + id + " has been deleted succesfully from devsync. Devsync Team");
        return "User deleted successfully";
    }
}
