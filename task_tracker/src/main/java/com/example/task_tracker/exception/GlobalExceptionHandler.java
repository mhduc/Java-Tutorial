package com.example.task_tracker.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public void handleTaskNotFoundException(TaskNotFoundException e) {
        System.err.println("Task not found: " + e.getMessage());
    }

    @ExceptionHandler(TaskOperationException.class)
    public void handleTaskOperationException(TaskOperationException e) {
        System.err.println("Task operation error: " + e.getMessage());
        if (e.getCause() != null) {
            System.err.println("Caused by: " + e.getCause().getMessage());
        }
    }

    @ExceptionHandler(NumberFormatException.class)
    public void handleNumberFormatException(NumberFormatException e) {
        System.err.println("Invalid number format. Please provide a valid task ID.");
    }

    @ExceptionHandler(Exception.class)
    public void handleGenericException(Exception e) {
        System.err.println("An unexpected error occurred: " + e.getMessage());
        e.printStackTrace();
    }
}