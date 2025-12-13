package com.example.devSync.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.devSync.entity.Project;
import com.example.devSync.entity.Task;
import com.example.devSync.entity.User;

@Repository
public interface TaskRepository extends JpaRepository<Task , Long>{

    List<Task> findByAssignedTo(User user);
    List<Task> findByProject(Project project);
     
}
