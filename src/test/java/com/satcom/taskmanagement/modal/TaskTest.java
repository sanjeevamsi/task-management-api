package com.satcom.taskmanagement.modal;

import com.satcom.taskmanagement.domain.model.Status;
import com.satcom.taskmanagement.domain.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testTaskCreation() {
        Task task = new Task(
                "Test Task",
                "This is a test task.",
                LocalDate.now().plusDays(7)
        );

        assertNotNull(task.getId());
        assertEquals("Test Task", task.getTitle());
        assertEquals("This is a test task.", task.getDescription());
        assertEquals(Status.PENDING, task.getStatus());
        assertEquals(LocalDate.now().plusDays(7), task.getDueDate());
    }

    @Test
    void shouldThrowExceptionWhenTitleIsBlank() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Task("  ", "Description", LocalDate.now().plusDays(7))
        );
    }

    @Test
    void shouldMarkTaskAsInProgress() {
        Task task = new Task("Test Task", "Description", LocalDate.now().plusDays(7));

        task.markInProgress();

        assertEquals(Status.IN_PROGRESS, task.getStatus());
    }

}
