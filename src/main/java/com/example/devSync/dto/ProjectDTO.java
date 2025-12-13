package com.example.devSync.dto;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;   // ADD THIS
    private Long createdById; //project manager Id 
    
}
