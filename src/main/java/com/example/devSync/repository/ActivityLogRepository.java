package com.example.devSync.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.devSync.entity.ActivityLog;
import com.example.devSync.enums.EntityType;

import java.util.List;


@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

   List<ActivityLog>findByEntityIdAndEntityType(Long id , EntityType entitytype);
   List<ActivityLog>findByUsername(String username);
    

   
} 