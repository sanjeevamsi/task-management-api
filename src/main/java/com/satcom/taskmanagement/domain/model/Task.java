package com.satcom.taskmanagement.domain.model;

import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;
import java.util.UUID;

public class Task {
    private final String id;
    private String title;
    private String description;
    private Status status;
    @FutureOrPresent
    private LocalDate dueDate;

    public Task(String title, String description, LocalDate dueDate) {
        validateTitle(title);

        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.status = Status.PENDING;
        this.dueDate = dueDate;
    }

    public String getId() {
        return id;
    }

    public void markInProgress() {
        this.status = Status.IN_PROGRESS;
    }

    public void markCompleted() {
        this.status = Status.DONE;
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
