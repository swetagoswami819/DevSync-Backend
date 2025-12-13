package com.example.devSync.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentsDTO {
    
    public String content;
    public Long userId;
    public Long taskId;
}


