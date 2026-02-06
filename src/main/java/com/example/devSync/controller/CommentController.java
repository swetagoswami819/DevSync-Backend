package com.example.devSync.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devSync.dto.CommentsDTO;

import com.example.devSync.service.CommentsService;



@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/devSync/comment")
public class CommentController {

    @Autowired
    private CommentsService commentsService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addcomment")
    public CommentsDTO addComment(@RequestBody CommentsDTO comment){
        return commentsService.addComment(comment);

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getcomments/{taskId}")
    public List<CommentsDTO> getCommentsByTask(@PathVariable Long taskId){
        return commentsService.getCommentsByTask(taskId);
    }
    
}
