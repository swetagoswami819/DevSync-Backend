package com.example.devSync.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devSync.entity.ActivityLog;
import com.example.devSync.service.ActivityLogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@Tag(name = "Activity Log APIs", description = "APIs for tracking all system activities")
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/devSync/logs")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;


    @Operation(
    summary = "Get project activity logs",
    description = "Fetches all activity logs related to a specific project using project ID"
)
    @PreAuthorize("hasAnyRole('ADMIN' , 'PROJECT_MANAGER')")
    @GetMapping("/project/{projId}")
    public List<ActivityLog> getLogsByProjectId(@PathVariable Long projId , Authentication authorities){

        return activityLogService.getLogsByProjectId(projId , authorities.getAuthorities());
    }


    @Operation(
    summary = "Get task activity logs",
    description = "Fetches all activity logs related to a specific task using task ID"
)
    @PreAuthorize("hasAnyRole('ADMIN','DEVELOPER')")
    @GetMapping("/task/{taskId}")
    public List<ActivityLog> getLogsByTaskId(@PathVariable Long taskId , Authentication authorities){
        return activityLogService.getLogsByTaskId(taskId , authorities.getAuthorities());
    }

    
    @Operation(
    summary = "Get user activity logs",
    description = "Fetches all logs performed by a specific user"
)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{username}")
    public List<ActivityLog> getLogsByUser(@PathVariable String username) {
        return activityLogService.getLogsByUser(username);
    }
    
}
