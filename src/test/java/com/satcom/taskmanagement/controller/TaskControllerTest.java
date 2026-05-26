package com.satcom.taskmanagement.controller;

import com.satcom.taskmanagement.application.exception.TaskNotFoundException;
import com.satcom.taskmanagement.application.service.TaskService;
import com.satcom.taskmanagement.domain.model.Task;
import com.satcom.taskmanagement.presentation.controller.TaskController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateTask() throws Exception {
        Task task = new Task(
                "Learn Spring Boot",
                "Practice REST APIs",
                LocalDate.of(2026, 6, 1)
        );

        Task createdTask = new Task(
                "Learn Spring Boot",
                "Practice REST APIs",
                LocalDate.of(2026, 6, 1)
        );

        when(taskService.createTask(any(Task.class)))
                .thenReturn(createdTask);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title")
                        .value("Learn Spring Boot"))
                .andExpect(jsonPath("$.description")
                        .value("Practice REST APIs"))
                .andExpect(jsonPath("$.status")
                        .value("PENDING"));
    }

    @Test
    void shouldGetTaskById() throws Exception {
        String taskId = "task-1";

        Task task = new Task(
                "Learn Testing",
                "JUnit and MockMvc",
                LocalDate.of(2026, 6, 10)
        );

        when(taskService.getTaskById(taskId))
                .thenReturn(task);

        mockMvc.perform(get("/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title")
                        .value("Learn Testing"))
                .andExpect(jsonPath("$.description")
                        .value("JUnit and MockMvc"))
                .andExpect(jsonPath("$.status")
                        .value("PENDING"));
    }

    @Test
    void shouldReturn404WhenTaskNotFound() throws Exception {
        String taskId = "invalid-id";

        when(taskService.getTaskById(taskId))
                .thenThrow(new TaskNotFoundException(taskId));

        mockMvc.perform(get("/tasks/{id}", taskId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllTasks() throws Exception {
        Task task1 = new Task(
                "Task 1",
                "Description 1",
                LocalDate.of(2026, 6, 1)
        );

        Task task2 = new Task(
                "Task 2",
                "Description 2",
                LocalDate.of(2026, 6, 5)
        );

        List<Task> tasks = List.of(task1, task2);

        when(taskService.getAllTasks(null, 0, 10))
                .thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].title").value("Task 2"));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        String taskId = "task-1";

        Task updatedTask = new Task(
                "Updated Title",
                "Updated Description",
                LocalDate.of(2026, 7, 1)
        );

        when(taskService.updateTask(eq(taskId), any(Task.class)))
                .thenReturn(updatedTask);

        mockMvc.perform(put("/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        String taskId = "task-1";

        doNothing().when(taskService).deleteTask(taskId);

        mockMvc.perform(delete("/tasks/{id}", taskId))
                .andExpect(status().isNoContent());
    }
}
