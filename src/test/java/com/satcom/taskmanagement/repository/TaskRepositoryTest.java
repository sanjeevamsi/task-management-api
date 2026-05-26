package com.satcom.taskmanagement.repository;

import com.satcom.taskmanagement.domain.model.Task;
import com.satcom.taskmanagement.domain.repository.TaskRepository;
import com.satcom.taskmanagement.infrastructure.repository.InMemoryTaskRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskRepositoryTest {

    @Test
    public void shouldSaveTask() {
        TaskRepository repository = new InMemoryTaskRepository();

        Task task = new Task(
                "Test Task",
                "This is a test task.",
                LocalDate.now().plusDays(7)
        );

        repository.save(task);

        assertTrue(repository.getTaskById(task.getId()).isPresent());
    }

    @Test
    public void shouldReturnEmptyWhenTaskNotFound() {
        TaskRepository repository = new InMemoryTaskRepository();

        Optional<Task> task = repository.getTaskById("non-existent-id");

        assertTrue(task.isEmpty());
    }

    @Test
    public void shouldDeleteTask() {
        TaskRepository repository = new InMemoryTaskRepository();

        Task task = new Task(
                "Test Task",
                "This is a test task.",
                LocalDate.now().plusDays(7)
        );

        repository.save(task);
        repository.deleteTask(task.getId());

        assertTrue(repository.getTaskById(task.getId()).isEmpty());
    }

    @Test
    public void shouldReturnAllTasks() {
        TaskRepository repository = new InMemoryTaskRepository();

        Task task1 = new Task(
                "Test Task 1",
                "This is the first test task.",
                LocalDate.now().plusDays(7)
        );

        Task task2 = new Task(
                "Test Task 2",
                "This is the second test task.",
                LocalDate.now().plusDays(14)
        );

        repository.save(task1);
        repository.save(task2);

        assertEquals(2, repository.getAllTasks().size());
    }
}
