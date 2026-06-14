package com.example.demo.repository;

import com.example.demo.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByStatus(String status);
    List<Task> findByTitleContainingIgnoreCase(String title);
    Page<Task> findByUserId(Long userId, Pageable pageable);
    List<Task> findByUserIdAndStatus(Long userId, String status);
    List<Task> findByUserIdAndTitleContainingIgnoreCase(Long userId, String title);


}