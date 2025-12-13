package com.example.devSync.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devSync.dto.ProjectDTO;
import com.example.devSync.dto.UserDTO;

import com.example.devSync.service.ProjectService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/devSync/projects")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;

    //create project
    @Operation(
    summary = "Create a new project",
    description = "Creates a new project with the provided details."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ProjectDTO createProject(@RequestBody ProjectDTO projectDTO)
    {
        return projectService.createProject(projectDTO);

    }

    //get project by user
    @Operation(
    summary = "Get projects by User ID",
    description = "Fetches all projects created by a specific user."
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{id}")
    public List<ProjectDTO> getProjectByUser(@PathVariable Long id ){
        return projectService.getProjectByUser(id);
    }

    //get project by id
    @Operation(
    summary = "Get project by project ID",
    description = "Retrieves the details of a project using its ID."
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable Long id){
        return projectService.getProjectById(id);
    }

    //delete project
    @Operation(
    summary = "Delete a project",
    description = "Deletes a project permanently using its project ID."
    )
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable Long id){
        return projectService.deleteProject(id);
    }

    //add members to project
    @Operation(
    summary = "Add member to a project",
    description = "Adds a user as a member to a specific project."
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER' ,'DEVELOPER')")
   @PostMapping("/addMembers/{projectId}/{userId}")
   public String addMemberToProject(@PathVariable Long projectId, @PathVariable Long userId){
        return projectService.addMemberToProject(projectId, userId);
   }

   //get projects of a user
   @Operation(
    summary = "Get projects of a user",
    description = "Retrieves all projects where the user is a member."
    )
    @PreAuthorize("isAuthenticated()")
   @GetMapping("/projectsOfUser/{userId}")
    public List<ProjectDTO> getProjectsOfUser(@PathVariable Long userId){
          return projectService.getProjectsByMemberUser(userId);
    }

   //get members of a project
   @Operation(
    summary = "Get members of a project",
    description = "Fetches all members associated with a specific project."
    )
    @PreAuthorize("isAuthenticated()")
   @GetMapping("/members/{projectId}")
   public List<UserDTO> getMembersofProject(@PathVariable Long projectId){
        return projectService.getMembersofProject(projectId);
        
   }

   //remove member from project
   @Operation(
    summary = "Remove member from a project",
    description = "Removes a user from the members of a specific project."
   )
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    @DeleteMapping("/removeMember/{projectId}/{userId}")
    public String removeMemberFromProject(@PathVariable Long projectId, @PathVariable Long userId){
        return projectService.removeMemberFromProject(projectId, userId);
    }

}
