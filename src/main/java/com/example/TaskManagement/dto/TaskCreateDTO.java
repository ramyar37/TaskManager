package com.example.TaskManagement.dto;

import jakarta.validation.constraints.NotBlank;

public class TaskCreateDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String status
}
