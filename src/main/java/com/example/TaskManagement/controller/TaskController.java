package com.example.TaskManagement.controller;

import com.example.TaskManagement.entity.Task;
import com.example.TaskManagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String hello(){

        return "Backend is up & running...";
    }

    //Create Task
    @PostMapping("/create")
    public Task createTask(@Valid @RequestBody Task task){
        return taskService.createTask(task);
    }

    //Update Task
    @PutMapping("/update/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task){
        return taskService.updateTask(id, task);
    }

    //Delete Task
    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return "Task deleted with id : " + id;
    }

    //Find Task
    @GetMapping("/find/{id}")
    public Task findTask(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    //Find All Tasks
    @GetMapping("/findAll/{id}")
    public List<Task> findAllTasks(){
       return taskService.getAllTasks();
    }

}


