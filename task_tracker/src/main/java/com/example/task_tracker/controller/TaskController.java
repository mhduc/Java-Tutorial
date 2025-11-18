package com.example.task_tracker.controller;

import com.example.task_tracker.exception.TaskOperationException;
import com.example.task_tracker.model.Task;
import com.example.task_tracker.service.TaskService;
import com.example.task_tracker.dto.CreateTaskDto;
import com.example.task_tracker.dto.UpdateTaskDto;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {
    
    private final TaskService taskService;
    
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    public void addTask(String description) {
        try {
            CreateTaskDto createTaskDto = new CreateTaskDto(description);
            Task task = taskService.createTask(createTaskDto);
            System.out.println("Task added successfully (ID: " + task.getId() + ")");
        } catch (TaskOperationException e) {
            System.err.println("Error adding task: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause().getMessage());
            }
        }
    }
    
    public void updateTask(int id, String description) {
        try {
            UpdateTaskDto updateTaskDto = new UpdateTaskDto(description);
            Optional<Task> updatedTask = taskService.updateTask(id, updateTaskDto);
            if (updatedTask.isPresent()) {
                System.out.println("Task updated successfully (ID: " + id + ")");
            } else {
                System.err.println("Task not found with ID: " + id);
            }
        } catch (TaskOperationException e) {
            System.err.println("Error updating task: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause().getMessage());
            }
        }
    }
    
    public void deleteTask(int id) {
        try {
            boolean deleted = taskService.deleteTask(id);
            if (deleted) {
                System.out.println("Task deleted successfully (ID: " + id + ")");
            } else {
                System.err.println("Task not found with ID: " + id);
            }
        } catch (TaskOperationException e) {
            System.err.println("Error deleting task: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause().getMessage());
            }
        }
    }
    
    public void markInProgress(int id) {
        try {
            Optional<Task> updatedTask = taskService.updateTaskStatus(id, "in_progress");
            if (updatedTask.isPresent()) {
                System.out.println("Task marked as in progress (ID: " + id + ")");
            } else {
                System.err.println("Task not found with ID: " + id);
            }
        } catch (TaskOperationException e) {
            System.err.println("Error updating task status: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause().getMessage());
            }
        }
    }
    
    public void markDone(int id) {
        try {
            Optional<Task> updatedTask = taskService.updateTaskStatus(id, "done");
            if (updatedTask.isPresent()) {
                System.out.println("Task marked as done (ID: " + id + ")");
            } else {
                System.err.println("Task not found with ID: " + id);
            }
        } catch (TaskOperationException e) {
            System.err.println("Error updating task status: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause().getMessage());
            }
        }
    }
    
    public void listAllTasks() {
        try {
            List<Task> tasks = taskService.getAllTasks();
            if (tasks.isEmpty()) {
                System.out.println("No tasks found");
            } else {
                System.out.println("All tasks:");
                tasks.forEach(this::printTask);
            }
        } catch (TaskOperationException e) {
            System.err.println("Error listing tasks: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause().getMessage());
            }
        }
    }
    
    public void listDoneTasks() {
        try {
            List<Task> tasks = taskService.getTasksByStatus("done");
            if (tasks.isEmpty()) {
                System.out.println("No done tasks found");
            } else {
                System.out.println("Done tasks:");
                tasks.forEach(this::printTask);
            }
        } catch (TaskOperationException e) {
            System.err.println("Error listing tasks: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause().getMessage());
            }
        }
    }
    
    public void listTodoTasks() {
        try {
            List<Task> tasks = taskService.getTasksByStatus("todo");
            if (tasks.isEmpty()) {
                System.out.println("No todo tasks found");
            } else {
                System.out.println("Todo tasks:");
                tasks.forEach(this::printTask);
            }
        } catch (TaskOperationException e) {
            System.err.println("Error listing tasks: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause().getMessage());
            }
        }
    }
    
    public void listInProgressTasks() {
        try {
            List<Task> tasks = taskService.getTasksByStatus("in_progress");
            if (tasks.isEmpty()) {
                System.out.println("No in-progress tasks found");
            } else {
                System.out.println("In-progress tasks:");
                tasks.forEach(this::printTask);
            }
        } catch (TaskOperationException e) {
            System.err.println("Error listing tasks: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause().getMessage());
            }
        }
    }
    
    private void printTask(Task task) {
        System.out.println("ID: " + task.getId() + " | Description: " + task.getDescription() + " | Status: " + task.getStatus());
    }
}