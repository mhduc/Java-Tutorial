package com.example.task_tracker.service;

import com.example.task_tracker.exception.TaskNotFoundException;
import com.example.task_tracker.exception.TaskOperationException;
import com.example.task_tracker.model.Task;
import com.example.task_tracker.repository.TaskRepository;
import com.example.task_tracker.dto.CreateTaskDto;
import com.example.task_tracker.dto.UpdateTaskDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    public Task createTask(CreateTaskDto createTaskDto) throws TaskOperationException {
        try {
            Task task = new Task();
            task.setDescription(createTaskDto.getDescription());
            task.setStatus("todo"); // Default status
            return taskRepository.save(task);
        } catch (IOException e) {
            throw new TaskOperationException("Failed to create task", e);
        }
    }
    
    public Optional<Task> getTaskById(int id) throws TaskOperationException {
        try {
            return taskRepository.findById(id);
        } catch (IOException e) {
            throw new TaskOperationException("Failed to retrieve task with ID: " + id, e);
        }
    }
    
    public List<Task> getAllTasks() throws TaskOperationException {
        try {
            return taskRepository.findAll();
        } catch (IOException e) {
            throw new TaskOperationException("Failed to retrieve tasks", e);
        }
    }
    
    public List<Task> getTasksByStatus(String status) throws TaskOperationException {
        try {
            return taskRepository.findByStatus(status);
        } catch (IOException e) {
            throw new TaskOperationException("Failed to retrieve tasks with status: " + status, e);
        }
    }
    
    public Optional<Task> updateTask(int id, UpdateTaskDto updateTaskDto) throws TaskOperationException {
        try {
            Optional<Task> existingTask = taskRepository.findById(id);
            if (existingTask.isPresent()) {
                Task task = existingTask.get();
                task.setDescription(updateTaskDto.getDescription());
                return Optional.of(taskRepository.save(task));
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new TaskOperationException("Failed to update task with ID: " + id, e);
        }
    }
    
    public Optional<Task> updateTaskStatus(int id, String status) throws TaskOperationException {
        try {
            Optional<Task> existingTask = taskRepository.findById(id);
            if (existingTask.isPresent()) {
                Task task = existingTask.get();
                task.setStatus(status);
                return Optional.of(taskRepository.save(task));
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new TaskOperationException("Failed to update task status for ID: " + id, e);
        }
    }
    
    public boolean deleteTask(int id) throws TaskOperationException {
        try {
            return taskRepository.deleteById(id);
        } catch (IOException e) {
            throw new TaskOperationException("Failed to delete task with ID: " + id, e);
        }
    }
}