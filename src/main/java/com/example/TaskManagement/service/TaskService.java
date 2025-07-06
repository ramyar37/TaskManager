package com.example.TaskManagement.service;

import com.example.TaskManagement.entity.Task;

import java.util.List;

public interface TaskService {

    Task createTask(Task task);

    Task updateTask(Long id, Task task);

    void deleteTask(Long id);

    Task getTaskById(Long id);

    List<Task> getAllTasks();
}
