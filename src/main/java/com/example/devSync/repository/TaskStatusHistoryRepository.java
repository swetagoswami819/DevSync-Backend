package com.example.devSync.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.devSync.entity.TaskStatusHistory;

@Repository
public interface TaskStatusHistoryRepository extends JpaRepository<TaskStatusHistory  , Long> {
    
    
}
