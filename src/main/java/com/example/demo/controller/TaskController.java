package com.example.demo.controller;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public TaskResponseDto create(
            @Valid @RequestBody TaskRequestDto request) {

        return taskService.createTask(
                request,
                getLoggedInEmail()
        );
    }

    @GetMapping
    public List<TaskResponseDto> getTasks() {
        return taskService.getTasksByUser(
                getLoggedInEmail()
        );
    }

    @PutMapping("/{id}")
    public TaskResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDto request) {

        return taskService.update(
                id,
                request,
                getLoggedInEmail()
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(
                id,
                getLoggedInEmail()
        );
    }

    @GetMapping("/page")
    public Page<TaskResponseDto> getTasksPaginated(
            @RequestParam int page,
            @RequestParam int size) {

        return taskService.getTasksByUserPaginated(
                getLoggedInEmail(),
                page,
                size
        );
    }

    @GetMapping("/filter")
    public List<TaskResponseDto> filterByStatus(
            @RequestParam String status) {

        return taskService.filterByStatusForUser(
                getLoggedInEmail(),
                status
        );
    }

    @GetMapping("/search")
    public List<TaskResponseDto> searchByTitle(
            @RequestParam String title) {

        return taskService.searchByTitleForUser(
                getLoggedInEmail(),
                title
        );
    }

    private String getLoggedInEmail() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        return auth.getName();
    }
}