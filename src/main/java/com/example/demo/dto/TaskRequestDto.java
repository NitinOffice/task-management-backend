package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class TaskRequestDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Status is required")
    private String status;

    public TaskRequestDto() {
    }

    public TaskRequestDto(String title, String status) {
        this.title = title;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}