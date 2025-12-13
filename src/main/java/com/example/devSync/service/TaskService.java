package com.example.devSync.service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import com.example.devSync.dto.TaskDTO;
import com.example.devSync.entity.Project;
import com.example.devSync.entity.Task;
import com.example.devSync.entity.TaskStatusHistory;
import com.example.devSync.entity.User;
import com.example.devSync.enums.ActionType;
import com.example.devSync.enums.EntityType;
import com.example.devSync.enums.TaskStatus;
import com.example.devSync.mapper.TaskMapper;
import com.example.devSync.repository.ProjectRepository;
import com.example.devSync.repository.TaskRepository;
import com.example.devSync.repository.TaskStatusHistoryRepository;
import com.example.devSync.repository.UserRepository;


@Service
public class TaskService {

        @Autowired
        private TaskRepository taskRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ProjectRepository projectRepository;

        @Autowired
        private TaskStatusHistoryRepository taskStatusHistoryRepository;

        @Autowired
        private ActivityLogService activityLogService;

        @Autowired
        private EmailService mailService;

        // CREATE TASK
        public TaskDTO createTask(TaskDTO taskDTO) {

                Project project = projectRepository.findById(taskDTO.getProjectId())
                                .orElseThrow(() -> new RuntimeException("Project not found"));

                User assignedUser = userRepository.findById(taskDTO.getAssignedToId())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                Task task = TaskMapper.toTaskEntity(taskDTO);
                task.setProject(project);
                task.setAssignedTo(assignedUser);

                String username = assignedUser.getUsername();
                String email = assignedUser.getEmail();

                Task savedTask = taskRepository.save(task);
                Long id = task.getId();

                // activity log call
                activityLogService.logActivity(
                                ActionType.CREATE,
                                "task is  created and assigned",
                                EntityType.TASK,
                                id,
                                username);

                // send mail to the assigned user
                mailService.sendEmail(
                                email,
                                "New Task Assigned",
                                "You have been assigned a new task: " + task.getTitle());

                return TaskMapper.toTaskDTO(savedTask);
        }

        // GET TASKS BY PROJECT
        public List<TaskDTO> getTasksByProject(Long projectId) {

                Project project = projectRepository.findById(projectId)
                                .orElseThrow(() -> new RuntimeException("Project not found"));

                return taskRepository.findByProject(project)
                                .stream()
                                .map(TaskMapper::toTaskDTO)
                                .collect(Collectors.toList());
        }

        // GET TASKS BY USER
        public List<TaskDTO> getTasksByAssignedUser(Long userId) {

                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                return taskRepository.findByAssignedTo(user)
                                .stream()
                                .map(TaskMapper::toTaskDTO)
                                .collect(Collectors.toList());
        }

        // UPDATE TASK STATUS
        public TaskDTO updateTaskStatus(Long taskId, TaskStatus newStatus , Collection<? extends GrantedAuthority> authorities) throws AccessDeniedException {

                Task task = taskRepository.findById(taskId)
                                .orElseThrow(() -> new RuntimeException("Task not found"));

                TaskStatus oldStatus = task.getStatus();

                boolean isAdmin = authorities.stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"));

                boolean isPM = authorities.stream().anyMatch(a->a.getAuthority().equals("ROLE_PROJECT_MANAGER"));

                //Only Admin can move to completed
                if(newStatus==TaskStatus.COMPLETED && !isAdmin){
                        throw new AccessDeniedException("Only Admin can mark task as completed");
                }

                //Only Project manager can move to review
                if(newStatus==TaskStatus.REVIEW && !isPM){
                        throw new AccessDeniedException("Only project manager can mark task as review");
                }

                //other transitions are allowed for anyone(developers)
                task.setStatus(newStatus);
                task.setUpdatedAt(LocalDateTime.now());

                if (oldStatus == newStatus) {
                        throw new RuntimeException("Task is already in this status");
                }

                Task savedTask = taskRepository.save(task);
                TaskDTO returnedTask = TaskMapper.toTaskDTO(savedTask);

                User user = savedTask.getAssignedTo();
                String username = user.getUsername();
                String email = user.getEmail();

                // Save History
                TaskStatusHistory history = new TaskStatusHistory();
                history.setTaskId(taskId);
                history.setOldStatus(oldStatus);
                history.setNewStatus(newStatus);
                history.setChangedAt(LocalDateTime.now());

                taskStatusHistoryRepository.save(history);

                // activity log call
                activityLogService.logActivity(
                                ActionType.STATUS_CHANGE,
                                "status of task is changed from " + oldStatus + "to " + newStatus,
                                EntityType.TASK,
                                taskId,
                                username);

                // send mail

                mailService.sendEmail(
                                email,
                                "Task Status Updated",
                                "Status of your assigned task is changed to: " + newStatus
                        );

                return returnedTask;

        }

        // DELETE TASK
        public String deleteTask(Long taskId) {

                Task task = taskRepository.findById(taskId)
                                .orElseThrow(() -> new RuntimeException("Task not found"));

                taskRepository.deleteById(taskId);

                User user = task.getAssignedTo();
                String username = (user != null) ? user.getUsername() : "SYSTEM";

                // activity log call
                activityLogService.logActivity(
                                ActionType.DELETE,
                                "task deleted",
                                EntityType.TASK,
                                taskId,
                                username);

                return "Task deleted successfully";
        }
}
