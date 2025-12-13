package com.example.devSync.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

import java.util.List;
import java.util.Optional;

import com.example.devSync.dto.TaskDTO;
import com.example.devSync.entity.TaskStatusHistory;
import com.example.devSync.enums.TaskStatus;
import com.example.devSync.repository.TaskStatusHistoryRepository;
import com.example.devSync.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/devSync/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskStatusHistoryRepository taskStatusHistoryRepository;

    //create task
    @Operation(
    summary = "Create a new task",
    description = "Creates a new task under a project and assigns it to a user."
)
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER' ,'DEVELOPER')")
    @PostMapping("/createTask")
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO){
        return taskService.createTask(taskDTO);
    }

    //get tasks by project ID
    @Operation(
    summary = "Get tasks by Project ID",
    description = "Fetches all tasks associated with a specific project."
)

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/taskByProjectId/{projectId}")
    public List<TaskDTO> getTasksByProject(@PathVariable Long projectId){
        return taskService.getTasksByProject(projectId);
    }

    //get taks by  user
   
    @Operation(
    summary = "Get tasks assigned to a user",
    description = "Retrieves all tasks assigned to a specific user."
)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/taskByUserId/{userId}")
    public List<TaskDTO> getTasksByUser(@PathVariable Long userId){
        return taskService.getTasksByAssignedUser(userId);
    }

    //update task status
    @Operation(
    summary = "Update task status",
    description = "Updates the current status of a task using its ID."
)
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER' ,'DEVELOPER')")
    @PutMapping("/updateStatus/{id}/status")
    public ResponseEntity<TaskDTO> updateTaskStatus(@PathVariable Long id , @RequestParam TaskStatus newStatus , Authentication authorities) throws AccessDeniedException{
        return ResponseEntity.ok(taskService.updateTaskStatus(id, newStatus , authorities.getAuthorities()));


    }
    
    //get task history
    @Operation(
    summary = "Get task status history",
    description = "Retrieves the status change history of a specific task using its ID."
)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/statusHistory/{id}")
    public Optional<TaskStatusHistory> getTaskStatusHistory(@PathVariable Long id){
        return taskStatusHistoryRepository.findById(id);
    }
    
    //delete task
    @Operation(
    summary = "Delete a task",
    description = "Deletes a task permanently using its task ID."
)
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER' )")
    @DeleteMapping("/{taskId}")
    public String deleteTask(@PathVariable Long taskId){
        return taskService.deleteTask(taskId);
    }
   
}
