package com.example.devSync.mapper;

import com.example.devSync.dto.CommentsDTO;
import com.example.devSync.entity.Comments;

public class CommentsMapper {
  

    // Entity -> DTO
    public static CommentsDTO toDto(Comments comment) {
        CommentsDTO dto = new CommentsDTO();
        dto.setContent(comment.getContent());
        dto.setTaskId(comment.getTask().getId());
        dto.setUserId(comment.getUser().getId());
        return dto;
    }

}

    

