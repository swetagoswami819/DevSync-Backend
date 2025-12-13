package com.example.devSync.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.devSync.dto.CommentsDTO;
import com.example.devSync.entity.Comments;
import com.example.devSync.entity.Task;
import com.example.devSync.entity.User;
import com.example.devSync.mapper.CommentsMapper;

import com.example.devSync.repository.CommentRepository;
import com.example.devSync.repository.TaskRepository;
import com.example.devSync.repository.UserRepository;

@Service
public class CommentsService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

public CommentsDTO addComment(CommentsDTO req) {

    User user = userRepository.findById(req.userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    Task task = taskRepository.findById(req.taskId)
        .orElseThrow(() -> new RuntimeException("Task not found"));

    Comments c = new Comments();
    c.setContent(req.content);
    c.setUser(user);
    c.setTask(task);

    Comments savedcomment  = commentRepository.save(c);
    CommentsDTO returnCommentsDTO = CommentsMapper.toDto(savedcomment);
    return returnCommentsDTO;  

    
}

    public List<CommentsDTO> getCommentsByTask(Long taskId){
        return commentRepository.findByTaskId(taskId)
        .stream()
        .map(CommentsMapper::toDto)
        .collect(Collectors.toList());    
    }   
}
