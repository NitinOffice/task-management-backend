package com.example.demo.service;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.dto.TaskResponseDto;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public TaskResponseDto createTask(
            TaskRequestDto request,
            String email) {

        User user = getUserFromEmail(email);

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setStatus(request.getStatus());
        task.setUserId(user.getId());

        Task savedTask = taskRepository.save(task);

        return mapToDto(savedTask);
    }

    public TaskResponseDto update(
            Long id,
            TaskRequestDto request,
            String email) {

        User user = getUserFromEmail(email);

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        if (!task.getUserId().equals(user.getId())) {
            throw new ResourceNotFoundException("Task not found");
        }

        task.setTitle(request.getTitle());
        task.setStatus(request.getStatus());

        Task updatedTask = taskRepository.save(task);

        return mapToDto(updatedTask);
    }

    public void delete(Long id, String email) {

        User user = getUserFromEmail(email);

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        if (!task.getUserId().equals(user.getId())) {
            throw new ResourceNotFoundException("Task not found");
        }

        taskRepository.delete(task);
    }

    public List<TaskResponseDto> getTasksByUser(String email) {

        User user = getUserFromEmail(email);

        return taskRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Page<TaskResponseDto> getTasksByUserPaginated(
            String email,
            int page,
            int size) {

        User user = getUserFromEmail(email);

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("title").ascending()
                );

        return taskRepository
                .findByUserId(user.getId(), pageable)
                .map(this::mapToDto);
    }

    public List<TaskResponseDto> filterByStatusForUser(
            String email,
            String status) {

        User user = getUserFromEmail(email);

        return taskRepository
                .findByUserIdAndStatus(user.getId(), status)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDto> searchByTitleForUser(
            String email,
            String title) {

        User user = getUserFromEmail(email);

        return taskRepository
                .findByUserIdAndTitleContainingIgnoreCase(
                        user.getId(),
                        title
                )
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private User getUserFromEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    private TaskResponseDto mapToDto(Task task) {

        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getStatus()
        );
    }
}