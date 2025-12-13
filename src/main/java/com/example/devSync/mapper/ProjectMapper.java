package com.example.devSync.mapper;


import com.example.devSync.dto.ProjectDTO;
import com.example.devSync.entity.Project;

public class ProjectMapper {

    //Entity to DTO
    public static ProjectDTO toProjectDTO (Project project){
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setTitle(project.getTitle());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setCreatedAt(project.getCreatedAt());
        projectDTO.setCreatedById(project.getCreatedBy().getId());

        return projectDTO;
        
    }


    //DTO to Entity
    public static Project toProjectEntity(ProjectDTO projectDTO){
        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setTitle(projectDTO.getTitle());
        project.setCreatedAt(projectDTO.getCreatedAt());
        project.setDescription(projectDTO.getDescription());
        return project;
        
    }
    
}
