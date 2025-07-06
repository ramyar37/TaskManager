package com.example.TaskManagement.service;

import com.example.TaskManagement.entity.Project;
import com.example.TaskManagement.entity.Task;
import com.example.TaskManagement.entity.User;
import com.example.TaskManagement.repository.ProjectRepository;
import com.example.TaskManagement.repository.TaskRepository;
import com.example.TaskManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Task createTask(Task task) {


        User assignedTo = userRepository.findById(task.getAssignedTo().getId())
                .orElseThrow(() -> new RuntimeException("AssignedTo User not found"));
        User assignedBy = userRepository.findById(task.getAssignedBy().getId())
                .orElseThrow(() -> new RuntimeException("AssignedBy User not found"));
        Project project = projectRepository.findById(task.getProject().getId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        task.setAssignedTo(assignedTo);
        task.setAssignedBy(assignedBy);
        task.setProject(project);
        task.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        task.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {

        Task existing = taskRepository.findById(id).orElseThrow();
        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setStatus(task.getStatus());
        existing.setUpdatedAt(task.getUpdatedAt());

        //Fetch and set AssignedTo
        if(task.getAssignedTo() != null && task.getAssignedTo().getId()!=null){
            User assignedTo = userRepository.findById(task.getAssignedTo().getId())
                    .orElseThrow(() -> new RuntimeException("AssignedTo User not found"));
            existing.setAssignedTo(assignedTo);
        }

        //Fetch and set AssignBy
        if(task.getAssignedBy() != null && task.getAssignedBy().getId() != null){
            User assignedBy = userRepository.findById(task.getAssignedBy().getId())
                    .orElseThrow(() -> new RuntimeException("AssignedBy User not found"));
            existing.setAssignedBy(assignedBy);
        }

        //Fetch and set Project
        if(task.getProject() != null && task.getProject().getId() != null){
            Project project = projectRepository.findById(task.getProject().getId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            existing.setProject(project);
        }
        return taskRepository.save(existing);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
