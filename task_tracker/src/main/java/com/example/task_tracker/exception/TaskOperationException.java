package com.example.task_tracker.exception;

public class TaskOperationException extends RuntimeException {
    public TaskOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}