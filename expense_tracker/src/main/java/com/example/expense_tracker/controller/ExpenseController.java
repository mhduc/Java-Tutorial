package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.CreateExpenseDto;
import com.example.expense_tracker.dto.UpdateExpenseDto;
import com.example.expense_tracker.exception.ExpenseOperationException;
import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.service.ExpenseService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public void addExpense(String description, double amount) {
        try {
            CreateExpenseDto dto = new CreateExpenseDto(description, amount, LocalDate.now());
            Expense expense = expenseService.createExpense(dto);
            System.out.println("Added (ID: " + expense.getId() + ")");
        } catch (ExpenseOperationException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateExpense(int id, String description, Double amount) {
        try {
            UpdateExpenseDto dto = new UpdateExpenseDto(description, amount, null);
            Optional<Expense> updated = expenseService.updateExpense(id, dto);
            System.out.println(updated.isPresent() ? "Updated (ID: " + id + ")" : "Not found (ID: " + id + ")");
        } catch (ExpenseOperationException e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteExpense(int id) {
        try {
            boolean deleted = expenseService.deleteExpense(id);
            System.out.println(deleted ? "Deleted (ID: " + id + ")" : "Not found (ID: " + id + ")");
        } catch (ExpenseOperationException e) {
            System.err.println(e.getMessage());
        }
    }

    public void listAllExpenses() {
        try {
            List<Expense> expenses = expenseService.getAllExpenses();
            if (expenses.isEmpty()) {
                System.out.println("No expenses");
            } else {
                expenses.forEach(e -> System.out.println("ID: " + e.getId() + " | " + e.getDescription() + " | " + e.getAmount() + " | " + e.getDate()));
            }
        } catch (ExpenseOperationException e) {
            System.err.println(e.getMessage());
        }
    }

    public void printTotalAmount() {
        System.out.println("Total: " + expenseService.getTotalAmount());
    }

    public void printMonthlyTotal(int month) {
        System.out.println("Monthly total (" + month + "): " + expenseService.getMonthlyTotal(month));
    }
}