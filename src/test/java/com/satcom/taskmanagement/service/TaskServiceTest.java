package com.satcom.taskmanagement.service;

import com.satcom.taskmanagement.application.exception.TaskNotFoundException;
import com.satcom.taskmanagement.application.service.TaskService;
import com.satcom.taskmanagement.domain.model.Task;
import com.satcom.taskmanagement.domain.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void shouldCreateTask() {
        Task task = new Task("Learn Spring Boot", "Practice DDD", LocalDate.now().plusDays(14));

        when(taskRepository.save(task)).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        assertEquals(task.getId(), createdTask.getId());
        assertEquals("Learn Spring Boot", createdTask.getTitle());

        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void shouldReturnTaskById() {
        String taskId = "task-1";

        Task task = new Task("Learn Spring Boot", "Practice DDD", LocalDate.now().plusDays(14));

        when(taskRepository.getTaskById(taskId))
                .thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById(taskId);

        assertNotNull(foundTask);
        assertEquals(task.getTitle(), foundTask.getTitle());

        verify(taskRepository, times(1)).getTaskById(taskId);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        String taskId = "invalid-id";

        when(taskRepository.getTaskById(taskId))
                .thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTaskById(taskId)
        );

        verify(taskRepository, times(1)).getTaskById(taskId);
    }

    @Test
    void shouldReturnAllTasks() {
        Task task1 = new Task("Task 1", "Description 1", LocalDate.now().plusDays(14));
        Task task2 = new Task("Task 2", "Description 2", LocalDate.now().plusDays(14));

        List<Task> tasks = List.of(task1, task2);

        when(taskRepository.getAllTasks()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks(null, 0, 10);

        assertEquals(2, result.size());

        verify(taskRepository, times(1)).getAllTasks();
    }

    @Test
    void shouldDeleteTask() {
        String taskId = "task-1";

        when(taskRepository.getTaskById(taskId))
                .thenReturn(Optional.of(
                        new Task("Delete Task", "Delete description", LocalDate.now().plusDays(14))
                ));

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteTask(taskId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingTask() {
        String taskId = "invalid-id";

        when(taskRepository.getTaskById(taskId))
                .thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.deleteTask(taskId)
        );

        verify(taskRepository, never()).deleteTask(taskId);
    }
}
