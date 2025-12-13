package com.example.devSync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.devSync.entity.Comments;

@Repository
public interface CommentRepository extends JpaRepository<Comments , Long>{
    List<Comments> findByTaskId(Long taskId);
    
}
