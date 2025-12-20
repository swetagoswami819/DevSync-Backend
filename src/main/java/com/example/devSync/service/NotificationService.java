package com.example.devSync.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


import com.example.devSync.entity.Notification;
import com.example.devSync.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private NotificationRepository notificationRepository;

    public void notifyUser(Long userId , Notification notification){
        //save notification to DB
        notificationRepository.save(notification);

        //send relatime notification
        messagingTemplate.convertAndSendToUser(
            userId.toString(),
            "/queue/notifications",
            notification
        );
    }

    public String markAsRead(Long notificationId , Long userId){
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found"));

        // if(!notification.getUserId().equals(userId)){
        //     throw new RuntimeException("Access Denied");
        // }

        notification.setRead(true);
        notificationRepository.save(notification);
        return "Notification marked as read";
    }

}
