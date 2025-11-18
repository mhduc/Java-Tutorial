package com.example.task_tracker.repository;

import com.example.task_tracker.exception.TaskOperationException;
import com.example.task_tracker.model.Task;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TaskRepository {
    private static final String TASKS_FILE = "tasks.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public List<Task> findAll() throws IOException {
        if (!new File(TASKS_FILE).exists()) {
            return new ArrayList<>();
        }
        
        return objectMapper.readValue(new File(TASKS_FILE), new TypeReference<List<Task>>() {});
    }
    
    public Optional<Task> findById(int id) throws IOException {
        List<Task> tasks = findAll();
        return tasks.stream().filter(task -> task.getId() == id).findFirst();
    }
    
    public Task save(Task task) throws IOException {
        List<Task> tasks = findAll();
        
        // If task has no ID, assign a new one
        if (task.getId() == 0) {
            int maxId = tasks.stream().mapToInt(Task::getId).max().orElse(0);
            task.setId(maxId + 1);
        }
        
        // Remove existing task with same ID if exists
        tasks.removeIf(t -> t.getId() == task.getId());
        
        // Add the new/updated task
        tasks.add(task);
        
        // Save to file
        objectMapper.writeValue(new File(TASKS_FILE), tasks);
        
        return task;
    }
    
    public boolean deleteById(int id) throws IOException {
        List<Task> tasks = findAll();
        boolean removed = tasks.removeIf(task -> task.getId() == id);
        
        if (removed) {
            objectMapper.writeValue(new File(TASKS_FILE), tasks);
        }
        
        return removed;
    }
    
    public List<Task> findByStatus(String status) throws IOException {
        List<Task> tasks = findAll();
        return tasks.stream().filter(task -> status.equals(task.getStatus())).toList();
    }
}