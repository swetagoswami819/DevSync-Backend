package com.example.devSync.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.devSync.dto.ProjectDTO;
import com.example.devSync.dto.UserDTO;
import com.example.devSync.entity.Project;
import com.example.devSync.entity.User;
import com.example.devSync.enums.ActionType;
import com.example.devSync.enums.EntityType;
import com.example.devSync.mapper.ProjectMapper;
import com.example.devSync.mapper.UserMapper;
import com.example.devSync.repository.ProjectRepository;
import com.example.devSync.repository.UserRepository;


@Service
public class ProjectService {

        @Autowired
        private ProjectRepository projectRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ActivityLogService activityLogService;

        @Autowired
        private EmailService mailService;

        // create Project
        public ProjectDTO createProject(ProjectDTO dto) {

                User user = userRepository.findById(dto.getCreatedById())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                Project project = ProjectMapper.toProjectEntity(dto);

                project.setCreatedBy(user);

                Project saved = projectRepository.save(project);

                // activity log call
                activityLogService.logActivity(
                                ActionType.CREATE,
                                "New project created",
                                EntityType.PROJECT,
                                saved.getId(),
                                user.getUsername());

                return ProjectMapper.toProjectDTO(saved);
        }

        // get project By User
        public List<ProjectDTO> getProjectByUser(Long userId) {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found with given id"));

                return projectRepository.findByCreatedBy(user)
                                .stream()
                                .map(ProjectMapper::toProjectDTO)
                                .collect(Collectors.toList());

        }

        // get project by id
        public ProjectDTO getProjectById(Long id) {

                Project project = projectRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Project with given id not found"));

                return ProjectMapper.toProjectDTO(project);
        }

        // delete project by id
        public String deleteProject(Long id) {

                Project project = projectRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Project with given id not found"));

                User owner = project.getCreatedBy();
                String username = owner.getUsername();

                // activity log call
                activityLogService.logActivity(
                                ActionType.DELETE,
                                " project deleted",
                                EntityType.PROJECT,
                                id,
                                username

                );

                return " successfully deleted By Id";
        }

        // add members to project
        public String addMemberToProject(Long projectId, Long userId) {

                Project project = projectRepository.findById(projectId)
                                .orElseThrow(() -> new RuntimeException("Project not found"));

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                if (project.getMembers().contains(user)) {
                        throw new RuntimeException("User already a member of this project");
                }

                project.getMembers().add(user);
                projectRepository.save(project);

                String username = user.getUsername();
                String email = user.getEmail();

                // activity log call
                activityLogService.logActivity(
                                ActionType.ADD_MEMBER,
                                "new member is added to the project",
                                EntityType.PROJECT,
                                projectId,
                                username);
                
                //send mail to the new member
                mailService.sendEmail(
                        email,
                        "added to the project",
                        "You are receiving this email becuase you are added as a member in the project with project id "+projectId+" in devSync. Thankyou devSync Team."

                );

                return "Member added successfully to the project";
        }

        // get projects of a user
        public List<ProjectDTO> getProjectsByMemberUser(Long userId) {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                List<Project> projList = new ArrayList<>(user.getJoinProjects());
                return projList
                                .stream()
                                .map(ProjectMapper::toProjectDTO)
                                .toList();

        }

        // get all members of a project
        public List<UserDTO> getMembersofProject(Long projectId) {
                Project project = projectRepository.findById(projectId)
                                .orElseThrow(() -> new RuntimeException("Project not found"));

                List<User> usersList = new ArrayList<>(project.getMembers());
                return usersList
                                .stream()
                                .map(UserMapper::toUserDTO)
                                .toList();
        }

        // remove member from project
        public String removeMemberFromProject(Long projectId, Long userId) {

                Project project = projectRepository.findById(projectId)
                                .orElseThrow(() -> new RuntimeException("Project not found"));

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                // if (!project.getCreatedBy().getId().equals(userId)) {
                // throw new RuntimeException("Only project owner can remove members");
                // }
                project.getMembers().remove(user);
                projectRepository.save(project);

                String username = user.getUsername();
                String email = user.getEmail();

                // activity log call
                activityLogService.logActivity(
                                ActionType.REMOVE_MEMBER,
                                "remove a member from the project",
                                EntityType.PROJECT,
                                projectId,
                                username);

                //send mail to the member 
                mailService.sendEmail(
                        email, 
                        "Member removed",
                        "You are receiving this mail becuase you are removed from the project with project Id  "+projectId+ " as a member. Thankyou DevSync Team." );

                return "Member removed successfully from the project";
        }

}