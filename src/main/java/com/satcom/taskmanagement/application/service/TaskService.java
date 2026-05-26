package com.satcom.taskmanagement.application.service;

import com.satcom.taskmanagement.application.exception.TaskNotFoundException;
import com.satcom.taskmanagement.domain.model.Status;
import com.satcom.taskmanagement.domain.model.Task;
import com.satcom.taskmanagement.domain.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getTaskById(String id) {
        return taskRepository.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTask(String id, Task updatedTask) {

        Task existingTask = taskRepository.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setDueDate(updatedTask.getDueDate());

        return taskRepository.save(existingTask);
    }

    public void deleteTask(String id) {
        taskRepository.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskRepository.deleteTask(id);
    }

    public List<Task> getAllTasks(Status status, int page, int size) {
        List<Task> tasks = taskRepository.getAllTasks()
                .stream()
                .filter(task -> status == null || task.getStatus() == status)
                .sorted(Comparator.comparing(Task::getDueDate))
                .toList();


        int fromIndex = page * size;
        if (fromIndex >= tasks.size()) {
            return List.of();
        }

        int toIndex = Math.min(fromIndex + size, tasks.size());
        return tasks.subList(fromIndex, toIndex);
    }

}
