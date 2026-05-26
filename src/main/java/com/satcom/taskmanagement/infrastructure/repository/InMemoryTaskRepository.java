package com.satcom.taskmanagement.infrastructure.repository;


import com.satcom.taskmanagement.domain.model.Task;
import com.satcom.taskmanagement.domain.repository.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final Map<String, Task> taskMap = new ConcurrentHashMap<>();

    @Override
    public Task save(Task task) {
        taskMap.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> getTaskById(String id) {
        return Optional.ofNullable(taskMap.get(id));
    }

    @Override
    public void deleteTask(String id) {
        taskMap.remove(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }
}
