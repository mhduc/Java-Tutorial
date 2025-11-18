package com.example.expense_tracker.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ExpenseNotFoundException.class)
    public void handleExpenseNotFoundException(ExpenseNotFoundException e) {
        System.err.println("Expense not found: " + e.getMessage());
    }

    @ExceptionHandler(ExpenseOperationException.class)
    public void handleExpenseOperationException(ExpenseOperationException e) {
        System.err.println("Expense operation error: " + e.getMessage());
        if (e.getCause() != null) {
            System.err.println("Caused by: " + e.getCause().getMessage());
        }
    }

    @ExceptionHandler(NumberFormatException.class)
    public void handleNumberFormatException(NumberFormatException e) {
        System.err.println("Invalid number format.");
    }

    @ExceptionHandler(Exception.class)
    public void handleGenericException(Exception e) {
        System.err.println("An unexpected error occurred: " + e.getMessage());
        e.printStackTrace();
    }
}