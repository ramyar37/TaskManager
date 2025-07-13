package com.example.TaskManagement.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleTestController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin(){
        return "Hello ADMIN!..";
    }

    @GetMapping("/manager")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public String manager(){
        return "Hello MANAGER (or ADMIN)!..";
    }

    @GetMapping("/employee")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    public String employee(){
        return "Hello EMPLOYEE (or higher)!..";
    }

    @GetMapping("/public")
    public String publicEndpoint(){
        return "This endpoint is open to everyone :)";
    }
}
