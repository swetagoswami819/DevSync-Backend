package com.example.devSync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.devSync.entity.Project;
import com.example.devSync.entity.User;

@Repository
public interface ProjectRepository extends JpaRepository<Project , Long> {
    boolean existsByTitle(String title);
    List<Project> findByCreatedBy(User user);

     
}
