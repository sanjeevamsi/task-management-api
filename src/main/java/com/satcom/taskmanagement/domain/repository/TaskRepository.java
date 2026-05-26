package com.satcom.taskmanagement.domain.repository;


import com.satcom.taskmanagement.domain.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    Optional<Task> getTaskById(String id);
    void deleteTask(String id);
    List<Task> getAllTasks();
}
