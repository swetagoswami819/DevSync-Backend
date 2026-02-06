package com.example.devSync.service;

import java.nio.file.AccessDeniedException;
import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.devSync.entity.ActivityLog;
import com.example.devSync.entity.Project;
import com.example.devSync.entity.Task;
import com.example.devSync.enums.ActionType;
import com.example.devSync.enums.EntityType;
import com.example.devSync.repository.ActivityLogRepository;
import com.example.devSync.repository.ProjectRepository;
import com.example.devSync.repository.TaskRepository;
import com.example.devSync.repository.UserRepository;
import com.example.devSync.security.UserPrincipal;

@Service
public class ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public void logActivity(
            ActionType action,
            String description,
            EntityType entityType,
            Long entityId,
            String username) {

        ActivityLog log = new ActivityLog();
        log.setActionType(action);
        log.setDescription(description);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setUsername(username);

        activityLogRepository.save(log);
    }

    //get logs by project Id
    public List<ActivityLog> getLogsByProjectId(Long ProjId, Collection<? extends GrantedAuthority> authorities) {

        boolean isPM = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_PROJECT_MANAGER"));

        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        //Project must exist
        Project project = projectRepository.findById(ProjId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        //PM can only access own projects
        if (isPM && !project.getCreatedBy().getId().equals(principal.getId())) {
            throw new RuntimeException("Project does not belongs to you" + principal.getId() + " " +  project.getCreatedBy());
        }
    

    List<ActivityLog> log = activityLogRepository.findByEntityIdAndEntityType(ProjId, EntityType.PROJECT);
    return log;
    }


    //get Logs By TaskId
    public List<ActivityLog> getLogsByTaskId(Long taskId , Collection<? extends GrantedAuthority> authorities) {

        boolean isDev = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_DEVELOPER"));

        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Task task = taskRepository.findById(taskId)
        .orElseThrow(()-> new RuntimeException("Task Id does not exist"));

        if(isDev && !task.getAssignedTo().getId().equals(principal.getId())){
            throw new RuntimeException("The Task is not assigned to u");
        }
        
        List<ActivityLog> log = activityLogRepository.findByEntityIdAndEntityType(taskId, EntityType.TASK);
        return log;
    }

    //get logs by User
    public List<ActivityLog> getLogsByUser(String username) {
        List<ActivityLog> log = activityLogRepository.findByUsername(username);

        return log;
    }

}
