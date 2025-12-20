package com.example.devSync.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")

public class Notification {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)

    private Long Id;   //(PK)
    private Long userId;    //(FK)
    private String message;
    private String type;    //task assigned , status changed
    private String entityType;  //task , project
    private Long entityId;

    private boolean isRead = false;
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
        
}
