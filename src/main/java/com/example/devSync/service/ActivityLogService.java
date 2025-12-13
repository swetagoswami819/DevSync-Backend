package com.example.devSync.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.devSync.entity.ActivityLog;
import com.example.devSync.enums.ActionType;
import com.example.devSync.enums.EntityType;
import com.example.devSync.repository.ActivityLogRepository;

@Service
public class ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    public void logActivity(
            ActionType action,
            String description,
            EntityType entityType,
            Long entityId,
            String username
    ) {

        ActivityLog log = new ActivityLog();
        log.setActionType(action);
        log.setDescription(description);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setUsername(username);

        activityLogRepository.save(log);
    }

    public List<ActivityLog> getLogsByProjectId(Long ProjId){

        List<ActivityLog> log  = activityLogRepository.findByEntityIdAndEntityType(ProjId , EntityType.PROJECT);
        return log;
    }

    public List<ActivityLog> getLogsByTaskId(Long taskId){
        List<ActivityLog> log = activityLogRepository.findByEntityIdAndEntityType(taskId , EntityType.TASK);
        return log;
    }

    public List<ActivityLog> getLogsByUser(String username){
        List<ActivityLog> log = activityLogRepository.findByUsername(username);

        return log;
    }


}

