package com.example.devSync.mapper;

import com.example.devSync.dto.TaskDTO;
import com.example.devSync.entity.Task;

public class TaskMapper {

    // Entity → DTO
    public static TaskDTO toTaskDTO(Task task){
        if(task == null) return null;

        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());

        if(task.getProject() != null){
            dto.setProjectId(task.getProject().getId());
        }

        if(task.getAssignedTo() != null){
            dto.setAssignedToId(task.getAssignedTo().getId());
        }

        return dto;
    }

    // DTO → Entity
    public static Task toTaskEntity(TaskDTO dto){
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setCreatedAt(dto.getCreatedAt());
        task.setUpdatedAt(dto.getUpdatedAt());
        return task;
    }
}
