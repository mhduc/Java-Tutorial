package com.example.expense_tracker.exception;

public class ExpenseOperationException extends RuntimeException {
    public ExpenseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}