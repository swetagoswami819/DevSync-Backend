package com.example.devSync.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devSync.entity.Notification;
import com.example.devSync.entity.User;
import com.example.devSync.repository.NotificationRepository;
import com.example.devSync.repository.UserRepository;
import com.example.devSync.service.NotificationService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/devSync/notification")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @PutMapping("/markasread/{notificationId}")    
    public String markAsRead(@PathVariable Long notificationId , @AuthenticationPrincipal UserDetails user){

        User userdb = userRepository.findByUsername(user.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));
        return notificationService.markAsRead(notificationId, userdb.getId());
        
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications(@AuthenticationPrincipal UserDetails user){

        User userdb = userRepository.findByUsername(user.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

        return notificationRepository.findByUserIdAndIsReadFalse(userdb.getId());
        
    }
    
}
