package com.example.devSync.dto;

import java.time.LocalDateTime;

import com.example.devSync.enums.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TaskDTO {
    
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long projectId; //which project
    private Long assignedToId; //whom assigned
   
}
