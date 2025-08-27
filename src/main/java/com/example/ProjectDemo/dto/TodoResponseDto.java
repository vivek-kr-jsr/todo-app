package com.example.ProjectDemo.dto;

import com.example.ProjectDemo.model.Status;

public class TodoResponseDto {

    private Long id;
    private String title;
    private Status status;

    public TodoResponseDto(Long id, String title, Status status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Status getStatus() {
        return status;
    }
}
